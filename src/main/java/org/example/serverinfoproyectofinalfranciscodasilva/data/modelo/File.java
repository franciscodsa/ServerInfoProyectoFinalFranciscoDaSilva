package org.example.serverinfoproyectofinalfranciscodasilva.data.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.example.serverinfoproyectofinalfranciscodasilva.data.ConstantesData;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = ConstantesData.FILES)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private LocalDateTime date;

    @Lob
    @Column(columnDefinition = ConstantesData.LONGBLOB)
    private byte[] data;

    private String description;

    @Column(name = ConstantesData.CLIENT_EMAIL)
    private String clientEmail;

    private InvoiceType invoiceType;

    @OneToOne(mappedBy = ConstantesData.FILES)
    private Balance balance;

}
