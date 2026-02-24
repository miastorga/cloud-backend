package com.transporte.monitorizacion.repository;

import com.transporte.monitorizacion.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
	List<Bus> findByEstado(String estado);

	List<Bus> findByRecorrido(String recorrido);

	Bus findByPatente(String patente);
}
