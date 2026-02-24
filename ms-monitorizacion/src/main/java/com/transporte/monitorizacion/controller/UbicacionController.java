package com.transporte.monitorizacion.controller;

import com.transporte.monitorizacion.model.Ubicacion;
import com.transporte.monitorizacion.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {

	@Autowired
	private UbicacionRepository ubicacionRepository;

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
