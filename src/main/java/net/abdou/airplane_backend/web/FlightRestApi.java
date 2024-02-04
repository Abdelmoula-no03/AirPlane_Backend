package net.abdou.airplane_backend.web;

import net.abdou.airplane_backend.dtos.FlightDto;
import net.abdou.airplane_backend.dtos.ReservationDto;
import net.abdou.airplane_backend.exceptions.ClientNotFoundException;
import net.abdou.airplane_backend.exceptions.FlightNotFoundException;
import net.abdou.airplane_backend.exceptions.ReservationNotFoundException;
import net.abdou.airplane_backend.exceptions.UnauthorizedCancellationException;
import net.abdou.airplane_backend.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class FlightRestApi {
    private final FlightService flightService;

    public FlightRestApi(FlightService flightService) {
        this.flightService = flightService;
    }
    @GetMapping("/flights")
    public List<FlightDto> flightsAtDate(@RequestParam(name = "date") String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return flightService.listFlightsByDate(localDate);
    }

    @PostMapping("/flights/reserve")
    public ReservationDto reserve (@RequestParam(name = "clientId") Long clientId,@RequestParam(name = "flightId") Long flightId) throws FlightNotFoundException, ClientNotFoundException {
        return flightService.reserve(clientId, flightId);
    }

    @DeleteMapping("/reservations/{reservationId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId, @RequestParam Long clientId) {
        try {
            flightService.cancelReservation(reservationId, clientId);
            return ResponseEntity.ok("Reservation canceled successfully");
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        } catch (UnauthorizedCancellationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized cancellation");
        }
    }

    @GetMapping("/reservations/{reservationId}")
    public ReservationDto getReservation(@PathVariable Long reservationId) throws ReservationNotFoundException {
        return flightService.getReservation(reservationId);
    }

}
