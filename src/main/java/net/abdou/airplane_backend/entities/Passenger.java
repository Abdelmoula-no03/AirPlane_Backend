package net.abdou.airplane_backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Passenger {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String passportId;
    @OneToOne
    private Reservation reservation;
    @ManyToOne
    private Flight flight;


}
