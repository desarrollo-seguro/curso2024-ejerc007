package com.example.ejercicio007;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ejercicio007.controller.UsuarioController;
import com.example.ejercicio007.model.Usuario;
import com.example.ejercicio007.service.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Nombre", roles = {"ADMIN"})
    public void testGetUsuarioById_Authorized() throws Exception {
        Long UsuarioId = 1L;
        Usuario usuario = new Usuario();

        mockMvc.perform(get("/usuario/{id}", UsuarioId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsuarioById_Unauthorized() throws Exception {
        Long UsuarioId = 1L;

        mockMvc.perform(get("/usuario/{id}", UsuarioId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "Nombre", roles = {"ESTE_NO"})
    public void testGetUsuarioById_MethodNotAllowed() throws Exception {
        Long UsuarioId = 1L;
        Usuario usuario = new Usuario();

        mockMvc.perform(get("/usuario/{id}", UsuarioId))
                .andExpect(status().isForbidden());
    }    
}
