package com.example.demo.controller;

import com.example.demo.model.Ubicacion;
import com.example.demo.repository.UbicacionRepository;
import com.example.demo.service.UbicacionProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para ubicaciones GPS de los buses
 * PRODUCTOR: Envía ubicaciones a RabbitMQ
 */
@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {
    
    @Autowired
    private UbicacionProducerService producerService;
    
    @Autowired
    private UbicacionRepository ubicacionRepository;
    
    /**
     * Recibe ubicación GPS del frontend y la envía a RabbitMQ
     */
    @PostMapping
    public ResponseEntity<String> recibirUbicacion(@RequestBody Ubicacion ubicacion) {
        try {
            // Validar datos
            if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
                return ResponseEntity.badRequest().body("Latitud y longitud son obligatorias");
            }
            
            // Asignar timestamp si no viene
            if (ubicacion.getTimestamp() == null) {
                ubicacion.setTimestamp(LocalDateTime.now());
            }
            
            // Enviar a RabbitMQ (el consumidor lo guardará en Oracle)
            producerService.enviarUbicacion(ubicacion);
            
            return ResponseEntity.ok("Ubicación enviada a procesamiento");
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error: " + e.getMessage());
        }
    }
    
    /**
     * Obtener todas las ubicaciones guardadas
     */
    @GetMapping
    public List<Ubicacion> obtenerUbicaciones() {
        return ubicacionRepository.findAll();
    }
    
    /**
     * Obtener ubicaciones de un bus específico
     */
    @GetMapping("/bus/{busId}")
    public List<Ubicacion> obtenerUbicacionesPorBus(@PathVariable Long busId) {
        return ubicacionRepository.findByBusId(busId);
    }
    
    /**
     * Obtener ubicaciones por patente
     */
    @GetMapping("/patente/{patente}")
    public List<Ubicacion> obtenerUbicacionesPorPatente(@PathVariable String patente) {
        return ubicacionRepository.findByPatente(patente);
    }
}
