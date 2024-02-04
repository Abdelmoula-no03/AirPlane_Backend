package net.abdou.airplane_backend.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter @Getter
public class FlightDto {
    private Long Id;
    private String departVille;
    private String arriveVille;
    private int num_sieges = 40;
    private LocalDateTime departDate;
    private LocalDateTime arriveDate;
    private double price;
}
