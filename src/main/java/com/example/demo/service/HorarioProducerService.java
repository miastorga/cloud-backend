package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.HorarioEventoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PRODUCTOR 2: Envía eventos de horarios/rutas a RabbitMQ
 */
@Service
public class HorarioProducerService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * Enviar evento de horario a la cola de RabbitMQ
     */
    public void enviarEvento(HorarioEventoDTO evento) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.HORARIOS_QUEUE, evento);
        System.out.println("✅ Evento de horario enviado a RabbitMQ: " + evento.getTipoEvento());
    }
}
