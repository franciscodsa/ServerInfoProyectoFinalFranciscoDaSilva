package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
import org.example.serverinfoproyectofinalfranciscodasilva.spring.common.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.ROLE_ACCOUNTANT;
import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.ROLE_ADMIN;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientServices clientServices;

    @PostMapping(Constantes.ADD_CLIENT_PATH)
    public ResponseEntity<AppMessage> addClient(@RequestBody Client cliente) {
        clientServices.add(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage(Constantes.CLIENTE_AGREGADO));
    }

    @RolesAllowed({ROLE_ADMIN})
    @PostMapping(Constantes.UPDATE_CLEINT_PATH)
    public ResponseEntity<AppMessage> updateClient(@RequestBody Client client) {
        clientServices.updateClient(client);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage(Constantes.ACTUALIZADO));
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping(Constantes.GET_CLIENTS_PATH)
    public List<Client> getAllClients() {
        return clientServices.getAll();
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping(Constantes.GET_CLIENTES_WTIH_NO_ACCOUNTANT_PATH)
    public List<Client> getClientsWithNoAccountant() {
        return clientServices.getClientsWithNoAccountant();
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT})
    @GetMapping(Constantes.GET_ACCOUNTANT_CLIENTS_PATH)
    public List<Client> getClientsByAccountantEmail(String accountantEmail) {
        return clientServices.getClientsByAccountantEmail(accountantEmail);
    }

}