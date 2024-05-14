package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "files")
public class FilesDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
