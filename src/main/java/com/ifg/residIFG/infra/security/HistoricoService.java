package com.ifg.residIFG.infra.security;

import com.ifg.residIFG.domain.historico.Historico;
import com.ifg.residIFG.repository.HistoricoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoricoService {

    private final HistoricoRepository repository;

    public HistoricoService(HistoricoRepository repository) {
        this.repository = repository;
    }

    public Historico createHistorico(Historico historico) {
        return repository.save(historico);
    }


    public List<Historico> findAll() {
        return repository.findAll();
    }


    public void deleteAll(){}
}
