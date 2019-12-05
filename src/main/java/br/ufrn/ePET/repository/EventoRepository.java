package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Online;

public interface EventoRepository extends JpaRepository<Online, Long>{
	
	@Query(value = "SELECT * FROM evento i WHERE i.participante_anexos = ?1", nativeQuery = true)
	List<Evento> findByParticipantesAnexos(Long id);
	
	@Query(value = "SELECT * FROM evento i WHERE i.ativo = false", nativeQuery = true)
	List<Evento> findByInativos();
	
	@Query(value = "SELECT * FROM evento i WHERE i.d_inscricao_fim => DATE('now') AND i.ativo = true", nativeQuery = true)
	Page<Evento> findByAtivos(java.sql.Date d_ins_fim, Pageable pageable);
}
