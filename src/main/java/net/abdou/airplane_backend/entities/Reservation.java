package net.abdou.airplane_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import net.abdou.airplane_backend.enums.ReservationStatus;

import java.util.Date;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Flight flight;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Passenger passenger;
    @ManyToOne
    private Client client;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Date reservatioDate;

}
