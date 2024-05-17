package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BalanceDTO {

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDate date;

    private String clientEmail;

    public BalanceDTO(Double revenue, Double expenses, Double irpf, Double iva, String clientEmail) {
        this.revenue = revenue;
        this.expenses = expenses;
        this.irpf = irpf;
        this.iva = iva;
        this.date= LocalDate.now();
        this.clientEmail = clientEmail;
    }
}
