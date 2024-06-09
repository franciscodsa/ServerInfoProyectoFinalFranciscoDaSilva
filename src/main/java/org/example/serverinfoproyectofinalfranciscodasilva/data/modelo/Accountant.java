package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.serverinfoproyectofinalfranciscodasilva.data.ConstantesData;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "accountantBuilder")
@Table(name = ConstantesData.ACCOUNTANT)
public class Accountant extends User {


}
