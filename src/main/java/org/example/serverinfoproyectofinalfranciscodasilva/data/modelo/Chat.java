package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "chats")
public class Chat {

    @Id
    private Long id;

    @Column(name = "accountant_email")
    private String accountantEmail;

    @Column(name = "client_email")
    private String clientEmail;

   /* @ManyToOne
    private Accountant accountant;

    @OneToOne
    private Client client;*/

    @OneToMany(mappedBy = "chat")
    private List<Mensaje> mensajeList;
}
