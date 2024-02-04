package net.abdou.airplane_backend.web;

import net.abdou.airplane_backend.dtos.ClientDto;
import net.abdou.airplane_backend.dtos.ClientHistoryDto;
import net.abdou.airplane_backend.dtos.PassengerDto;
import net.abdou.airplane_backend.exceptions.ClientNotFoundException;
import net.abdou.airplane_backend.exceptions.FlightNotFoundException;
import net.abdou.airplane_backend.services.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientRestApi {
      private final FlightService flightService;

      public ClientRestApi(FlightService flightService){
          this.flightService = flightService;
      }

      @GetMapping("/flights/{flightId}/passengers")
      public List<PassengerDto> getFlightPassengers (@PathVariable Long flightId) throws FlightNotFoundException {
          return flightService.getFlightPassengers(flightId);
      }
      @GetMapping("/clients")
      public List<ClientDto> getClientList(){
          return flightService.listClients();
      }
      @GetMapping("/clients/{clientId}")
      public ClientDto getClient(@PathVariable Long clientId) throws ClientNotFoundException {
          return flightService.getClient(clientId);
      }
      @PostMapping("/clients/add")
      public ClientDto addClient(@RequestBody ClientDto clientDto){
          return flightService.saveClient(clientDto);
      }

      @DeleteMapping("/clients/{clientId}")
      public void deleteClient(@PathVariable Long clientId) throws ClientNotFoundException {
          flightService.removeClient(clientId);
      }
      @GetMapping("/clients/{clientId}/history")
      public ClientHistoryDto getClientHistory(@PathVariable Long clientId,@RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "2") int size) throws ClientNotFoundException {
          return flightService.getClientHistory(clientId, page, size);
      }






}
