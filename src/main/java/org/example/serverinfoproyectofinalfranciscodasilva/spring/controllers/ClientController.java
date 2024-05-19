package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientServices clientServices;

    @PostMapping
    public ResponseEntity<AppMessage> addClient(@RequestBody Client cliente) {
        clientServices.add(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Cliente agregado"));
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientServices.getAll();
    }

    @GetMapping("/noAccountant")
    public List<Client> getClientsWithNoAccountant(){
        return clientServices.getClientsWithNoAccountant();
    }

    @GetMapping("/byAccountant")
    public List<Client> getClientsByAccountantEmail(String accountantEmail){
        return clientServices.getClientsByAccountantEmail(accountantEmail);
    }

    @GetMapping("/{clientEmail}")
    public Client getClientByEmail(@PathVariable String clientEmail) {
        return clientServices.getClientByEmail(clientEmail);
    }


    //todo: esto no va a hacer falta porque se eliminaran con el endpoint de users
    @DeleteMapping("/delete/{clientEmail}")
    public ResponseEntity<AppMessage> deleteClient(@PathVariable String clientEmail) {
        clientServices.deleteByEmail(clientEmail);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Eliminado"));
    }

    @PostMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientServices.updateClient(client));
    }
}