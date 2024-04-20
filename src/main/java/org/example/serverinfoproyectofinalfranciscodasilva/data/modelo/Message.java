package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    private Long id;

    private String content;

    private LocalDateTime sendingDate;

    @ManyToOne
    private Chat chat;
}
