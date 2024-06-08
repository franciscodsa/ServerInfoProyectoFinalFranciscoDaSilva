package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private Double income;

    private Double expenses;

    private Double irpf;

    private Double iva;

    private LocalDateTime date;

    private String quarter;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File files;
}
