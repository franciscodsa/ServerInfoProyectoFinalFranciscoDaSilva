package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.AccountantRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.common.ConstantesServices;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountantServices {
    private final AccountantRepository accountantRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public Accountant add(Accountant accountant) {
        if (userRepository.findByEmail(accountant.getEmail()).isPresent()) {
            throw new UsersException(ConstantesServices.YA_EXISTE_UN_USUARIO_CON_ESTE_EMAIL);
        }

        return accountantRepository.save(accountant);
    }

    public Accountant getAccountantByClientEmail(String clientEmail) {

        Client client = clientRepository.findById(clientEmail)
                .orElseThrow(() -> new UsersException(ConstantesServices.CLIENTE_NO_ENCONTRADO));

        if (client.getAccountantEmail() == null) {
            throw new UsersException(ConstantesServices.CLIENTE_NO_TIENE_CONTADOR_ASIGNADO);
        }

        return accountantRepository.findAccountantByClientEmail(clientEmail).orElseThrow(() -> new UsersException(ConstantesServices.CONTADOR_NO_ENCONTRADO));
    }

    public List<Accountant> getAll() {
        return accountantRepository.findAll();
    }

}
