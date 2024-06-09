package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.BalanceDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.BalanceServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BalanceControllerTest {

    @Mock
    private BalanceServices balanceServices; // Mock del servicio

    @InjectMocks
    private BalanceController balanceController; // Inyección del controlador a probar

    private MockMvc mockMvc;

    @Test
    public void testGetBalancesByClientIdAndYearAndQuarter() throws Exception {
        // Crear instancia de BalanceDTO
        BalanceDTO balanceDTO = new BalanceDTO(1000.0, 500.0, 200.0, 300.0, "test@example.com");

        int year = LocalDate.now().getYear();
        String quarter = "T1";

        // Configurar el mock del servicio para devolver el balance
        when(balanceServices.findByClientIdAndYearAndQuarter("test@example.com", year, quarter))
                .thenReturn(balanceDTO);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();

        // Simular la llamada HTTP GET al endpoint /balances/quarter
        mockMvc.perform(get("/balances/quarter")
                        .param("clientEmail", "test@example.com")
                        .param("year", String.valueOf(year))
                        .param("quarter", quarter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value("test@example.com"))
                .andExpect(jsonPath("$.income").value(1000.0))
                .andExpect(jsonPath("$.expenses").value(500.0))
                .andExpect(jsonPath("$.iva").value(300.0))
                .andExpect(jsonPath("$.irpf").value(200.0));
    }

    @Test
    public void testUpdateBalance() throws Exception {
        // Crear instancia de Balance
        Balance balance = new Balance();
        balance.setId(1L);
        balance.setIncome(1000.0);
        balance.setExpenses(500.0);
        balance.setIva(200.0);

        AppMessage appMessage = new AppMessage("Actualizado");

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();

        // Simular la llamada HTTP POST al endpoint /balances/update
        mockMvc.perform(post("/balances/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"income\":1000.0,\"expenses\":500.0,\"iva\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Actualizado"));
    }
}
