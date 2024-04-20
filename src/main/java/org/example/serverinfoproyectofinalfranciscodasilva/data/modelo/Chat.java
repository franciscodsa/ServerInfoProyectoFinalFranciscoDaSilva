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
    private Accountant accountant;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "chat")
    private List<Message> messageList;
}
