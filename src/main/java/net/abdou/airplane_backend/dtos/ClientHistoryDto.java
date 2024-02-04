package net.abdou.airplane_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.abdou.airplane_backend.entities.Reservation;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientHistoryDto {
    private List<ReservationDto> reservationList;
    private String name;
    private int currentPage;
    private int totalPages;
    private int pageSize;
}
