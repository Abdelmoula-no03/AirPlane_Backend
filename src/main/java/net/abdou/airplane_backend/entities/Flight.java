package net.abdou.airplane_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.abdou.airplane_backend.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String departVille;
    private String arriveVille;
    private int num_sieges = 40;
    private LocalDateTime departDate;
    private LocalDateTime arriveDate;
    @OneToMany(mappedBy = "flight", orphanRemoval = true)
    private List<Passenger> passengers;
    private double price;
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;


    @PrePersist
    @PreUpdate
    private void updateFlightStatus() {
        if (passengers != null && passengers.size() == num_sieges) {
            flightStatus = FlightStatus.CLOSE;
        } else {
            flightStatus = FlightStatus.OPEN;
        }
    }
}
