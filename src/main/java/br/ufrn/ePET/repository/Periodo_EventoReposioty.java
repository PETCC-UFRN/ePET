package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Periodo_Evento;

public interface Periodo_EventoReposioty extends JpaRepository<Periodo_Evento, Long>{

	@Query(value = "SELECT * FROM periodo_Evento p WHERE p.id_evento = ?1", nativeQuery = true)
	Page<Periodo_Evento> findByEventoID(Long id_evento, Pageable pageable);

	List<Periodo_Evento> findByEvento(Evento evento);
}
