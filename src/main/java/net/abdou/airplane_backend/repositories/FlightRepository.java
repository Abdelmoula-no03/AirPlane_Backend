package net.abdou.airplane_backend.repositories;

import net.abdou.airplane_backend.entities.Flight;
import net.abdou.airplane_backend.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
