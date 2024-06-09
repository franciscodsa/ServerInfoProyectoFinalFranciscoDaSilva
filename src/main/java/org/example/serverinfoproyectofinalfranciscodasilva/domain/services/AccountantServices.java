package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.AccountantRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountantServices {
    private final AccountantRepository accountantRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public Accountant add(Accountant accountant) {
        if (userRepository.findByEmail(accountant.getEmail()).isPresent()) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return accountantRepository.save(accountant);
    }

    public Accountant getAccountantByClientEmail(String clientEmail) {

        Client client = clientRepository.findById(clientEmail)
                .orElseThrow(() -> new UsersException("Cliente no encontrado"));

        if (client.getAccountantEmail() == null) {
            throw new UsersException("Cliente no tiene contador asignado");
        }

        return accountantRepository.findAccountantByClientEmail(clientEmail).orElseThrow(() -> new UsersException("Contador no encontrado"));
    }

    public List<Accountant> getAll() {
        return accountantRepository.findAll();
    }

}
