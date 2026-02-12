package com.example.demo.repository;

import com.example.demo.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    List<Ubicacion> findByBusId(Long busId);
    List<Ubicacion> findByPatente(String patente);
}
