package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.serverinfoproyectofinalfranciscodasilva.data.ConstantesData;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "clientBuilder")
@AllArgsConstructor
@Table(name = ConstantesData.CLIENTS)
public class Client extends User {

    @Column(name = ConstantesData.ACCOUNTANT_EMAIL, nullable = true)
    private String accountantEmail;


}
