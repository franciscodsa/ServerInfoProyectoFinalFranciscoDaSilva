package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.FileDBRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.FilesException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDBServices {

    private final FileDBRepository fileDBRepository;

    private final ClientRepository clientRepository;

    public FilesDB store(MultipartFile file, String description, String clientEmail, InvoiceType invoiceType) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
           if (!clientRepository.existsById(clientEmail)){
               throw new UsersException("No existe cliente, verifique email: " + clientEmail);
            }

           /* Client client = clientRepository.findById(clientEmail)
                    .orElseThrow(() -> new UsersException("Cliente no encontrado con el ID: " + clientEmail));*/

            FilesDB filesDB = new FilesDB();

            filesDB.setFileName(fileName);
            filesDB.setFileType(file.getContentType());
            filesDB.setData(file.getBytes());
            filesDB.setDescription(description);
            filesDB.setClientEmail(clientEmail);
            filesDB.setInvoiceType(invoiceType);
            filesDB.setDate(LocalDateTime.now());

            return fileDBRepository.save(filesDB);
        } catch (IOException e) {
            //todo colocar excepcion mapeada
            throw new RuntimeException(e);
        }
    }

    //todo modificar para sar el optional y enviar excepcion en caso de error
    public FilesDB getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public List<FilesDBInfoDTO> getFilesByClient(String clientEmail){
        return fileDBRepository.getFilesInfoByClient(clientEmail);
    }

    public List<FilesDBInfoDTO> getExpensesFilesByClient(String clientEmail){
        return fileDBRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.EXPENSE);
    }
    public List<FilesDBInfoDTO> getIncomeFilesByClient(String clientEmail){
        return fileDBRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.INCOME);
    }

    public void deleteFile(Long fileId) {
        if (!fileDBRepository.existsById(fileId)) {
            throw new FilesException("Archivo no encontrado");
        }

        fileDBRepository.deleteById(fileId);
    }
}
