package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.BalanceRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.FileDBRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.FilesException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDBServices {

    private final FileDBRepository fileDBRepository;
    private final BalanceRepository balanceRepository;
    private final ClientRepository clientRepository;


    /*@Transactional
    public FilesDB store(MultipartFile file, String description, String clientEmail, InvoiceType invoiceType, Balance balance) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
           if (!clientRepository.existsById(clientEmail)){
               throw new UsersException("No existe cliente, verifique email: " + clientEmail);
            }


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
    }*/

    @Transactional
    public FilesDB store(MultipartFile file, String description, String clientEmail, InvoiceType invoiceType, Balance balance) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (!clientRepository.existsById(clientEmail)) {
                throw new UsersException("No existe cliente, verifique email: " + clientEmail);
            }

            FilesDB filesDB = new FilesDB();
            filesDB.setFileName(fileName);
            filesDB.setFileType(file.getContentType());
            filesDB.setData(file.getBytes());
            filesDB.setDescription(description);
            filesDB.setClientEmail(clientEmail);
            filesDB.setInvoiceType(invoiceType);
            filesDB.setDate(LocalDateTime.now());

            // Save file information
            filesDB = fileDBRepository.save(filesDB);

            // Set the file reference in the balance
            balance.setFiles(filesDB);
            balance.setDate(LocalDateTime.now());
            balance.setClientEmail(clientEmail);
            balance.setDate(LocalDateTime.now());
            balance.setIrpf(calculateIrpf(balance.getIncome(),balance.getExpenses())); // Calcular IRPF antes de guardar
            balance.setQuarter(getCurrentQuarter());

            // Save balance information
            balanceRepository.save(balance);

            return filesDB;
        } catch (IOException e) {
            throw new FilesException("Error guardando factura");
        }
    }


    //todo modificar para sar el optional y enviar excepcion en caso de error
    public FilesDB getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public List<FilesDBInfoDTO> getFilesByClient(String clientEmail) {
        return fileDBRepository.getFilesInfoByClient(clientEmail);
    }

    public List<FilesDBInfoDTO> getExpensesFilesByClient(String clientEmail) {
        return fileDBRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.EXPENSE);
    }

    public List<FilesDBInfoDTO> getIncomeFilesByClient(String clientEmail) {
        return fileDBRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.INCOME);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        if (!fileDBRepository.existsById(fileId)) {
            throw new FilesException("Archivo no encontrado");
        }

        balanceRepository.deleteByFilesId(fileId);
        fileDBRepository.deleteById(fileId);
    }

    private double calculateIrpf(double revenue, double expenses) {
        double irpf;
        double difference = revenue - expenses;
        irpf = difference * 0.20; // 20% IRPF

        return irpf;
    }

    private String getCurrentQuarter() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        String quarter;

        if (month >= 1 && month <= 3) {
            quarter = "T1";
        } else if (month >= 4 && month <= 6) {
            quarter = "T2";
        } else if (month >= 7 && month <= 9) {
            quarter = "T3";
        } else {
            quarter = "T4";
        }

        return quarter;
    }
}
