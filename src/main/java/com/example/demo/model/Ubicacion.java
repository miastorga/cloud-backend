package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "UBICACIONES")
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bus_id")
    private Long busId;
    
    private String patente;
    private Double latitud;
    private Double longitud;
    private LocalDateTime timestamp;
    private String velocidad;
    private String direccion;
}
