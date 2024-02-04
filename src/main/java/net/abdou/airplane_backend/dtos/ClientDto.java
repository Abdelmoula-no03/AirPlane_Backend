package net.abdou.airplane_backend.dtos;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String email;
}
