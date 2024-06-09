package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "clientBuilder")
@AllArgsConstructor
@Table(name = "clients")
public class Client extends User {

    @Column(name = "accountant_email", nullable = true)
    private String accountantEmail;


}
