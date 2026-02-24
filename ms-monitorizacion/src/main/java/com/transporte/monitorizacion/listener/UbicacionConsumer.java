package com.transporte.monitorizacion.listener;

import com.transporte.monitorizacion.model.Ubicacion;
import com.transporte.monitorizacion.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UbicacionConsumer {

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@KafkaListener(topics = "ubicaciones_vehiculos", groupId = "monitorizacion-group")
	public void procesarUbicacion(Ubicacion ubicacion) {
		try {
			System.out.println("📍 [MONITOR] Ubicación recibida de Kafka:");
			System.out.println("   Bus: " + ubicacion.getPatente());
			System.out.println("   GPS: (" + ubicacion.getLatitud() + ", " + ubicacion.getLongitud() + ")");

			// Resetear el ID para que JPA genere uno nuevo con la secuencia de Oracle
			ubicacion.setId(null);
			Ubicacion guardada = ubicacionRepository.save(ubicacion);

			System.out.println("✅ [MONITOR] Ubicación guardada en Oracle con ID: " + guardada.getId());

		} catch (Exception e) {
			System.err.println("❌ [MONITOR] Error al guardar ubicación: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
