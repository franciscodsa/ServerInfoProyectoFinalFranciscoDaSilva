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

    @ManyToOne
    private Contador contador;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "chat")
    private List<Mensaje> mensajeList;
}
