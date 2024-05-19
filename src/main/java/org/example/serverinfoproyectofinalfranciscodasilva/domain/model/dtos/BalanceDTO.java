package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class BalanceDTO {

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDateTime date;

    private String clientEmail;

    public BalanceDTO(Double revenue, Double expenses, Double irpf, Double iva, String clientEmail) {
        this.revenue = revenue;
        this.expenses = expenses;
        this.irpf = irpf;
        this.iva = iva;
        this.date= LocalDateTime.now();
        this.clientEmail = clientEmail;
    }
}
