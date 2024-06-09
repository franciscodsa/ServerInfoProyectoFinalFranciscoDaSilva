package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.common.ConstantesServices;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServices {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public Client add(Client client) {
        //Hay que chequear con UserRepository porque sino solo se chequea la tabla clients y puede que exista un accountant con el mismo email
        if (userRepository.existsById(client.getEmail())) {
            throw new UsersException(ConstantesServices.YA_EXISTE_UN_USUARIO_CON_ESTE_EMAIL);
        }

        return clientRepository.save(client);
    }


    public List<Client> getClientsByAccountantEmail(String accountantEmail) {
        return clientRepository.findAllByAccountantEmail(accountantEmail);
    }

    public List<Client> getClientsWithNoAccountant() {
        return clientRepository.findAllByAccountantEmailIsNull();
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client updateClient(Client updatedClient) {

        if (updatedClient.getAccountantEmail() != null && !userRepository.existsById(updatedClient.getAccountantEmail())) {
            throw new UsersException(ConstantesServices.CONTADOR_INEXISTENTE);
        }

        Client existingClient = clientRepository.findById(updatedClient.getEmail())
                .orElseThrow(() -> new UsersException(ConstantesServices.CLIENTE_NO_ENCONTRADO));

        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setLastName(updatedClient.getLastName());
        existingClient.setDateOfBirth(updatedClient.getDateOfBirth());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setAccountantEmail(updatedClient.getAccountantEmail());

        return clientRepository.save(existingClient);
    }
}
