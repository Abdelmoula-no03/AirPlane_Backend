package net.abdou.airplane_backend.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import net.abdou.airplane_backend.entities.Flight;
import net.abdou.airplane_backend.entities.Passenger;
import net.abdou.airplane_backend.enums.ReservationStatus;

import java.util.Date;
@Getter @Setter
public class ReservationDto {
    private Long id;
    private FlightDto flight;
    private PassengerDto passenger;
    private ReservationStatus status;
    private Date reservationDate;
}
