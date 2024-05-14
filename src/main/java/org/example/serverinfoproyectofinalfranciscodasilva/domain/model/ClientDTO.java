package org.example.serverinfoproyectofinalfranciscodasilva.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private Long id;
    private LocalDate dateOfBirth;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
