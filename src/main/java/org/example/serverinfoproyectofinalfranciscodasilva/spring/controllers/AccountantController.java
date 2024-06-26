package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.AccountantServices;
import org.example.serverinfoproyectofinalfranciscodasilva.spring.common.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.*;

@RestController
@AllArgsConstructor
@RequestMapping(Constantes.ACCOUNTANT_PATH)
public class AccountantController {

    private final AccountantServices accountantServices;

    @RolesAllowed({ROLE_ADMIN})
    @PostMapping(Constantes.ADD_PATH)
    public ResponseEntity<AppMessage> addAccountant(@RequestBody Accountant accountant) {
        accountantServices.add(accountant);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage(Constantes.CONTADOR_AGREGADO));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping()
    public ResponseEntity<List<Accountant>> getAllAccountants() {
        List<Accountant> accountants = accountantServices.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(accountants);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.GET_ACCOUTANT_BY_CLIENT_EMAIL_PATH)
    public ResponseEntity<Accountant> getAccountantByClientEmail(@RequestParam String clientEmail) {
        return ResponseEntity.ok(accountantServices.getAccountantByClientEmail(clientEmail));
    }
}
