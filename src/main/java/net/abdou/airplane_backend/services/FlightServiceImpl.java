package net.abdou.airplane_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.abdou.airplane_backend.dtos.*;
import net.abdou.airplane_backend.entities.Client;
import net.abdou.airplane_backend.entities.Flight;
import net.abdou.airplane_backend.entities.Passenger;
import net.abdou.airplane_backend.entities.Reservation;
import net.abdou.airplane_backend.enums.FlightStatus;
import net.abdou.airplane_backend.enums.ReservationStatus;
import net.abdou.airplane_backend.exceptions.ClientNotFoundException;
import net.abdou.airplane_backend.exceptions.FlightNotFoundException;
import net.abdou.airplane_backend.exceptions.ReservationNotFoundException;
import net.abdou.airplane_backend.exceptions.UnauthorizedCancellationException;
import net.abdou.airplane_backend.mappers.FlightMapper;
import net.abdou.airplane_backend.repositories.ClientRepository;
import net.abdou.airplane_backend.repositories.FlightRepository;
import net.abdou.airplane_backend.repositories.PassengerRepository;
import net.abdou.airplane_backend.repositories.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService{

    private ClientRepository clientRepository;
    private FlightRepository flightRepository;
    private PassengerRepository passengerRepository;
    private ReservationRepository reservationRepository;
    private FlightMapper flightMapper;


    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = flightMapper.fromClientDto(clientDto);
        clientRepository.save(client);
        log.info("Client saved successfully!!");
        return clientDto;
    }

    @Override
    public List<ClientDto> listClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(flightMapper::fromClient)
                .toList();
    }

    @Override
    public ClientDto getClient(Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("The client not Found!"));
        return flightMapper.fromClient(client);
    }

    @Override
    public void removeClient(Long clientId) throws ClientNotFoundException {
        clientRepository.deleteById(clientId);
        log.info("Client delated successfully!!");

    }

    @Override
    public ReservationDto reserve(Long clientId, Long flightId) throws ClientNotFoundException, FlightNotFoundException {
        Client client = clientRepository.findById(clientId)
                        .orElseThrow(()-> new ClientNotFoundException("the reservation cannot be made because the Client not found !"));
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("The flight for this reservation are not found!!"));
        if(flight.getFlightStatus() == FlightStatus.OPEN){

            Reservation reservation = new Reservation();
            reservation.setReservatioDate(new Date());
            reservation.setFlight(flight);
            reservation.setClient(client);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);

            Passenger passenger = new Passenger();
            passenger.setName(reservation.getClient().getName());
            passenger.setEmail(reservation.getClient().getEmail());
            passenger.setFlight(reservation.getFlight());
            passenger.setPassportId(UUID.randomUUID().toString());
            passenger.setReservation(reservation);
            passengerRepository.save(passenger);

            reservation.setPassenger(passenger);

            flightRepository.save(flight);

            return flightMapper.fromReservation(reservation);
        }
        else {
            log.info("the flight is no longer open for reservation");
        }
        return null;
    }

    @Override
    public ReservationDto getReservation(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found !!"));
        return flightMapper.fromReservation(reservation);
    }


    @Override
    public void cancelReservation(Long reservationId, Long clientId) throws ReservationNotFoundException, UnauthorizedCancellationException {
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("You re trying to cancel a reservation that is not found!!"));
        if(!reservation.getClient().getId().equals(clientId)){
            throw new UnauthorizedCancellationException("Unauthorized cancellation for reservation");
        }

        Flight flight = reservation.getFlight();
        Passenger passenger = reservation.getPassenger();
        flight.getPassengers().remove(passenger);
        reservation.setStatus(ReservationStatus.CANCELED);
        passengerRepository.delete(passenger);
        reservationRepository.save(reservation);
        flightRepository.save(flight);

    }

    @Override
    public List<FlightDto> listFlightsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Flight> flights = flightRepository.findByDepartDateBetween(startOfDay, endOfDay);

        if (flights.isEmpty()) {
            log.info("No flights found for the specified day.");
        }
        return flights.stream()
                .map(flightMapper::fromFlight)
                .toList();
    }

    @Override
    public List<ReservationDto> listClientReservations(Long clientId) {
        return null;
    }

    @Override
    public List<PassengerDto> getFlightPassengers(Long flightId) throws FlightNotFoundException {
        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new FlightNotFoundException("Flight Not Found !!")
        );

        return flight
                .getPassengers()
                .stream()
                .map(flightMapper::fromPassenger)
                .toList();
    }

    @Override
    public ClientHistoryDto getClientHistory(Long clientId, int page, int size) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("You re searching for history of client that not found!"));
        Page<Reservation> clientHistory = reservationRepository.findReservationsByClientId(clientId, PageRequest.of(page,size));
        List<ReservationDto> reservationDtos = clientHistory
                .getContent()
                .stream()
                .map(flightMapper::fromReservation)
                .toList();
        ClientHistoryDto clientHistoryDto = new ClientHistoryDto();
        clientHistoryDto.setName(client.getName());
        clientHistoryDto.setReservationList(reservationDtos);
        clientHistoryDto.setCurrentPage(page);
        clientHistoryDto.setPageSize(size);
        clientHistoryDto.setTotalPages(clientHistory.getTotalPages());

        return clientHistoryDto;
    }
}
