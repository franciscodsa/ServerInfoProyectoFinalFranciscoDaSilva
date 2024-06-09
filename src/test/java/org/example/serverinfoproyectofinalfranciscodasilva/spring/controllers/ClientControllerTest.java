package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Client;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.ClientServices;
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
public class ClientControllerTest {

    @Mock
    private ClientServices clientServices; // Mock del servicio

    @InjectMocks
    private ClientController clientController; // Inyección del controlador a probar

    private MockMvc mockMvc;

    @Test
    public void testAddClient() throws Exception {
        // Crear instancia de Client
        Client client = new Client();
        client.setEmail("test@example.com");
        AppMessage appMessage = new AppMessage("Cliente agregado");

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Simular la llamada HTTP POST al endpoint /addClient
        mockMvc.perform(post("/addClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cliente agregado"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        // Crear instancia de Client
        Client client = new Client();
        client.setEmail("test@example.com");

        AppMessage appMessage = new AppMessage("Actualizado");

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Simular la llamada HTTP POST al endpoint /clients/update
        mockMvc.perform(post("/clients/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Actualizado"));
    }

    @Test
    public void testGetAllClients() throws Exception {
        // Crear instancias de Client
        Client client1 = new Client();
        client1.setEmail("test1@example.com");

        Client client2 = new Client();
        client2.setEmail("test2@example.com");

        List<Client> clients = Arrays.asList(client1, client2);

        // Configurar el mock del servicio para devolver la lista de clientes
        when(clientServices.getAll()).thenReturn(clients);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Simular la llamada HTTP GET al endpoint /clients
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    public void testGetClientsWithNoAccountant() throws Exception {
        // Crear instancias de Client
        Client client1 = new Client();
        client1.setEmail("test1@example.com");
        client1.setAccountantEmail(null);

        Client client2 = new Client();
        client2.setEmail("test2@example.com");
        client2.setAccountantEmail(null);

        List<Client> clients = Arrays.asList(client1, client2);

        // Configurar el mock del servicio para devolver la lista de clientes sin contador
        when(clientServices.getClientsWithNoAccountant()).thenReturn(clients);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Simular la llamada HTTP GET al endpoint /clients/noAccountant
        mockMvc.perform(get("/clients/noAccountant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    public void testGetClientsByAccountantEmail() throws Exception {
        // Crear instancias de Client
        Client client1 = new Client();
        client1.setEmail("test1@example.com");
        client1.setAccountantEmail("accountant@example.com");

        Client client2 = new Client();
        client2.setEmail("test2@example.com");
        client2.setAccountantEmail("accountant@example.com");

        List<Client> clients = Arrays.asList(client1, client2);

        // Configurar el mock del servicio para devolver la lista de clientes por email del contador
        when(clientServices.getClientsByAccountantEmail("accountant@example.com")).thenReturn(clients);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Simular la llamada HTTP GET al endpoint /clients/byAccountant
        mockMvc.perform(get("/clients/byAccountant").param("accountantEmail", "accountant@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }
}
