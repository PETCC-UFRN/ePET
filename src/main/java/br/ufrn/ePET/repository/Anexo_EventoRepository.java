package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Anexo_Evento;

public interface Anexo_EventoRepository extends JpaRepository<Anexo_Evento, Long>{
	
	@Query(value = "SELECT * FROM anexo_evento a WHERE a.id_evento = ?1", nativeQuery = true)
	List<Anexo_Evento> findByIdEvento(Long id_evento);
	
	@Query(value = "DELETE FROM anexo_evento a WHERE a.id_evento = ?1", nativeQuery = true)
	void deleteByIdEvento(Long id_evento);
}
