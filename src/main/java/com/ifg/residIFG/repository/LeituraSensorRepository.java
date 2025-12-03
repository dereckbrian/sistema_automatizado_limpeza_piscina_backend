package com.ifg.residIFG.repository;

import com.ifg.residIFG.domain.piscinas.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {
    // Busca as últimas 20 leituras para o gráfico (ordenado por hora)
    List<LeituraSensor> findTop20ByOrderByDataHoraDesc();

    // Busca a última leitura para mostrar no card em tempo real
    LeituraSensor findTopByOrderByDataHoraDesc();
}
