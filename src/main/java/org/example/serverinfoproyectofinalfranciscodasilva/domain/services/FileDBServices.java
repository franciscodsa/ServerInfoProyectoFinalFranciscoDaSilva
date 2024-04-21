package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FileDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.FileDBRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileDBServices {

    private final FileDBRepository fileDBRepository;

    private final ClientRepository clientRepository;

    public FileDB store(MultipartFile file, String description, Long clientId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el ID: " + clientId));

            FileDB fileDB = new FileDB();

            fileDB.setFileName(fileName);
            fileDB.setFileType(file.getContentType());
            fileDB.setData(file.getBytes());
            fileDB.setDescription(description);
            fileDB.setClient(client);

            return fileDBRepository.save(fileDB);
        } catch (IOException e) {
            //todo colocar excepcion mapeada
            throw new RuntimeException(e);
        }
    }

    //todo modificar para sar el optional y enviar excepcion en caso de error
    public FileDB getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

}