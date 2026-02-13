package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.model.Ubicacion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionProducerService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void enviarUbicacion(Ubicacion ubicacion) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.UBICACIONES_QUEUE, ubicacion);
        System.out.println("✅ Ubicación enviada a RabbitMQ: Bus " + ubicacion.getPatente());
    }
}
