package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.*;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientServices clientServices;

    @PostMapping("/addClient")
    public ResponseEntity<AppMessage> addClient(@RequestBody Client cliente) {
        clientServices.add(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Cliente agregado"));
    }

    @RolesAllowed({ROLE_ADMIN})
    @PostMapping("/clients/update")
    public ResponseEntity<AppMessage> updateClient(@RequestBody Client client) {
        clientServices.updateClient(client);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Actualizado"));
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientServices.getAll();
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping("/clients/noAccountant")
    public List<Client> getClientsWithNoAccountant(){
        return clientServices.getClientsWithNoAccountant();
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT})
    @GetMapping("/clients/byAccountant")
    public List<Client> getClientsByAccountantEmail(String accountantEmail){
        return clientServices.getClientsByAccountantEmail(accountantEmail);
    }

}