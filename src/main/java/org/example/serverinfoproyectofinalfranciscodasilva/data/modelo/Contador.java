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
@Table(name = "accountant")
public class Contador {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;


    private String phone;


    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "accountant")
    private List<Cliente> clienteList;

    @OneToMany(mappedBy = "accountant")
    private List<Chat> chatList;
}
