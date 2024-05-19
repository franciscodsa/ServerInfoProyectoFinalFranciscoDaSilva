package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends ListCrudRepository<Client, String> {

    List<Client> findAllByAccountantEmail(String accountantEmail);

    List<Client> findAllByAccountantEmailIsNull();

}
