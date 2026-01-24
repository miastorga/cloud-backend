package com.example.demo.controller;

import com.example.demo.model.Bus;
import com.example.demo.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @GetMapping
    public List<Bus> listarBuses() {
        return busRepository.findAll();
    }

    @PostMapping
    public Bus crearBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }

}