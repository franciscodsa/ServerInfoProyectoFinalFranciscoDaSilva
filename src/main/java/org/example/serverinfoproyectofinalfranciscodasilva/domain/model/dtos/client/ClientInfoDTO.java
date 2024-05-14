package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ClientInfoDTO {
    private String email;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String phone;
    private String accountantEmail;
}
