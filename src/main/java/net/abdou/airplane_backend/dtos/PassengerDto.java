package net.abdou.airplane_backend.dtos;

import lombok.*;
import net.abdou.airplane_backend.entities.Flight;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PassengerDto {
    private Long id;
    private String name;
    private String email;
    private String passportId;
}
