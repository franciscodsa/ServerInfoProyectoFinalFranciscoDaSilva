package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileDBController {

    private final FileDBServices fileDBServices;


    @PostMapping("/upload")
    public ResponseEntity<AppMessage> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("clientEmail") String clientEmail,
            @RequestParam("invoiceType") InvoiceType invoiceType
    ) {
        fileDBServices.store(file, description, clientEmail, invoiceType);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Subido"));
    }

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
            // Retorna la respuesta sin establecer el encabezado Content-Disposition
            return new ResponseEntity<>(filesDB.getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<List<FilesDBInfoDTO>> getFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getFilesByClient(clientEmail));
    }

    @GetMapping("/expensesInfo")
    public ResponseEntity<List<FilesDBInfoDTO>> getExpensesFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getExpensesFilesByClient(clientEmail));
    }

    @GetMapping("/incomeInfo")
    public ResponseEntity<List<FilesDBInfoDTO>> getIncomeFilesByClient(@RequestParam String clientEmail) {
        return ResponseEntity.ok(fileDBServices.getIncomeFilesByClient(clientEmail));
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<AppMessage> deleteFile(@PathVariable Long fileId) {
        fileDBServices.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Eliminado"));
    }

}
