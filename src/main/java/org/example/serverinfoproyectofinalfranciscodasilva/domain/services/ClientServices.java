package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServices {
    private final ClientRepository clientRepository;
    public Client add(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()){
            throw new UsersException("Ya existe un usuario con este email");
        }

        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
