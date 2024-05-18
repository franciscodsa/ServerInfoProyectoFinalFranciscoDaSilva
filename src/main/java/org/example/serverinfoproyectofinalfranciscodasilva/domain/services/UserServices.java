package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.*;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices {


    private final UserRepository userRepository;

    private final AccountantRepository accountantRepository;
    private final ClientRepository clientRepository;
    private final FileDBRepository filesRepository;
    private final BalanceRepository balanceRepository;
/*    private final ChatsRepository chatsRepository;
    private final MessagesRepository messagesRepository;*/

    public User addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsersException("Usuario inexistente"));

    }

    @Transactional
    public void deleteUser(String email) {
        // Check if the user exists
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userRepository.existsById(email)) {
            throw new UsersException("Usuario inexistente");
        }

        // Delete accountant details if the user is an accountant
        accountantRepository.findById(email).ifPresent(accountant -> {
         /*   chatsRepository.findByAccountantEmail(email).ifPresent(chat -> {
                messagesRepository.deleteAllByChatId(chat.getId());
                chatsRepository.delete(chat);
            });*/
            accountantRepository.delete(accountant);
        });

        // Delete client details if the user is a client
        clientRepository.findById(email).ifPresent(client -> {
            balanceRepository.deleteAllByClientEmail(email);
            filesRepository.deleteAllByClientEmail(email);
           /* chatsRepository.findByClientEmail(email).ifPresent(chat -> {
                messagesRepository.deleteAllByChatId(chat.getId());
                chatsRepository.delete(chat);
            });*/
            clientRepository.delete(client);
        });

        // Finally, delete the user
        userRepository.deleteById(email);
    }
}
