package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;

import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
@Repository
public interface BalanceRepository extends ListCrudRepository<Balance, Long> {

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO(SUM(b.income), SUM(b.expenses), SUM(b.irpf), SUM(b.iva), b.clientEmail) FROM Balance b WHERE b.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year GROUP BY b.clientEmail")
    BalanceDTO findByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Balance b WHERE b.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year")
    void deleteByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

    void deleteAllByClientEmail(String email);
}
*/


@Repository
public interface BalanceRepository extends ListCrudRepository<Balance, Long> {

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO(SUM(b.income), SUM(b.expenses), SUM(b.irpf), SUM(b.iva), f.clientEmail) " +
            "FROM Balance b JOIN b.files f WHERE f.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year GROUP BY f.clientEmail")
    BalanceDTO findByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

/*    @Transactional
    @Modifying
    @Query("DELETE FROM Balance b WHERE b.id IN " +
            "(SELECT sub.id FROM (SELECT b.id FROM Balance b JOIN b.files f WHERE f.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year) AS sub)")
    void deleteByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Balance b WHERE b.id IN " +
            "(SELECT sub.id FROM (SELECT b.id FROM Balance b JOIN b.files f WHERE f.clientEmail = :clientEmail) AS sub)")
    void deleteAllByClientEmail(String clientEmail);*/

    void deleteByFilesId(Long fileId);
}

