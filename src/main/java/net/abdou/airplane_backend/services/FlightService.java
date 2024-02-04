package net.abdou.airplane_backend.services;

import net.abdou.airplane_backend.dtos.*;
import net.abdou.airplane_backend.exceptions.ClientNotFoundException;
import net.abdou.airplane_backend.exceptions.FlightNotFoundException;
import net.abdou.airplane_backend.exceptions.ReservationNotFoundException;
import net.abdou.airplane_backend.exceptions.UnauthorizedCancellationException;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    ClientDto saveClient(ClientDto clientDto);
    List<ClientDto> listClients();
    ClientDto getClient(Long clientId) throws ClientNotFoundException;
    void removeClient(Long clientId) throws ClientNotFoundException;
    ReservationDto reserve (Long clientId, Long flightId) throws ClientNotFoundException, FlightNotFoundException;
    ReservationDto getReservation (Long reservationId) throws ReservationNotFoundException;
    void cancelReservation (Long reservationId, Long clientId) throws ReservationNotFoundException, UnauthorizedCancellationException;
    List<FlightDto> listFlightsByDate(LocalDate date);
    List<ReservationDto> listClientReservations(Long clientId);
    List<PassengerDto> getFlightPassengers(Long flightId) throws FlightNotFoundException;
    ClientHistoryDto getClientHistory(Long clientId, int page, int size) throws ClientNotFoundException;












}
