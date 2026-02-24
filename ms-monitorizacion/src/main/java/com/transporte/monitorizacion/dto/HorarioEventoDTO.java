package com.transporte.monitorizacion.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalTime;

@Data
public class HorarioEventoDTO implements Serializable {
	private Long busId;
	private String patente;
	private String ruta;
	private String paradaOrigen;
	private String paradaDestino;
	private LocalTime horaSalida;
	private LocalTime horaLlegada;
	private String tipoEvento;
	private String motivo;
}
