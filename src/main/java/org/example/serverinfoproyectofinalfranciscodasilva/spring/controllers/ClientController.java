package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.common.ConstantesRoles.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientServices clientServices;


    @PostMapping("/add")
    public ResponseEntity<AppMessage> addClient(@RequestBody Client cliente) {
        clientServices.add(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Cliente agregado"));
    }

    @RolesAllowed({ROLE_ADMIN})
    @PostMapping("/update")
    public ResponseEntity<AppMessage> updateClient(@RequestBody Client client) {
        clientServices.updateClient(client);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Actualizado"));
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping
    public List<Client> getAllClients() {
        return clientServices.getAll();
    }

    @RolesAllowed({ROLE_ADMIN})
    @GetMapping("/noAccountant")
    public List<Client> getClientsWithNoAccountant(){
        return clientServices.getClientsWithNoAccountant();
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT})
    @GetMapping("/byAccountant")
    public List<Client> getClientsByAccountantEmail(String accountantEmail){
        return clientServices.getClientsByAccountantEmail(accountantEmail);
    }

   /* //todo: esto no va a hacer falta porque el detealle lo estoy cogiendo directo de la lista de clientes
    @GetMapping("/{clientEmail}")
    public Client getClientByEmail(@PathVariable String clientEmail) {
        return clientServices.getClientByEmail(clientEmail);
    }

    //todo: esto no va a hacer falta porque se eliminaran con el endpoint de users

    @DeleteMapping("/delete/{clientEmail}")
    public ResponseEntity<AppMessage> deleteClient(@PathVariable String clientEmail) {
        clientServices.deleteByEmail(clientEmail);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Eliminado"));
    }*/
}