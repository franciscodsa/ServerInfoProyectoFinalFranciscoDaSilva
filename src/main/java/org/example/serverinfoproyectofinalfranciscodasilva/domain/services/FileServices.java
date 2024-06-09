package org.example.serverinfoproyectofinalfranciscodasilva.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.File;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.BalanceRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.ClientRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.data.repositories.FileRepository;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.common.ConstantesServices;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.FilesException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.UsersException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO;
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
public class FileServices {

    private final FileRepository fileRepository;
    private final BalanceRepository balanceRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public File store(MultipartFile file, String description, String clientEmail, InvoiceType invoiceType, Balance balance) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (!clientRepository.existsById(clientEmail)) {
                throw new UsersException(ConstantesServices.NO_EXISTE_CLIENTE_VERIFIQUE_EMAIL + clientEmail);
            }

            File filesDB = new File();
            filesDB.setFileName(fileName);
            filesDB.setFileType(file.getContentType());
            filesDB.setData(file.getBytes());
            filesDB.setDescription(description);
            filesDB.setClientEmail(clientEmail);
            filesDB.setInvoiceType(invoiceType);
            filesDB.setDate(LocalDateTime.now());

            // Save file information
            filesDB = fileRepository.save(filesDB);

            // Set the file reference in the balance
            balance.setFiles(filesDB);
            balance.setDate(LocalDateTime.now());
            balance.setIrpf(calculateIrpf(balance.getIncome(), balance.getExpenses())); // Calcular IRPF antes de guardar
            balance.setQuarter(getCurrentQuarter());

            // Save balance information
            balanceRepository.save(balance);

            return filesDB;
        } catch (IOException e) {
            throw new FilesException(ConstantesServices.ERROR_GUARDANDO_FACTURA);
        }
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public List<FileInfoDTO> getFilesByClient(String clientEmail) {
        return fileRepository.getFilesInfoByClient(clientEmail);
    }

    public List<FileInfoDTO> getExpensesFilesByClient(String clientEmail) {
        return fileRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.EXPENSE);
    }

    public List<FileInfoDTO> getIncomeFilesByClient(String clientEmail) {
        return fileRepository.getFilesInfoByInvoiceTypeAndClient(clientEmail, InvoiceType.INCOME);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new FilesException(ConstantesServices.ARCHIVO_NO_ENCONTRADO);
        }

        balanceRepository.deleteByFilesId(fileId);
        fileRepository.deleteById(fileId);
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
