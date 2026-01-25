
package com.example.demo.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "HORARIOS")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
    
    private String paradaOrigen;
    private String paradaDestino;
    private LocalTime horaSalida;
    private LocalTime horaLlegadaEstimada;
    private String diasOperacion; // Ej: "L-V", "L-D", etc.
    private Boolean activo;
}