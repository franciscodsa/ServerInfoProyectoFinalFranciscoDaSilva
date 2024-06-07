package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.serverinfoproyectofinalfranciscodasilva.common.ConstantesRoles.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    /*@PostMapping()
    public User saveUser(@RequestBody User user) {
        return userServices.addUser(user);
    }*/

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @PostMapping("/update")
    public ResponseEntity<AppMessage> updateUser(@RequestBody User userDetails) {
        userServices.updateUser(userDetails);

        return ResponseEntity.ok(new AppMessage("Actualizado"));

    }

/*
    @GetMapping()
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }
*/

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("{email}")
    public ResponseEntity<User> getUserById(@PathVariable String email) {
        User user = userServices.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @RolesAllowed({ROLE_ADMIN})
    @DeleteMapping("/{email}")
    public ResponseEntity<AppMessage> deleteUser(@PathVariable String email) {
        userServices.deleteUser(email);
        return ResponseEntity.ok().body(new AppMessage("Eliminado"));
    }
}
