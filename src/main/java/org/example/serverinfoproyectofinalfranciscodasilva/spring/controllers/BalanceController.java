package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.BalanceServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceServices balanceServices;

    @GetMapping("/quarter")
    public ResponseEntity<BalanceDTO> getBalancesByClientIdAndYearAndQuarter(
            @RequestParam String clientEmail,
            @RequestParam int year,
            @RequestParam String quarter) {
        BalanceDTO balance = balanceServices.findByClientIdAndYearAndQuarter(clientEmail, year, quarter);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @DeleteMapping("/delete-quarter")
    public ResponseEntity<Void> deleteBalancesByClientIdAndYearAndQuarter(
            @RequestParam String clientEmail,
            @RequestParam int year,
            @RequestParam String quarter) {
        balanceServices.deleteByClientIdAndYearAndQuarter(clientEmail, year, quarter);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public Balance addBalance(@RequestBody Balance balance){
        return balanceServices.save(balance);
    }
}
