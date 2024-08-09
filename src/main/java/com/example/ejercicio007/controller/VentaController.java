package com.example.ejercicio007.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.ejercicio007.model.Venta;
import com.example.ejercicio007.service.VentaService;

import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> añadirVenta(@RequestBody Venta venta) {
        Venta ventaAñadida = ventaService.añadirVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaAñadida);
    }

    @GetMapping("/{id}")
    public Optional<Venta> leerVenta(@PathVariable Long id) {
        return ventaService.leerVenta(id);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void devolverVenta(@PathVariable Long id) {
        ventaService.devolverVenta(id);
    }

    @GetMapping
    public Page<Venta> listarVentas(@RequestParam int page, @RequestParam int size) {
        return ventaService.listarVentas(page, size);
    }
}
