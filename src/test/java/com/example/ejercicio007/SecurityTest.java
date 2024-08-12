package com.example.ejercicio007;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ejercicio007.controller.LoginController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class)
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password")
    public void whenUnauthenticated_thenAuthorized() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());  // Ahora devuelve 200
    }

    @Test
    public void whenUnauthenticated_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());  // Ahora devuelve 401
    }
}


