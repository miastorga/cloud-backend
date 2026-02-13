package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor        
@AllArgsConstructor       
@Table(name = "UBICACIONES")
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UBICACIONES_SEQ_GEN")
    @SequenceGenerator(name = "UBICACIONES_SEQ_GEN", sequenceName = "UBICACIONES_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "bus_id")
    private Long busId;
    
    private String patente;
    private Double latitud;
    private Double longitud;
    
    @Column(name = "FECHA_HORA")
    private LocalDateTime timestamp;  
    
    private String velocidad;
    private String direccion;
}