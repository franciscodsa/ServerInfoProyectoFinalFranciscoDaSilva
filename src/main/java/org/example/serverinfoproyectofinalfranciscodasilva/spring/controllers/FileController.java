package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.File;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.FileServices;
import org.example.serverinfoproyectofinalfranciscodasilva.spring.common.Constantes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.spring.common.ConstantesRoles.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(Constantes.FILES_PATH)
public class FileController {

    private final FileServices fileServices;

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @PostMapping(Constantes.UPLOAD_FILES_PATH)
    public ResponseEntity<AppMessage> uploadFile(
            @RequestParam(Constantes.FILE_PARAM) MultipartFile file,
            @RequestParam(Constantes.DESCRIPTION_PARAM) String description,
            @RequestParam(Constantes.CLIENT_EMAIL_PARAM) String clientEmail,
            @RequestParam(Constantes.INVOICE_TYPE_PARAM) InvoiceType invoiceType,
            @RequestParam(Constantes.INCOME_PARAM) Double income,
            @RequestParam(Constantes.EXPENSES_PARAM) Double expenses,
            @RequestParam(Constantes.IVA_PARAM) Double iva
    ) {
        Balance balance = new Balance();
        balance.setIncome(income);
        balance.setExpenses(expenses);
        balance.setIva(iva);
        balance.setDate(LocalDateTime.now());

        fileServices.store(file, description, clientEmail, invoiceType, balance);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage(Constantes.SUBIDO));
    }


    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.DOWNLOAD_BY_FILE_ID_PATH)
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        File file = fileServices.getFile(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getFileType())); // Establece el tipo MIME correcto
        headers.setContentLength(file.getData().length); // Establece la longitud del contenido

        // Agregar el encabezado Content-Disposition con el nombre del archivo
        String fileName = file.getFileName(); // Reemplaza esto con el nombre del archivo real
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);

    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.FILES_INFO_PATH)
    public ResponseEntity<List<FileInfoDTO>> getFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileServices.getFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.EXPENSES_FILES_INFO_PATH)
    public ResponseEntity<List<FileInfoDTO>> getExpensesFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileServices.getExpensesFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping(Constantes.INCOME_FILES_INFO_PATH)
    public ResponseEntity<List<FileInfoDTO>> getIncomeFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileServices.getIncomeFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT})
    @DeleteMapping(Constantes.DELETE_FILE_BY_ID_PATH)
    public ResponseEntity<AppMessage> deleteFile(@PathVariable Long fileId) {
        fileServices.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage(Constantes.ELIMINADO));
    }

}
