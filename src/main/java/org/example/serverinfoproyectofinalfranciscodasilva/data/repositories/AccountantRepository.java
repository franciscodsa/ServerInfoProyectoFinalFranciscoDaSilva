package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountantRepository extends ListCrudRepository<Accountant, String> {

    @Query("SELECT a FROM Accountant a WHERE a.email = (SELECT c.accountantEmail FROM Client c WHERE c.email = :clientEmail)")
    Optional<Accountant> findAccountantByClientEmail(@Param("clientEmail") String clientEmail);
}
