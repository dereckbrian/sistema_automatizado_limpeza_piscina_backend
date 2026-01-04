package com.ifg.residIFG.repository;

import com.ifg.residIFG.domain.alertas.Alertas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertasRepository extends JpaRepository<Alertas, Long> {
    // Busca os ultimos 20 alertas do usuario ordenado por data
    List<Alertas> findTop20ByUsuarioEmailOrderByDataHoraDesc(String email);
}