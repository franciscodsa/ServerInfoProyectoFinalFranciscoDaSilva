package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.*;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.common.ConstantesServices;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final AccountantRepository accountantRepository;
    private final ClientRepository clientRepository;
    private final FileRepository filesRepository;
    private final BalanceRepository balanceRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsersException(ConstantesServices.USUARIO_INEXISTENTE));

    }

    @Transactional
    public void deleteUser(String email) {

        if (!userRepository.existsById(email)) {
            throw new UsersException(ConstantesServices.USUARIO_INEXISTENTE);
        }


        // Elimina al contador y coloca en null el accountantEmail de sus clientes para que puedan ser reasignados a otro contador
        accountantRepository.findById(email).ifPresent(accountant -> {
            clientRepository.findAllByAccountantEmail(email).forEach(client -> {
                client.setAccountantEmail(null);
                clientRepository.save(client);
            });

            accountantRepository.delete(accountant);
        });


        // Elimina al cliente junto a sus archivos y balances
        clientRepository.findById(email).ifPresent(client -> {
            filesRepository.getFilesInfoByClient(email).forEach(file -> balanceRepository.deleteByFilesId(file.getId()));

            filesRepository.deleteAllByClientEmail(email);

            clientRepository.delete(client);
        });

        // elimina el usuario
        userRepository.deleteById(email);
    }

    public void updateUser(User userDetails) {
        if (!userRepository.existsById(userDetails.getEmail())) {
            throw new UsersException(ConstantesServices.USUARIO_INEXISTENTE);
        }

        clientRepository.findById(userDetails.getEmail()).ifPresent(client -> {
            client.setPhone(userDetails.getPhone());
            client.setFirstName(userDetails.getFirstName());
            client.setLastName(userDetails.getLastName());
            client.setDateOfBirth(userDetails.getDateOfBirth());

            clientRepository.save(client);
        });


        accountantRepository.findById(userDetails.getEmail()).ifPresent(accountant -> {
            accountant.setPhone(userDetails.getPhone());
            accountant.setFirstName(userDetails.getFirstName());
            accountant.setLastName(userDetails.getLastName());
            accountant.setDateOfBirth(userDetails.getDateOfBirth());

            accountantRepository.save(accountant);
        });
    }

}
