package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.BalanceServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.serverinfoproyectofinalfranciscodasilva.common.ConstantesRoles.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceServices balanceServices;


    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("/quarter")
    public ResponseEntity<BalanceDTO> getBalancesByClientIdAndYearAndQuarter(
            @RequestParam String clientEmail,
            @RequestParam int year,
            @RequestParam String quarter) {
        BalanceDTO balance = balanceServices.findByClientIdAndYearAndQuarter(clientEmail, year, quarter);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @PostMapping("/update")
    public ResponseEntity<AppMessage> updateBalance(@RequestBody Balance balance){
        balanceServices.update(balance);
        return new ResponseEntity<>(new AppMessage("Actualizado"), HttpStatus.OK);
    }
}
