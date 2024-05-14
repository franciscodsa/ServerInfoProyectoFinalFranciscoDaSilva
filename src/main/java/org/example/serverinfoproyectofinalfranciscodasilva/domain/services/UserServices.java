package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.UserRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.springframework.stereotype.Service;

import java.util.List;

//todo: esto se usa para resolver los lazy initialization exception en conjunto con excluir del tostring las cosas lazy
@Transactional
@Service
@RequiredArgsConstructor
public class UserServices {


    private final UserRepository userRepository;

    public User addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsersException("Ya existe un usuario con este email");
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        final List<User> all = userRepository.findAll();

        return all;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsersException("Usuario inexistente"));

        return user;

    }
}
