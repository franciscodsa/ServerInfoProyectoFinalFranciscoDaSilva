package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;
}
