package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "accountantBuilder")
@Table(name = "accountant")
public class Accountant extends User {


}
