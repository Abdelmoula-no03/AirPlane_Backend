package net.abdou.airplane_backend.repositories;

import net.abdou.airplane_backend.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findReservationsByClientId(Long clientId);


    Page<Reservation> findReservationsByClientId(Long clientId,Pageable pageable);
}
