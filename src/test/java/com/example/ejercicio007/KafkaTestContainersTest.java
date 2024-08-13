package com.example.ejercicio007;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

import com.example.ejercicio007.model.Usuario;
import com.example.ejercicio007.service.KafkaConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class KafkaTestContainersTest {

    private static KafkaContainer kafkaContainer;

    @SpyBean
    private KafkaConsumerService kafkaConsumerService;

    @BeforeAll
    public static void startKafka() {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        kafkaContainer.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
        System.setProperty("spring.kafka.consumer.bootstrap-servers", kafkaContainer.getBootstrapServers());
        System.setProperty("spring.kafka.consumer.group-id", "test-group");
        System.setProperty("spring.kafka.consumer.key-deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        System.setProperty("spring.kafka.consumer.value-deserializer", "org.springframework.kafka.support.serializer.ErrorHandlingDeserializer");
        System.setProperty("spring.kafka.consumer.properties.spring.deserializer.value.delegate.class", "org.springframework.kafka.support.serializer.JsonDeserializer");
        System.setProperty("spring.kafka.consumer.properties.spring.json.value.default.type", "com.example.ejercicio007.model.Usuario");
        System.setProperty("spring.kafka.consumer.properties.spring.json.trusted.packages", "*");
        System.setProperty("spring.kafka.producer.key-serializer", "org.apache.kafka.common.serialization.StringSerializer");
        System.setProperty("spring.kafka.producer.value-serializer", "org.springframework.kafka.support.serializer.JsonSerializer");

        createTopic("topic-pepe");

    }

    private static void createTopic(String topicName) {
        Map<String, Object> configs = KafkaTestUtils.producerProps(kafkaContainer.getBootstrapServers());
        try (AdminClient adminClient = AdminClient.create(configs)) {
            adminClient.createTopics(Collections.singleton(new NewTopic(topicName, 1, (short) 1))).all().get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create topic", e);
        }

        // Esperar hasta que el topic esté disponible
        await()
            .atMost(30, TimeUnit.SECONDS)
            .until(() -> {
                try (AdminClient adminClient = AdminClient.create(configs)) {
                    return adminClient.listTopics().names().get().contains(topicName);
                }
            });
    }

    @Test
    public void testKafkaConsumer() throws Exception {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(kafkaContainer.getBootstrapServers());
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);
        template.setDefaultTopic("topic-pepe");

        Usuario usuario = new Usuario(1L, "nombre", "contraseña", "rol");
        String usuarioJson = new ObjectMapper().writeValueAsString(usuario);

        //Thread.sleep(10000);

        template.sendDefault(usuarioJson);
        template.sendDefault(usuarioJson);
        template.sendDefault(usuarioJson);

        // Verificar que el mensaje fue consumido
        await()
            .atMost(10, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                // Verificar que el mensaje fue consumido 3 veces
                verify(kafkaConsumerService, times(3)).consume(any());
            });

    }
}