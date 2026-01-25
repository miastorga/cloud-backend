package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByBusId(Long busId);
    List<Horario> findByParadaOrigen(String parada);
    List<Horario> findByActivoTrue();
}