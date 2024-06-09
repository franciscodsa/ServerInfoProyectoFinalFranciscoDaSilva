package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.User;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.UserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserServices userServices; // Mock del servicio

    @InjectMocks
    private UserController userController; // Inyección del controlador a probar

    private MockMvc mockMvc;

    @Test
    public void testUpdateUser() throws Exception {
        // Crear instancia de User
        User user = new User();
        user.setEmail("test@example.com");

        AppMessage appMessage = new AppMessage("Actualizado");

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Simular la llamada HTTP POST al endpoint /users/update
        mockMvc.perform(post("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Actualizado"));
    }

    @Test
    public void testGetUserById() throws Exception {
        // Crear instancia de User
        User user = new User();
        user.setEmail("test@example.com");

        // Configurar el mock del servicio para devolver el usuario
        when(userServices.getUserByEmail("test@example.com")).thenReturn(user);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Simular la llamada HTTP GET al endpoint /users/{email}
        mockMvc.perform(get("/users/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Simular la llamada HTTP DELETE al endpoint /users/{email}
        mockMvc.perform(delete("/users/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado"));
    }
}
