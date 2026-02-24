package com.transporte.productor.service;

import com.transporte.productor.model.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UbicacionProducerService {

	private static final String TOPIC = "ubicaciones_vehiculos";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${monitorizacion.api.url:http://ms-monitorizacion:8083}")
	private String monitorizacionUrl;

	private final Random random = new Random();

	// Coordenadas base de Santiago de Chile
	private static final double LAT_BASE = -33.4489;
	private static final double LON_BASE = -70.6693;

	/**
	 * Envía una ubicación al tópico de Kafka.
	 */
	public void enviarUbicacion(Ubicacion ubicacion) {
		kafkaTemplate.send(TOPIC, ubicacion.getPatente(), ubicacion);
		System.out.println("✅ [PRODUCTOR] Ubicación enviada a Kafka [" + TOPIC + "]: Bus "
				+ ubicacion.getPatente() + " (busId=" + ubicacion.getBusId() + ")");
	}

	/**
	 * Consulta los buses existentes en ms-monitorizacion.
	 * Si no hay buses creados, retorna una lista vacía.
	 */
	private List<Map<String, Object>> obtenerBuses() {
		try {
			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
					monitorizacionUrl + "/api/buses",
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Map<String, Object>>>() {
					});
			return response.getBody() != null ? response.getBody() : Collections.emptyList();
		} catch (Exception e) {
			System.out.println("⚠️ [PRODUCTOR] No se pudo consultar buses: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	/**
	 * Simulador automático: cada 1 segundo consulta los buses existentes
	 * y genera una ubicación aleatoria para un bus random.
	 * Si no hay buses creados, no genera nada.
	 */
	@Scheduled(cron = "*/1 * * * * *")
	public void simularUbicacion() {
		List<Map<String, Object>> buses = obtenerBuses();

		if (buses.isEmpty()) {
			System.out.println("⏸️ [PRODUCTOR] No hay buses registrados, esperando...");
			return;
		}

		// Elegir un bus random de los existentes
		Map<String, Object> busElegido = buses.get(random.nextInt(buses.size()));

		Long busId = busElegido.get("id") != null
				? ((Number) busElegido.get("id")).longValue()
				: null;
		String patente = (String) busElegido.get("patente");

		Ubicacion ubicacion = new Ubicacion();
		ubicacion.setBusId(busId);
		ubicacion.setPatente(patente);
		ubicacion.setLatitud(LAT_BASE + (random.nextDouble() * 0.05 - 0.025));
		ubicacion.setLongitud(LON_BASE + (random.nextDouble() * 0.05 - 0.025));
		ubicacion.setTimestamp(LocalDateTime.now());
		ubicacion.setVelocidad(String.valueOf(20 + random.nextInt(60)) + " km/h");
		ubicacion.setDireccion(new String[] { "Norte", "Sur", "Este", "Oeste" }[random.nextInt(4)]);

		enviarUbicacion(ubicacion);
	}
}
