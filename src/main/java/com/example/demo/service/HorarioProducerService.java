package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.HorarioEventoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioProducerService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void enviarEvento(HorarioEventoDTO evento) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.HORARIOS_QUEUE, evento);
        System.out.println("✅ Evento de horario enviado a RabbitMQ: " + evento.getTipoEvento());
    }
}
