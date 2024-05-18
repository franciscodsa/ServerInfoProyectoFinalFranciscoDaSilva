package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/accountant")
public class AccountantController {


    private ContadorRepository contadorRepository;

    @PostMapping
    public Contador addContador(@RequestBody Contador contador) {
        return contadorRepository.save(contador);
    }

    @GetMapping
    public List<Contador> getAllContadores() {
        final List<Contador> all = contadorRepository.findAll();
        return all;
    }
}
