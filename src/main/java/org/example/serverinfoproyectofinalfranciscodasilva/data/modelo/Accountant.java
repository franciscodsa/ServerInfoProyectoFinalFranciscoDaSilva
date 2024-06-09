package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "accountantBuilder")
@Table(name = "accountant")
public class Accountant extends User {


}
