package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDate date;

    private String quarter;

    @Column(name = "client_email")
    private String clientEmail;
 /*   @ManyToOne
    private Client client;*/
}
