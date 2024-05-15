package org.example.serverinfoproyectofinalfranciscodasilva.data.repositories;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends ListCrudRepository<FilesDB, Long> {

    //todo haz metodo para que te devuelva una lista de todos los archivos sin los archivos, necesitaras un dto
}
