package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.FilesDB;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.FileDBServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FileDBController {

    private final FileDBServices fileDBServices;


    @PostMapping("/upload")
    public ResponseEntity<AppMessage> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("description")String description, @RequestParam String clientEmail){
        try {
            fileDBServices.store(file,description, clientEmail);

            return ResponseEntity.status(HttpStatus.OK).body(new AppMessage("Uploaded"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new AppMessage("Failed"));
        }
    }

  /*  @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId){
        FilesDB filesDB = fileDBServices.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filesDB.getFileName() + "\"")
                .body(filesDB.getData());
    }*/
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

}
