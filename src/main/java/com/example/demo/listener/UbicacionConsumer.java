package com.example.demo.listener;

import com.example.demo.model.Ubicacion;
import com.example.demo.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UbicacionConsumer {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @KafkaListener(topics = "ubicaciones_vehiculos", groupId = "transporte-group")
    public void procesarUbicacion(Ubicacion ubicacion) {
        try {
            System.out.println("📍 Ubicación recibida de Kafka:");
            System.out.println("   Bus: " + ubicacion.getPatente());
            System.out.println("   GPS: (" + ubicacion.getLatitud() + ", " + ubicacion.getLongitud() + ")");

            Ubicacion guardada = ubicacionRepository.save(ubicacion);

            System.out.println("✅ Ubicación guardada en Oracle con ID: " + guardada.getId());

        } catch (Exception e) {
            System.err.println("❌ Error al guardar ubicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
