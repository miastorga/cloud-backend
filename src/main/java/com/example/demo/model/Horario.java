
package com.example.demo.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "HORARIOS")
public class Horario {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HORARIOS_SEQ_GEN")
@SequenceGenerator(name = "HORARIOS_SEQ_GEN", sequenceName = "HORARIOS_SEQ", allocationSize = 1)
private Long id;
    
    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
    
    private String paradaOrigen;
    private String paradaDestino;

  @Column(name = "hora_salida", columnDefinition = "TIMESTAMP")
    private LocalTime horaSalida;

    @Column(name = "hora_llegada", columnDefinition = "TIMESTAMP")
    private LocalTime horaLlegadaEstimada;
    
    private String diasOperacion;
    private Boolean activo;
}