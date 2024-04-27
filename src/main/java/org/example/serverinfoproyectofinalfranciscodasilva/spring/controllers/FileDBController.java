package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Archivo;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.FileDBServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FileDBController {

    private final FileDBServices fileDBServices;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("description")String description, @RequestParam Long clientId){
        try {
            fileDBServices.store(file,description, clientId);

            return ResponseEntity.status(HttpStatus.OK).body("Uploaded");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed");
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId){
        Archivo archivo = fileDBServices.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getFileName() + "\"")
                .body(archivo.getData());
    }

}
