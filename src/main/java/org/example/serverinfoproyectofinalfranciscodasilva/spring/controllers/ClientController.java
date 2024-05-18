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
    public Client addClient(@RequestBody Client cliente) {
        return clientServices.add(cliente);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientServices.getAll();
    }

    @GetMapping("/{clientEmail}")
    public Client getClientByEmail(@PathVariable String clientEmail) {
        return clientServices.getByEmail(clientEmail);
    }


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