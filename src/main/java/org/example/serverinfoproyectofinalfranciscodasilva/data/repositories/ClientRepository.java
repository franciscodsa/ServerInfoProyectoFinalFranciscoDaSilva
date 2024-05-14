package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.ClientDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository  extends ListCrudRepository<Client, Long> {

    //todo no deberia de ser necesario porque para encontrarpor email se puede usar el user repository y listo, ve que te viene mejor
    Optional<Client> findByEmail(String email);

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.ClientDTO(c.id, c.dateOfBirth, c.email, c.firstName, c.lastName, c.phone) " +
            "FROM Client c")
    List<ClientDTO> findAllClients();
}
