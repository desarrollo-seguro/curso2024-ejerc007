package com.example.ejercicio007.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.ejercicio007.model.Venta;
import com.example.ejercicio007.repository.VentaRepository;

import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @PreAuthorize("hasRole('TAQUILLERO')")
    public Venta añadirVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Optional<Venta> leerVenta(Long id) {
        return ventaRepository.findById(id);
    }

    @PreAuthorize("hasRole('TAQUILLERO')")
    public void devolverVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    public Page<Venta> listarVentas(int page, int size) {
        return ventaRepository.findByBorradoIsFalse(PageRequest.of(page, size));
    }
}
