package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.BalanceRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.common.ConstantesServices;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.BalanceException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BalanceServices {

    private final BalanceRepository balanceRepository;

    public Balance update(Balance balance) {

        Balance toUpdate = balanceRepository.findById(balance.getId()).orElseThrow(() -> new BalanceException(ConstantesServices.BALANCE_INEXISTENTE));

        toUpdate.setIncome(balance.getIncome());
        toUpdate.setExpenses(balance.getExpenses());
        toUpdate.setIva(balance.getIva());
        toUpdate.setDate(LocalDateTime.now());
        toUpdate.setIrpf(calculateIrpf(balance.getIncome(), balance.getExpenses())); // Calcular IRPF antes de guardar
        toUpdate.setQuarter(getCurrentQuarter());


        return balanceRepository.save(toUpdate);
    }

    public BalanceDTO findByClientIdAndYearAndQuarter(String clientEmail, int year, String quarter) {
        BalanceDTO result = balanceRepository.findByClientEmailAndQuarterAndYear(clientEmail, quarter, year);
        if (result == null) {
            result = new BalanceDTO(0.0, 0.0, 0.0, 0.0, clientEmail);
        }

        return result;
    }

    private double calculateIrpf(double revenue, double expenses) {
        double irpf;
        double difference = revenue - expenses;
        irpf = difference * 0.20; // 20% IRPF

        return irpf;
    }

    public String getCurrentQuarter() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        String quarter;

        if (month >= 1 && month <= 3) {
            quarter = "T1";
        } else if (month >= 4 && month <= 6) {
            quarter = "T2";
        } else if (month >= 7 && month <= 9) {
            quarter = "T3";
        } else {
            quarter = "T4";
        }

        return quarter;
    }
}
