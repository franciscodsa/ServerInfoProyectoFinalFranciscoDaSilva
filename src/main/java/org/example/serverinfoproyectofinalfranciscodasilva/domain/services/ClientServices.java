package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServices {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    //todo: creo que es mejor no devolver el client y en su lugar devolver un AppMessage para decir que ya se agrego el usuario
    public Client add(Client client) {
        //Hay que chequear con UserRepository porque sino solo se chequea la tabla clients y puede que exista un accountant con el mismo email
        if (userRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return clientRepository.save(client);
    }

    public Client getByEmail(String email) {
        Client client = clientRepository.findById(email).orElseThrow(() -> new UsersException("Usuario no encontrado"));

        return client;
    }

    public List<Client> getAll() {
        final List<Client> all = clientRepository.findAll();
        return all;
    }
}
