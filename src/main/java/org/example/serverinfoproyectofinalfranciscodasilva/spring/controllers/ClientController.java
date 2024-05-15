package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.client.ClientInfoDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
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
    public List<Client> getAllClientes() {
        final List<Client> all = clientServices.getAll();
        return all;
    }

    @GetMapping("/{clientEmail}")
    public Client getClientByEmail(@PathVariable String clientEmail){
        return clientServices.getByEmail(clientEmail);
    }
}