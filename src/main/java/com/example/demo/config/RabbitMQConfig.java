package com.example.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    // Nombres de las colas
    public static final String UBICACIONES_QUEUE = "ubicaciones-queue";
    public static final String HORARIOS_QUEUE = "horarios-queue";
    
    /**
     * Cola para mensajes de ubicaciones GPS
     */
    @Bean
    public Queue ubicacionesQueue() {
        return new Queue(UBICACIONES_QUEUE, true); // durable = true
    }
    
    /**
     * Cola para mensajes de horarios/rutas
     */
    @Bean
    public Queue horariosQueue() {
        return new Queue(HORARIOS_QUEUE, true);
    }
    
    /**
     * Configurar RabbitTemplate para enviar objetos como JSON
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    
    /**
     * Convertidor JSON para serializar/deserializar mensajes
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
