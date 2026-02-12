package com.example.demo.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO para eventos de horarios/rutas
 */
@Data
public class HorarioEventoDTO implements Serializable {
    private Long busId;
    private String patente;
    private String ruta;
    private String paradaOrigen;
    private String paradaDestino;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private String tipoEvento; // "CAMBIO_RUTA", "ACTUALIZACION_HORARIO", "RETRASO"
    private String motivo;
}
