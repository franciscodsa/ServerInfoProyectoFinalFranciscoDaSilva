package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.AllArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.AccountantServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/accountant")
public class AccountantController {

    private final AccountantServices accountantServices;

    @PostMapping()
    public ResponseEntity<AppMessage> addAccountant(@RequestBody Accountant accountant) {
        accountantServices.add(accountant);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Contador agregado"));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Accountant> getAccountantByEmail(@PathVariable String email) {
        Accountant accountant = accountantServices.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(accountant);
    }

    @GetMapping()
    public ResponseEntity<List<Accountant>> getAllAccountants() {
        List<Accountant> accountants = accountantServices.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(accountants);
    }

    //todo: esto no va a hacer falta porque se eliminaran con el endpoint de users
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<AppMessage> deleteAccountant(@PathVariable String email) {
        accountantServices.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Accountant deleted successfully"));
    }

    @PostMapping("/update")
    public ResponseEntity<Accountant> updateAccountant(@RequestBody Accountant accountant) {
        return ResponseEntity.ok(accountantServices.updateAccountant(accountant));
    }

}
