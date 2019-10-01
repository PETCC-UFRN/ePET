package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Periodo_Evento;

public interface Periodo_EventoReposioty extends JpaRepository<Periodo_Evento, Long>{
	
	Page<Periodo_Evento> findByEvento(Long id_evento, Pageable pageable);
}
