package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.serverinfoproyectofinalfranciscodasilva.data.ConstantesData;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = ConstantesData.BALANCE)
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
    @JoinColumn(name = ConstantesData.FILE_ID)
    private File files;
}
