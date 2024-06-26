package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BalanceDTO {

    private Double income;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDateTime date;

    private String clientEmail;

    public BalanceDTO(Double income, Double expenses, Double irpf, Double iva, String clientEmail) {
        this.income = income;
        this.expenses = expenses;
        this.irpf = irpf;
        this.iva = iva;
        this.date = LocalDateTime.now();
        this.clientEmail = clientEmail;
    }
}
