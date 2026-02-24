package com.example.demo.service;

import com.example.demo.dto.HorarioEventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HorarioProducerService {

    private static final String TOPIC = "horarios";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void enviarEvento(HorarioEventoDTO evento) {
        kafkaTemplate.send(TOPIC, evento.getPatente(), evento);
        System.out.println("✅ Evento de horario enviado a Kafka [" + TOPIC + "]: " + evento.getTipoEvento());
    }
}
