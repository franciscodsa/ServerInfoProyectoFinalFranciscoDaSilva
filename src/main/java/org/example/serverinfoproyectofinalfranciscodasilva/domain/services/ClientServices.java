package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServices {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    //todo: creo que es mejor no devolver el client y en su lugar devolver un AppMessage para decir que ya se agrego el usuario
    public Client add(Client client) {
        //Hay que chequear con UserRepository porque sino solo se chequea la tabla clients y puede que exista un accountant con el mismo email
        if (userRepository.existsById(client.getEmail())) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return clientRepository.save(client);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findById(email).orElseThrow(() -> new UsersException("Usuario no encontrado"));
    }

    public List<Client> getClientsByAccountantEmail(String accountantEmail){
        return clientRepository.findAllByAccountantEmail(accountantEmail);
    }

    public List<Client> getClientsWithNoAccountant(){
        return clientRepository.findAllByAccountantEmailIsNull();
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    //todo: esto no va a hacer falta porque se eliminaran con el endpoint de users
    public void deleteByEmail(String email) {
        if (!clientRepository.existsById(email)) {
            throw new UsersException("Usuario no encontrado");
        }
        clientRepository.deleteById(email);
    }

    @Transactional
    public Client updateClient(Client updatedClient) {

        if (!userRepository.existsById(updatedClient.getAccountantEmail())){
            throw new UsersException("Contador inexistente");
        }

        Client existingClient = clientRepository.findById(updatedClient.getEmail())
                .orElseThrow(() -> new UsersException("Cliente no encontrado"));

        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setLastName(updatedClient.getLastName());
        existingClient.setDateOfBirth(updatedClient.getDateOfBirth());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setAccountantEmail(updatedClient.getAccountantEmail());

        return clientRepository.save(existingClient);
    }
}
