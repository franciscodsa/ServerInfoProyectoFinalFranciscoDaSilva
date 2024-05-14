package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.client.ClientInfoDTO;
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
        if (userRepository.findByEmail(client.getEmail()).isPresent()){
            throw new UsersException("Ya existe un usuario con este email");
        }

        return clientRepository.save(client);
    }

    public Client getByEmail(String email){
        return null;
    }

    public List<ClientInfoDTO> getAll() {
        final List<ClientInfoDTO> all = clientRepository.findAllClients();
        return all;
    }
}