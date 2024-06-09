package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FileInfoDTO {
    private Long id;
    private String fileName;
    private String description;
    private LocalDateTime date;
    private Long balanceId;
    private Double total;
    private Double iva;

    public FileInfoDTO(Long id, String fileName, String description, LocalDateTime date, Long balanceId, Double income, Double expenses, Double iva) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.date = date;
        this.balanceId = balanceId;
        this.iva = iva;
        this.total = (income != null ? income : 0) + (expenses != null ? expenses : 0);
    }
}


