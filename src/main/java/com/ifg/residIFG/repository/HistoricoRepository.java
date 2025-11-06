package com.ifg.residIFG.repository;

import com.ifg.residIFG.domain.historico.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {

}
