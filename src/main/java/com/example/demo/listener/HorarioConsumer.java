package com.example.demo.listener;

import com.example.demo.dto.HorarioEventoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    
    @RabbitListener(queues = "horarios-queue")
    public void procesarHorario(HorarioEventoDTO evento) {
        try {
            System.out.println("🕐 Evento de horario recibido:");
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
            
            System.out.println("✅ Archivo JSON generado: " + file.getAbsolutePath());
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
