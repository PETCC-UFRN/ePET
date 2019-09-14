package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
	
	@Query(value = "SELECT count(id_participante) FROM `participante` WHERE id_evento = ?1", nativeQuery = true)
	int countAtivos(Long id);
	
	List<Participante> findByEspera(boolean espera);
	
	@Query(value = "SELECT * FROM PARTICIPANTES u WHERE u.id_pessoa = ?1 AND ORDER BY id_participante DESC", nativeQuery = true)
	List<Participante> findByPessoa(Long id);
}
