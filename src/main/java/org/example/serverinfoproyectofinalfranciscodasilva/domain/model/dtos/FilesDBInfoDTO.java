package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilesDBInfoDTO {
    private Long id;
    private String fileName;
    private String description;
    private LocalDateTime date;
    private Long balanceId;
    private Double total;
    private Double iva;

    public FilesDBInfoDTO(Long id, String fileName, String description, LocalDateTime date, Long balanceId, Double income, Double expenses, Double iva) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.date = date;
        this.balanceId = balanceId;
        this.iva = iva;
        this.total = (income != null ? income : 0) + (expenses != null ? expenses : 0);
    }
}


