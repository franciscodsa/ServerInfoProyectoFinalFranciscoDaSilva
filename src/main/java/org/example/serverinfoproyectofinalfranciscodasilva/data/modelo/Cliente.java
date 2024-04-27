package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "clients")
public class Cliente {

    @Id
    private Long id;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    @ManyToOne
    private Contador contador;

    @ManyToOne
    private Chat chat;

    @OneToMany(mappedBy = "client")
    private List<Archivo> archivoList;
}
