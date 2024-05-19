package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.AccountantRepository;
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

    public Accountant add(Accountant accountant) {
        if (userRepository.findByEmail(accountant.getEmail()).isPresent()) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return accountantRepository.save(accountant);
    }

    public Accountant getByEmail(String email) {
        return accountantRepository.findById(email).orElseThrow(() -> new UsersException("Contador no encontrado"));
    }

    public List<Accountant> getAll() {
        return accountantRepository.findAll();
    }

    //todo: esto no va a hacer falta porque se eliminaran con el endpoint de users
    public void deleteByEmail(String email) {
        if (!accountantRepository.existsById(email)) {
            throw new UsersException("Contador no encontrado");
        }
        accountantRepository.deleteById(email);
    }

    @Transactional
    public Accountant updateAccountant(Accountant updatedAccountant) {

        Accountant existingAccount = accountantRepository.findById(updatedAccountant.getEmail())
                .orElseThrow(() -> new UsersException("Contodar no encontrado"));

        existingAccount.setFirstName(updatedAccountant.getFirstName());
        existingAccount.setLastName(updatedAccountant.getLastName());
        existingAccount.setDateOfBirth(updatedAccountant.getDateOfBirth());
        existingAccount.setPhone(updatedAccountant.getPhone());

        return accountantRepository.save(existingAccount);
    }
}
