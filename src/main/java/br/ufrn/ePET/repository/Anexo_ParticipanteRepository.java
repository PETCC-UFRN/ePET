package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Anexo_Participante;

public interface Anexo_ParticipanteRepository extends JpaRepository<Anexo_Participante, Long>{
	
	@Query(value = "SELECT * FROM anexo_participante a WHERE a.id_participante = ?1", nativeQuery = true)
	List<Anexo_Participante> findByIdParticipante(Long id_participante);

	@Modifying
	@Query(value = "DELETE FROM anexo_participante a WHERE a.id_participante = ?1", nativeQuery = true)
	void deleteByIdParticipante(Long id_participante);
}
