package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Accountant;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.AccountantServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountantControllerTest {

    @Mock
    private AccountantServices accountantServices; // Mock del servicio

    @InjectMocks
    private AccountantController accountantController; // Inyección del controlador a probar

    private MockMvc mockMvc;

    @Test
    public void testAddAccountant() throws Exception {
        // Crear instancia de Accountant y AppMessage
        Accountant accountant = new Accountant();
        accountant.setEmail("test@example.com");
        AppMessage appMessage = new AppMessage("Contador agregado");

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(accountantController).build();

        // Simular la llamada HTTP POST al endpoint /accountant/add
        mockMvc.perform(post("/accountant/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contador agregado"));
    }

    @Test
    public void testGetAllAccountants() throws Exception {
        // Crear instancias de Accountant
        Accountant accountant1 = new Accountant();
        accountant1.setEmail("test1@example.com");

        Accountant accountant2 = new Accountant();
        accountant2.setEmail("test2@example.com");

        List<Accountant> accountants = Arrays.asList(accountant1, accountant2);

        // Configurar el mock del servicio para devolver la lista de contadores
        when(accountantServices.getAll()).thenReturn(accountants);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(accountantController).build();

        // Simular la llamada HTTP GET al endpoint /accountant
        mockMvc.perform(get("/accountant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    public void testGetAccountantByClientEmail() throws Exception {
        // Crear instancia de Accountant
        Accountant accountant = new Accountant();
        accountant.setEmail("test@example.com");

        // Configurar el mock del servicio para devolver el contador
        when(accountantServices.getAccountantByClientEmail("client@example.com")).thenReturn(accountant);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(accountantController).build();

        // Simular la llamada HTTP GET al endpoint /accountant/byClientEmail
        mockMvc.perform(get("/accountant/byClientEmail").param("clientEmail", "client@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
