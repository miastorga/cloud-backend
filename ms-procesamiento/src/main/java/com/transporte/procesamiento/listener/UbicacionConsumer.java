package com.transporte.procesamiento.listener;

import com.transporte.procesamiento.dto.HorarioEventoDTO;
import com.transporte.procesamiento.model.Ubicacion;
import com.transporte.procesamiento.service.HorarioProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * Consume ubicaciones del tópico "ubicaciones_vehiculos",
 * las procesa (lógica de negocio) y publica eventos en el tópico "horarios".
 */
@Component
public class UbicacionConsumer {

	@Autowired
	private HorarioProducerService horarioProducerService;

	@KafkaListener(topics = "ubicaciones_vehiculos", groupId = "procesamiento-group")
	public void procesarUbicacion(Ubicacion ubicacion) {
		try {
			System.out.println("📍 [PROCESAMIENTO] Ubicación recibida de Kafka:");
			System.out.println("   Bus: " + ubicacion.getPatente());
			System.out.println("   GPS: (" + ubicacion.getLatitud() + ", " + ubicacion.getLongitud() + ")");

			// --- LÓGICA DE PROCESAMIENTO ---
			// Transforma la ubicación en un evento de horario.
			// En un escenario real aquí se calcularían tiempos estimados,
			// se detectarían retrasos, cambios de ruta, etc.
			HorarioEventoDTO evento = new HorarioEventoDTO();
			evento.setBusId(ubicacion.getBusId());
			evento.setPatente(ubicacion.getPatente());
			evento.setRuta("Ruta Santiago Centro");
			evento.setParadaOrigen("Parada Actual");
			evento.setParadaDestino("Terminal");
			evento.setHoraSalida(LocalTime.now());
			evento.setHoraLlegada(LocalTime.now().plusMinutes(30));
			evento.setTipoEvento("ACTUALIZACION_POSICION");
			evento.setMotivo("Posición GPS procesada - " + ubicacion.getDireccion() + " a " + ubicacion.getVelocidad());

			// Publica el evento procesado en el tópico "horarios"
			horarioProducerService.enviarEvento(evento);

			System.out.println(
					"✅ [PROCESAMIENTO] Ubicación procesada y evento publicado para bus: " + ubicacion.getPatente());

		} catch (Exception e) {
			System.err.println("❌ [PROCESAMIENTO] Error al procesar ubicación: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
