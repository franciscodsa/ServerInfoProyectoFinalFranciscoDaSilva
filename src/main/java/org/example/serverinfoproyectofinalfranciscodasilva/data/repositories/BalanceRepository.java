package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;

import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BalanceRepository extends ListCrudRepository<Balance, Long> {

   /* @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.balance.BalanceDto(SUM(b.revenue), SUM(b.expenses), SUM(b.irpf), SUM(b.iva), b.clientEmail) FROM Balance b WHERE b.clientEmail = :clientEmail AND b.date BETWEEN :startDate AND :endDate GROUP BY b.clientEmail")
    BalanceDto findByClientEmailAndDateBetween(String clientEmail, LocalDate startDate, LocalDate endDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Balance b WHERE b.clientEmail = :clientEmail AND b.date BETWEEN :startDate AND :endDate")
    void deleteByClientEmailAndDateBetween(String clientEmail, LocalDate startDate, LocalDate endDate);*/

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO(SUM(b.revenue), SUM(b.expenses), SUM(b.irpf), SUM(b.iva), b.clientEmail) FROM Balance b WHERE b.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year GROUP BY b.clientEmail")
    BalanceDTO findByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Balance b WHERE b.clientEmail = :clientEmail AND b.quarter = :quarter AND YEAR(b.date) = :year")
    void deleteByClientEmailAndQuarterAndYear(String clientEmail, String quarter, int year);

    void deleteAllByClientEmail(String email);
}
