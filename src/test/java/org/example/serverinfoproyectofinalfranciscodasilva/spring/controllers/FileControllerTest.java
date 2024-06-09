package org.example.serverinfoproyectofinalfranciscodasilva.spring.controllers;

import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.Balance;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.File;
import org.example.serverinfoproyectofinalfranciscodasilva.data.modelo.InvoiceType;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.dtos.FileInfoDTO;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.services.FileServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @Mock
    private FileServices fileServices; // Mock del servicio

    @InjectMocks
    private FileController fileController; // Inyección del controlador a probar

    private MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        // Crear instancia de MockMultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());

        // Crear instancia de Balance
        Balance balance = new Balance();
        balance.setIncome(1000.0);
        balance.setExpenses(500.0);
        balance.setIva(200.0);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        // Simular la llamada HTTP POST al endpoint /files/upload
        mockMvc.perform(multipart("/files/upload")
                        .file(mockFile)
                        .param("description", "Test description")
                        .param("clientEmail", "test@example.com")
                        .param("invoiceType", InvoiceType.INCOME.toString())
                        .param("income", "1000.0")
                        .param("expenses", "500.0")
                        .param("iva", "200.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Subido"));
    }

    @Test
    public void testGetFile() throws Exception {
        // Crear instancia de File
        File file = new File();
        file.setFileName("test.txt");
        file.setFileType("text/plain");
        file.setData("Test file content".getBytes());

        // Configurar el mock del servicio para devolver el archivo
        when(fileServices.getFile(1L)).thenReturn(file);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        // Simular la llamada HTTP GET al endpoint /files/download/{fileId}
        mockMvc.perform(get("/files/download/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "text/plain"))
                .andExpect(header().string(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getData().length)))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"attachment\"; filename=\"test.txt\""))
                .andExpect(content().bytes(file.getData()));
    }

    @Test
    public void testGetFilesByClient() throws Exception {
        // Crear instancias de FilesDBInfoDTO
        FileInfoDTO file1 = new FileInfoDTO();
        file1.setFileName("test1.txt");

        FileInfoDTO file2 = new FileInfoDTO();
        file2.setFileName("test2.txt");

        List<FileInfoDTO> files = Arrays.asList(file1, file2);

        // Configurar el mock del servicio para devolver la lista de archivos
        when(fileServices.getFilesByClient("test@example.com")).thenReturn(files);

        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        // Simular la llamada HTTP GET al endpoint /files/info
        mockMvc.perform(get("/files/info").param("clientEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileName").value("test1.txt"))
                .andExpect(jsonPath("$[1].fileName").value("test2.txt"));
    }

    @Test
    public void testDeleteFile() throws Exception {
        // Configurar MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        // Simular la llamada HTTP DELETE al endpoint /files/delete/{fileId}
        mockMvc.perform(delete("/files/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado"));
    }
}
