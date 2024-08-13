package com.example.ejercicio007.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.ejercicio007.model.Usuario;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "topic-pepe", groupId = "grupo-consumidor")
    public void consume(Usuario usuario) {
        System.out.println("Usuario recibido: " + usuario);
    }
}