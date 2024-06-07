package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FilesDBInfoDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.FileDBServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.serverinfoproyectofinalfranciscodasilva.common.ConstantesRoles.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileDBController {

    private final FileDBServices fileDBServices;


    /*@PostMapping("/upload")
    public ResponseEntity<AppMessage> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("clientEmail") String clientEmail,
            @RequestParam("invoiceType") InvoiceType invoiceType
    ) {
        fileDBServices.store(file, description, clientEmail, invoiceType);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Subido"));
    }*/

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @PostMapping("/upload")
    public ResponseEntity<AppMessage> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("clientEmail") String clientEmail,
            @RequestParam("invoiceType") InvoiceType invoiceType,
            @RequestParam("income") Double income,
            @RequestParam("expenses") Double expenses,
            @RequestParam("iva") Double iva
    ) {
        Balance balance = new Balance();
        balance.setIncome(income);
        balance.setExpenses(expenses);
        balance.setIva(iva);
        balance.setDate(LocalDateTime.now());

        fileDBServices.store(file, description, clientEmail, invoiceType, balance);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Subido"));
    }


    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        FilesDB filesDB = fileDBServices.getFile(fileId);

        if (filesDB != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(filesDB.getFileType())); // Establece el tipo MIME correcto
            headers.setContentLength(filesDB.getData().length); // Establece la longitud del contenido

            // Agregar el encabezado Content-Disposition con el nombre del archivo
            String fileName = filesDB.getFileName(); // Reemplaza esto con el nombre del archivo real
            headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(filesDB.getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("/info")
    public ResponseEntity<List<FilesDBInfoDTO>> getFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("/expensesInfo")
    public ResponseEntity<List<FilesDBInfoDTO>> getExpensesFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getExpensesFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT, ROLE_USER})
    @GetMapping("/incomeInfo")
    public ResponseEntity<List<FilesDBInfoDTO>> getIncomeFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getIncomeFilesByClient(clientEmail));
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_ACCOUNTANT})
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<AppMessage> deleteFile(@PathVariable Long fileId) {
        fileDBServices.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Eliminado"));
    }

}
