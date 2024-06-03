package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends ListCrudRepository<FilesDB, Long> {

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO(f.id, f.fileName, f.description, f.date, b.id, b.income, b.expenses, b.iva) " +
            "FROM FilesDB f LEFT JOIN f.balance b WHERE f.clientEmail = :clientEmail AND f.invoiceType = :invoiceType")
    List<FilesDBInfoDTO> getFilesInfoByInvoiceTypeAndClient(@Param("clientEmail") String clientEmail, @Param("invoiceType") InvoiceType invoiceType);

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO(f.id, f.fileName, f.description, f.date, b.id, b.income, b.expenses, b.iva) " +
            "FROM FilesDB f LEFT JOIN f.balance b WHERE f.clientEmail = :clientEmail")
    List<FilesDBInfoDTO> getFilesInfoByClient(@Param("clientEmail") String clientEmail);

    void deleteAllByClientEmail(String clientEmail);
}
