package com.example.ejercicio007.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ejercicio007.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
        Page<Venta> findByBorradoIsFalse(PageRequest pageRequest);

}