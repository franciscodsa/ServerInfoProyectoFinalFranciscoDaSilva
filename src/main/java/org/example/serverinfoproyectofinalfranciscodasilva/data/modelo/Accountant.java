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

    /*//todo ver si se puede quitar y en su lugar tener un joincolum en client nada mas
    @OneToMany(mappedBy = "accountant")
    private List<Client> clientList;

    @OneToMany(mappedBy = "accountant")
    private List<Chat> chatList;*/
}
