package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
	
	@Query(value = "SELECT count(id_participante) FROM Participante WHERE id_evento = ?", nativeQuery = true)
	int countAtivos(Long id);
	
	List<Participante> findByEspera(boolean espera);
	
	@Query(value = "SELECT * FROM participante u WHERE u.id_pessoa = ? ORDER BY u.id_participante DESC", nativeQuery = true)
	Page<Participante> findByPessoa(Long id, Pageable pageable);
}
