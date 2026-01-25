package com.example.demo.controller;

import com.example.demo.model.Bus;
import com.example.demo.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class BusController {
    
    @Autowired
    private BusRepository busRepository;
    
    @GetMapping
    public List<Bus> listarBuses() {
        return busRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Bus obtenerBusPorId(@PathVariable Long id) {
        return busRepository.findById(id).orElse(null);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Bus> obtenerBusesPorEstado(@PathVariable String estado) {
        return busRepository.findByEstado(estado);
    }
    
    @GetMapping("/recorrido/{recorrido}")
    public List<Bus> obtenerBusesPorRecorrido(@PathVariable String recorrido) {
        return busRepository.findByRecorrido(recorrido);
    }
    
    @GetMapping("/patente/{patente}")
    public Bus obtenerBusPorPatente(@PathVariable String patente) {
        return busRepository.findByPatente(patente);
    }
    
    @PostMapping
    public Bus crearBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }
    
    @PutMapping("/{id}")
    public Bus actualizarBus(@PathVariable Long id, @RequestBody Bus bus) {
        bus.setId(id);
        return busRepository.save(bus);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarBus(@PathVariable Long id) {
        busRepository.deleteById(id);
    }
}