package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ListCrudRepository<Client, String> {

    //todo no deberia de ser necesario porque para encontrarpor email se puede usar el user repository y listo, ve que te viene mejor (creo que sera mejor individual para cliente y accountant porque habra que hacer queries con los joins para no usar fetch eager)
  /*  @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.client.ClientInfoDTO(c.email, c.dateOfBirth, c.firstName, c.lastName, c.phone, a.email) FROM Client c LEFT JOIN c.accountant a WHERE c.email = :email")
    Optional<ClientInfoDTO> findByEmail(String email);

    @Query("SELECT new org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.client.ClientInfoDTO(c.email, c.dateOfBirth, c.firstName, c.lastName, c.phone, a.email) FROM Client c LEFT JOIN c.accountant a")
    List<ClientInfoDTO> findAllClients();*/

}
