package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Cliente;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Archivo;
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

    public Archivo store(MultipartFile file, String description, Long clientId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Cliente cliente = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el ID: " + clientId));

            Archivo archivo = new Archivo();

            archivo.setFileName(fileName);
            archivo.setFileType(file.getContentType());
            archivo.setData(file.getBytes());
            archivo.setDescription(description);
            archivo.setCliente(cliente);

            return fileDBRepository.save(archivo);
        } catch (IOException e) {
            //todo colocar excepcion mapeada
            throw new RuntimeException(e);
        }
    }

    //todo modificar para sar el optional y enviar excepcion en caso de error
    public Archivo getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

}
