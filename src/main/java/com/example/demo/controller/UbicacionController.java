package com.example.demo.controller;

import com.example.demo.model.Ubicacion;
import com.example.demo.repository.UbicacionRepository;
import com.example.demo.service.UbicacionProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {
    
    @Autowired
    private UbicacionProducerService producerService;
    
    @Autowired
    private UbicacionRepository ubicacionRepository;
    
    @PostMapping
    public ResponseEntity<String> recibirUbicacion(@RequestBody Ubicacion ubicacion) {
        try {
            System.out.println("🔵 CONTROLADOR: Recibiendo ubicación de bus: " + ubicacion.getPatente());
            // Validar datos
            if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
                return ResponseEntity.badRequest().body("Latitud y longitud son obligatorias");
            }
            
            if (ubicacion.getTimestamp() == null) {
                ubicacion.setTimestamp(LocalDateTime.now());
            }

            System.out.println("🔵 CONTROLADOR: Enviando a RabbitMQ...");
            
            producerService.enviarUbicacion(ubicacion);
            System.out.println("🔵 CONTROLADOR: Enviado exitosamente");

            
            return ResponseEntity.ok("Ubicación enviada a procesamiento");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR en controlador: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public List<Ubicacion> obtenerUbicaciones() {
        return ubicacionRepository.findAll();
    }
    
    @GetMapping("/bus/{busId}")
    public List<Ubicacion> obtenerUbicacionesPorBus(@PathVariable Long busId) {
        return ubicacionRepository.findByBusId(busId);
    }
    
    @GetMapping("/patente/{patente}")
    public List<Ubicacion> obtenerUbicacionesPorPatente(@PathVariable String patente) {
        return ubicacionRepository.findByPatente(patente);
    }
}
