package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Horario;
import com.example.demo.repository.HorarioRepository;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})public class HorarioController {
    
    @Autowired
    private HorarioRepository horarioRepository;
    
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
        return horarioRepository.save(horario);
    }
    
    @PutMapping("/{id}")
    public Horario actualizarHorario(@PathVariable Long id, @RequestBody Horario horario) {
        horario.setId(id);
        return horarioRepository.save(horario);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarHorario(@PathVariable Long id) {
        horarioRepository.deleteById(id);
    }
}