package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.BalanceRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BalanceServices {
    private final ClientRepository clientRepository;

    private final BalanceRepository balanceRepository;

    public Balance save(Balance balance) {

      /*  Balance balanceToSave = new Balance();
        balanceToSave.setIrpf(calculateIrpf(balanceToSave.getRevenue(), balance.getExpenses()));
        balanceToSave.setIva(balance.getIva());
        balanceToSave.setDate(balance.getDate());*/
       /* balanceToSave.setClient(clientRepository.findById(balance.getClientEmail()).get());*/
        balance.setDate(LocalDateTime.now());
        balance.setIrpf(calculateIrpf(balance.getIncome(),balance.getExpenses())); // Calcular IRPF antes de guardar
        balance.setQuarter(getCurrentQuarter());

        return balanceRepository.save(balance);
    }

    public BalanceDTO findByClientIdAndYearAndQuarter(String clientEmail, int year, String quarter) {
        /*LocalDate startDate = calculateStartDate(year, quarter);
        LocalDate endDate = startDate.plusMonths(3).minusDays(1);*/

         BalanceDTO result = balanceRepository.findByClientEmailAndQuarterAndYear(clientEmail, quarter, year);
         if (result== null){
             result = new BalanceDTO(0.0, 0.0, 0.0, 0.0, clientEmail);
         }

        return result;
    }

    /*public void deleteByClientIdAndYearAndQuarter(String clientEmail, int year, String quarter) {
        *//*LocalDate startDate = calculateStartDate(year, quarter);
        LocalDate endDate = startDate.plusMonths(3).minusDays(1);*//*
        balanceRepository.deleteByClientEmailAndQuarterAndYear(clientEmail, quarter, year);
    }
*/
/*    private LocalDate calculateStartDate(int year, int quarter) {
        int month = (quarter - 1) * 3 + 1;
        return LocalDate.of(year, month, 1);
    }*/

    private double calculateIrpf(double revenue, double expenses) {
        double irpf;
        double difference = revenue - expenses;
        irpf = difference * 0.20; // 20% IRPF

        return irpf;
    }

    private String getCurrentQuarter() {
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
