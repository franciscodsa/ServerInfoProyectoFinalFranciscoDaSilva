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
@Builder(builderMethodName = "clientBuilder")
@AllArgsConstructor
@Table(name = "clients")
public class Client extends User{

    @Column(name = "accountant_email", nullable = true)
    private String accountantEmail;


}
