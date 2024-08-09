package com.example.ejercicio007.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/login")
    public String login() {
        return "login"; // nombre del archivo de la vista de inicio de sesión
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // nombre del archivo de la vista de la página de inicio
    }
}

