package com.transporte.procesamiento.service;

import com.transporte.procesamiento.dto.HorarioEventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HorarioProducerService {

	private static final String TOPIC = "horarios";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void enviarEvento(HorarioEventoDTO evento) {
		kafkaTemplate.send(TOPIC, evento.getPatente(), evento);
		System.out.println(
				"✅ [PROCESAMIENTO] Evento de horario publicado en Kafka [" + TOPIC + "]: " + evento.getTipoEvento());
	}
}
