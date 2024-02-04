package net.abdou.airplane_backend;

import net.abdou.airplane_backend.entities.Client;
import net.abdou.airplane_backend.entities.Flight;
import net.abdou.airplane_backend.entities.Passenger;
import net.abdou.airplane_backend.entities.Reservation;
import net.abdou.airplane_backend.enums.ReservationStatus;
import net.abdou.airplane_backend.repositories.ClientRepository;
import net.abdou.airplane_backend.repositories.FlightRepository;
import net.abdou.airplane_backend.repositories.PassengerRepository;
import net.abdou.airplane_backend.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
public class AirPlaneBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(AirPlaneBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ClientRepository clientRepository,
                            FlightRepository flightRepository,
                            PassengerRepository passengerRepository,
                            ReservationRepository reservationRepository){

        return args -> {
            Stream.of("Samir", "Marwan", "Ilyass", "Mohammed")
                    .forEach(name ->
                    {
                        Client client = new Client();
                        client.setName(name);
                        client.setEmail(name + "@gmail.com");
                        clientRepository.save(client);

                    });

            clientRepository.findAll().forEach(
                    client -> {
                        String passportId = UUID.randomUUID().toString();
                        for (int i = 0;i < 4 ;i++){


                            //for Flight
                            Flight flight = new Flight();

                                // Create a list of cities
                            List<String> cities = new ArrayList<>();
                            cities.add("New York");
                            cities.add("London");
                            cities.add("Tokyo");
                            cities.add("Paris");
                            cities.add("Rabat");
                            cities.add("Marrakech");
                            cities.add("Casablanca");
                            cities.add("Tanger");
                            cities.add("Marseille");
                                // choose a random city
                            Random random = new Random();
                            int randomInt = random.nextInt(cities.size());
                            flight.setDepartVille(cities.get(randomInt));
                            cities.remove(randomInt);
                            int randomInt1 = random.nextInt(cities.size());
                            flight.setArriveVille(cities.get(randomInt1));
                            LocalDateTime randomDate = generateRandomFutureDate();
                            flight.setDepartDate(randomDate);
                            flight.setArriveDate(randomDate.plus(generateRandomDuration()));
                            flight.setPrice(800*Math.random());
                            flightRepository.save(flight);

                            //for reservation
                            Reservation reservation = new Reservation();
                            reservation.setClient(client);
                            reservation.setFlight(flight);
                            reservation.setReservatioDate(new Date());
                            reservation.setStatus(Math.random()>0.3? ReservationStatus.CONFIRMED:ReservationStatus.CANCELED);
                            reservationRepository.save(reservation);

                            Passenger passenger = new Passenger();
                            passenger.setName(reservation.getClient().getName());
                            passenger.setEmail(reservation.getClient().getEmail());
                            passenger.setFlight(reservation.getFlight());
                            passenger.setPassportId(passportId);
                            passenger.setReservation(reservation);
                            passengerRepository.save(passenger);

                        }

                    }
            );

        };
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static LocalDateTime generateRandomFutureDate() {

        int minFutureDays = 1;   // Minimum 1 day in the future
        int maxFutureDays = 30;  // Maximum 30 days in the future

        // Generate a random number of future days
        int randomFutureDays = getRandomNumberInRange(minFutureDays, maxFutureDays);
        // Create a LocalDateTime object for the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();


        return currentDateTime.plusDays(randomFutureDays);
    }

    public static Duration generateRandomDuration() {
        // Set the range of hours
        int minHours = 2;
        int maxHours = 5;

        // Generate a random number of hours within the specified range
        int randomHours = getRandomNumberInRange(minHours, maxHours);

        // Create a Duration object with the random number of hours
        return Duration.ofHours(randomHours);
    }

}
