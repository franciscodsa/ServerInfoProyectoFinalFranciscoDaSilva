package org.example.serverinfoproyectofinalfranciscodasilva.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppMessage {

    private String message;
    private LocalDateTime fecha;

    public AppMessage(String message) {
        this.message = message;
        this.fecha = LocalDateTime.now();
    }
}
