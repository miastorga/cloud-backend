package com.transporte.monitorizacion.listener;

import com.transporte.monitorizacion.dto.HorarioEventoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HorarioConsumer {

	private final ObjectMapper objectMapper;
	private final String outputPath = "data/horarios";

	public HorarioConsumer() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@KafkaListener(topics = "horarios", groupId = "monitorizacion-group")
	public void procesarHorario(HorarioEventoDTO evento) {
		try {
			System.out.println("🕐 [MONITOR] Evento de horario recibido de Kafka:");
			System.out.println("   Bus: " + evento.getPatente());
			System.out.println("   Tipo: " + evento.getTipoEvento());
			System.out.println("   Motivo: " + evento.getMotivo());

			Path directory = Paths.get(outputPath);
			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}

			String timestamp = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			String fileName = String.format("horario_%s_%s.json",
					evento.getPatente(), timestamp);

			File file = new File(directory.toFile(), fileName);

			objectMapper.writerWithDefaultPrettyPrinter()
					.writeValue(file, evento);

			System.out.println("✅ [MONITOR] Archivo JSON generado: " + file.getAbsolutePath());

		} catch (Exception e) {
			System.err.println("❌ [MONITOR] Error al generar JSON: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
