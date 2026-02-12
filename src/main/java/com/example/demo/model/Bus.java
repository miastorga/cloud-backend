package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BUSES")
public class Bus {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUSES_SEQ_GEN")
@SequenceGenerator(name = "BUSES_SEQ_GEN", sequenceName = "BUSES_SEQ", allocationSize = 1)
private Long id;
    
    private String patente;
    private String chofer;
    private String recorrido;
    private String estado;
}