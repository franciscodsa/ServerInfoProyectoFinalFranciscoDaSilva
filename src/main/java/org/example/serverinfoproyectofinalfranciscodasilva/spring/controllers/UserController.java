package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    @PostMapping()
    public User saveUser(@RequestBody User user){
        return userServices.addUser(user);
    }
    @GetMapping()
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }

    @GetMapping("{email}")
    public ResponseEntity<User> getUserById(@PathVariable String email) {
        User user = userServices.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
