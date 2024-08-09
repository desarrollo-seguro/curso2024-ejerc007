package com.example.ejercicio007;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenLoggedInUserAccessesHome_thenOk() throws Exception {
        // Simula un inicio de sesión exitoso
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                .user("user")
                .password("password"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenAnonymousAccessesHome_thenRedirectsToLogin() throws Exception {
        // Intenta acceder a la página protegida sin autenticación
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    public void whenAnonymousAccessesLogin_thenOk() throws Exception {
        // Accede a la página de inicio de sesión sin autenticación
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


