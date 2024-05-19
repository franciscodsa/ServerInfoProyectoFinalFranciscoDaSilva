package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime date;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    private String description;

    @Column(name = "client_email")
    private String clientEmail;

    private InvoiceType invoiceType;
}
