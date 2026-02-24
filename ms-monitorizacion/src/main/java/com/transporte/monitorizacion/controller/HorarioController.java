package com.transporte.monitorizacion.controller;

import com.transporte.monitorizacion.model.Horario;
import com.transporte.monitorizacion.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

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

	@DeleteMapping("/{id}")
	public void eliminarHorario(@PathVariable Long id) {
		horarioRepository.deleteById(id);
	}
}
