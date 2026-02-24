package com.transporte.productor.service;

import com.transporte.productor.model.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UbicacionProducerService {

	private static final String TOPIC = "ubicaciones_vehiculos";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private final Random random = new Random();

	// Patentes de buses simulados
	private final String[] patentes = { "BGXY-10", "CJKL-20", "DFGH-30", "HJKL-40", "RRTT-50" };

	// Coordenadas base de Santiago de Chile
	private static final double LAT_BASE = -33.4489;
	private static final double LON_BASE = -70.6693;

	/**
	 * Envía una ubicación al tópico de Kafka.
	 */
	public void enviarUbicacion(Ubicacion ubicacion) {
		kafkaTemplate.send(TOPIC, ubicacion.getPatente(), ubicacion);
		System.out.println("✅ [PRODUCTOR] Ubicación enviada a Kafka [" + TOPIC + "]: Bus " + ubicacion.getPatente());
	}

	/**
	 * Simulador automático: genera y envía una ubicación aleatoria cada 1 segundo.
	 * Cumple con el requerimiento de la pauta de envío periódico con @Scheduled.
	 */
	@Scheduled(cron = "*/1 * * * * *")
	public void simularUbicacion() {
		String patente = patentes[random.nextInt(patentes.length)];

		Ubicacion ubicacion = new Ubicacion();
		ubicacion.setPatente(patente);
		ubicacion.setLatitud(LAT_BASE + (random.nextDouble() * 0.05 - 0.025));
		ubicacion.setLongitud(LON_BASE + (random.nextDouble() * 0.05 - 0.025));
		ubicacion.setTimestamp(LocalDateTime.now());
		ubicacion.setVelocidad(String.valueOf(20 + random.nextInt(60)) + " km/h");
		ubicacion.setDireccion(new String[] { "Norte", "Sur", "Este", "Oeste" }[random.nextInt(4)]);

		enviarUbicacion(ubicacion);
	}
}
