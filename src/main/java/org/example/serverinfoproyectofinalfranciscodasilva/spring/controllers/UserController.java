package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.UserServices;
import org.example.serverinfoproyectofinalfranciscodasilva.spring.common.Constantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constantes.USERS_PATH)
public class UserController {

    private final UserServices userServices;

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @PostMapping(Constantes.UPDATE_USER_PATH)
    public ResponseEntity<AppMessage> updateUser(@RequestBody User userDetails) {
        userServices.updateUser(userDetails);

        return ResponseEntity.ok(new AppMessage(Constantes.ACTUALIZADO));

    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.EMAIL)
    public ResponseEntity<User> getUserById(@PathVariable String email) {
        User user = userServices.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @RolesAllowed({ROLE_ADMIN})
    @DeleteMapping(Constantes.EMAIL_PATH_VARIABLE)
    public ResponseEntity<AppMessage> deleteUser(@PathVariable String email) {
        userServices.deleteUser(email);
        return ResponseEntity.ok().body(new AppMessage(Constantes.ELIMINADO));
    }
}
