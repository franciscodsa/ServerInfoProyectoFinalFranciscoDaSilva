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
@AllArgsConstructor
@Table(name = "accountant")
public class Accountant extends User {

    @OneToMany(mappedBy = "accountant")
    private List<Client> clientList;

    @OneToMany(mappedBy = "accountant")
    private List<Chat> chatList;
}
