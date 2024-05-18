package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.AllArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
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

    @PostMapping("/add")
    public ResponseEntity<AppMessage> addAccountant(@RequestBody Accountant accountant) {
        accountantServices.add(accountant);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Accountant added successfully"));
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<Accountant> getAccountantByEmail(@PathVariable String email) {
        Accountant accountant = accountantServices.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(accountant);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Accountant>> getAllAccountants() {
        List<Accountant> accountants = accountantServices.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(accountants);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<AppMessage> deleteAccountant(@PathVariable String email) {
        accountantServices.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Accountant deleted successfully"));
    }

}
