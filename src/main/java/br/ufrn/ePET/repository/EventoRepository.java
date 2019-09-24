package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
	
	@Query(value = "SELECT * FROM Evento i WHERE i.participante_anexos = ?1", nativeQuery = true)
	List<Evento> findByParticipantesAnexos(Long id);
	
	@Query(value = "SELECT * FROM Evento i WHERE i.ativo = falso", nativeQuery = true)
	List<Evento> findByInativos();
}
