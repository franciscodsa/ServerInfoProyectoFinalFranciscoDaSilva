package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.File;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends ListCrudRepository<File, Long> {

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO(f.id, f.fileName, f.description, f.date, b.id, b.income, b.expenses, b.iva) " +
            "FROM File f LEFT JOIN f.balance b WHERE f.clientEmail = :clientEmail AND f.invoiceType = :invoiceType")
    List<FileInfoDTO> getFilesInfoByInvoiceTypeAndClient(@Param("clientEmail") String clientEmail, @Param("invoiceType") InvoiceType invoiceType);

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO(f.id, f.fileName, f.description, f.date, b.id, b.income, b.expenses, b.iva) " +
            "FROM File f LEFT JOIN f.balance b WHERE f.clientEmail = :clientEmail")
    List<FileInfoDTO> getFilesInfoByClient(@Param("clientEmail") String clientEmail);

    void deleteAllByClientEmail(String clientEmail);
}
