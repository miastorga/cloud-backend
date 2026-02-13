package com.example.demo.controller;

import com.example.demo.dto.HorarioEventoDTO;
import com.example.demo.model.Horario;
import com.example.demo.repository.HorarioRepository;
import com.example.demo.service.HorarioProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private HorarioProducerService producerService;
    
    @GetMapping
    public List<Horario> listarHorarios() {
        return horarioRepository.findAll();
    }
    
    @GetMapping("/bus/{busId}")
    public List<Horario> obtenerHorariosPorBus(@PathVariable Long busId) {
        return horarioRepository.findByBusId(busId);
    }
    
    @GetMapping("/parada/{origen}")
    public List<Horario> obtenerHorariosPorParada(@PathVariable String origen) {
        return horarioRepository.findByParadaOrigen(origen);
    }
    
    @PostMapping
    public Horario crearHorario(@RequestBody Horario horario) {
        Horario guardado = horarioRepository.save(horario);
        
        HorarioEventoDTO evento = new HorarioEventoDTO();
        evento.setBusId(guardado.getBus().getId());
        evento.setParadaOrigen(guardado.getParadaOrigen());
        evento.setParadaDestino(guardado.getParadaDestino());
        evento.setHoraSalida(guardado.getHoraSalida());
        evento.setHoraLlegada(guardado.getHoraLlegadaEstimada());
        evento.setTipoEvento("NUEVO_HORARIO");
        
        producerService.enviarEvento(evento);
        
        return guardado;
    }
    
    @PutMapping("/{id}")
    public Horario actualizarHorario(@PathVariable Long id, @RequestBody Horario horario) {
        horario.setId(id);
        Horario actualizado = horarioRepository.save(horario);
        
        HorarioEventoDTO evento = new HorarioEventoDTO();
        evento.setBusId(actualizado.getBus().getId());
        evento.setParadaOrigen(actualizado.getParadaOrigen());
        evento.setParadaDestino(actualizado.getParadaDestino());
        evento.setHoraSalida(actualizado.getHoraSalida());
        evento.setHoraLlegada(actualizado.getHoraLlegadaEstimada());
        evento.setTipoEvento("ACTUALIZACION_HORARIO");
        evento.setMotivo("Horario actualizado");
        
        producerService.enviarEvento(evento);
        
        return actualizado;
    }
    
    @DeleteMapping("/{id}")
    public void eliminarHorario(@PathVariable Long id) {
        horarioRepository.deleteById(id);
    }
    
    @PostMapping("/evento")
    public ResponseEntity<String> reportarEvento(@RequestBody HorarioEventoDTO evento) {
        try {
            producerService.enviarEvento(evento);
            return ResponseEntity.ok("Evento registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error: " + e.getMessage());
        }
    }
}
