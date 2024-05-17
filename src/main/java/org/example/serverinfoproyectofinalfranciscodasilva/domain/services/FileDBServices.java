package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.FileDBRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDBServices {

    private final FileDBRepository fileDBRepository;

    private final ClientRepository clientRepository;

    public FilesDB store(MultipartFile file, String description, String clientEmail, InvoiceType invoiceType) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Client client = clientRepository.findById(clientEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el ID: " + clientEmail));

            FilesDB filesDB = new FilesDB();

            filesDB.setFileName(fileName);
            filesDB.setFileType(file.getContentType());
            filesDB.setData(file.getBytes());
            filesDB.setDescription(description);
            filesDB.setClientEmail(client.getEmail());
            filesDB.setInvoiceType(invoiceType);

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
        return null;
    }

    public List<FilesDBInfoDTO> getExpensesFilesByClient(String clientEmail){
        final List<FilesDBInfoDTO> expenseFilesInfo = fileDBRepository.getExpenseFilesInfo(clientEmail, InvoiceType.EXPENSE);
        return expenseFilesInfo;
    }
    public List<FilesDBInfoDTO> getIncomeFilesByClient(String clientEmail){
        return null;
    }

}
