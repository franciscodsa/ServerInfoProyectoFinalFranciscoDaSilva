package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "files")
public class Files {

    @Id
    private Long id;
    private String fileName;
    private String fileType;

    private byte[] data;
    private String description;

    @ManyToOne
    private Client client;

}
