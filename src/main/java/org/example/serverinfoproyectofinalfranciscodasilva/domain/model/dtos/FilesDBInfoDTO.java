package org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class FilesDBInfoDTO {
    private Long id;
    private String fileName;
    private String description;
    private LocalDateTime date;
}
