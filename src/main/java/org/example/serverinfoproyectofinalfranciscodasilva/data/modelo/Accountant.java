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
@Table(name = "accountant")
public class Accountant {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;


    private String phone;


    private String firstName;

    private String lastName;

    private String dateOfBirth;

    @OneToMany(mappedBy = "accountant")
    private List<Client> clientList;

    @OneToMany(mappedBy = "accountant")
    private List<Chat> chatList;
}
