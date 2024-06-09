package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BalanceRepository extends ListCrudRepository<Balance, Long> {

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO(SUM(b.income), SUM(b.expenses), SUM(b.irpf), SUM(b.iva), f.clientEmail) " +
            "FROM Balance b JOIN b.files f WHERE f.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year GROUP BY f.clientEmail")
    BalanceDTO findByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);


    void deleteByFilesId(Long fileId);
}

