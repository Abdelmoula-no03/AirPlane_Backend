package net.abdou.airplane_backend.mappers;

import net.abdou.airplane_backend.dtos.ClientDto;
import net.abdou.airplane_backend.dtos.FlightDto;
import net.abdou.airplane_backend.dtos.PassengerDto;
import net.abdou.airplane_backend.dtos.ReservationDto;
import net.abdou.airplane_backend.entities.Client;
import net.abdou.airplane_backend.entities.Flight;
import net.abdou.airplane_backend.entities.Passenger;
import net.abdou.airplane_backend.entities.Reservation;
import net.abdou.airplane_backend.enums.ReservationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FlightMapper {

    public ClientDto fromClient(Client client){
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        return clientDto;
    }

    public FlightDto fromFlight(Flight flight){
        FlightDto flightDto = new FlightDto();
        BeanUtils.copyProperties(flight, flightDto);
        return flightDto;
    }

    public PassengerDto fromPassenger (Passenger passenger){
        PassengerDto passengerDto = new PassengerDto();
        BeanUtils.copyProperties(passenger, passengerDto);
        //passengerDto.setReservationDto(fromReservation(passenger.getReservation()));
        return passengerDto;
    }

    public ReservationDto fromReservation(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        BeanUtils.copyProperties(reservation, reservationDto);
        reservationDto.setFlight(fromFlight(reservation.getFlight()));
        if (reservation.getStatus() == ReservationStatus.CONFIRMED){
            reservationDto.setPassenger(fromPassenger(reservation.getPassenger()));
        }
        reservationDto.setReservationDate(reservation.getReservatioDate());
        return reservationDto;
    }

    public Client fromClientDto(ClientDto clientDto){
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return client;
    }

    public Flight fromFlightDto(FlightDto flightDto){
        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return flight;
    }

    public Passenger fromPassengerDto (PassengerDto passengerDto){
        Passenger passenger = new Passenger();
        BeanUtils.copyProperties(passengerDto, passenger);
        return passenger;
    }

    public Reservation fromReservationDto(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservation);
        return reservation;
    }




}
