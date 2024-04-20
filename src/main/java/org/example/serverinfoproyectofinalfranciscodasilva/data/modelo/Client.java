package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    private Long id;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private Double revenue;

    private Double expenses;

    private Double irpf;

    private Double iva;

    @ManyToOne
    private Accountant accountant;

    @ManyToOne
    private Chat chat;

    @OneToMany(mappedBy = "client")
    private List<Files> filesList;
}
