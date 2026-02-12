package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
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
    @Column(name = "FECHA_HORA") // Cambiamos el nombre de la columna, no del atributo
    private LocalDateTime timestamp;  
      private String velocidad;
    private String direccion;
}