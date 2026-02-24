package com.transporte.productor.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO de Ubicación (sin anotaciones JPA).
 * Este microservicio solo produce datos, no persiste.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {
	private Long id;
	private Long busId;
	private String patente;
	private Double latitud;
	private Double longitud;
	private LocalDateTime timestamp;
	private String velocidad;
	private String direccion;
}
