package org.example.serverinfoproyectofinalfranciscodasilva.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppError {

    private String message;
    private LocalDateTime fecha;

    public AppError(String message) {
        this.message = message;
        this.fecha = LocalDateTime.now();
    }
}
