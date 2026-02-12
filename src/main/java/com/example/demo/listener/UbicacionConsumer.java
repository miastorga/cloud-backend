package com.example.demo.listener;

import com.example.demo.model.Ubicacion;
import com.example.demo.repository.UbicacionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CONSUMIDOR 1: Escucha ubicaciones de RabbitMQ y las guarda en Oracle
 */
@Component
public class UbicacionConsumer {
    
    @Autowired
    private UbicacionRepository ubicacionRepository;
    
    /**
     * Este método se ejecuta AUTOMÁTICAMENTE cada vez que llega 
     * un mensaje a la cola "ubicaciones-queue"
     */
    @RabbitListener(queues = "ubicaciones-queue")
    public void procesarUbicacion(Ubicacion ubicacion) {
        try {
            System.out.println("📍 Ubicación recibida de RabbitMQ:");
            System.out.println("   Bus: " + ubicacion.getPatente());
            System.out.println("   GPS: (" + ubicacion.getLatitud() + ", " + ubicacion.getLongitud() + ")");
            
            // Guardar en Oracle
            Ubicacion guardada = ubicacionRepository.save(ubicacion);
            
            System.out.println("✅ Ubicación guardada en Oracle con ID: " + guardada.getId());
            
        } catch (Exception e) {
            System.err.println("❌ Error al guardar ubicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
