package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BalanceDto {

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDate date;

    private String clientEmail;

    public BalanceDto(Double revenue, Double expenses, Double irpf, Double iva, String clientEmail) {
        this.revenue = revenue;
        this.expenses = expenses;
        this.irpf = irpf;
        this.iva = iva;
        this.date= LocalDate.now();
        this.clientEmail = clientEmail;
    }
}
