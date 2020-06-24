package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Frequencia;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
	
	@Query(value = "SELECT SUM(assiduidade) FROM frequencia i, periodo_evento p WHERE i.id_participante = ?1 AND "
			+ "i.id_periodo_evento = p.id_periodo_evento AND p.id_evento = ?2", nativeQuery = true)
	int findAssiduidadeByParticipante(Long id, Long id_evento);

	@Query(value = "SELECT * FROM frequencia f WHERE f.id_periodo_evento = ?1 AND f.id_participante = ?2", nativeQuery = true)
	Frequencia findByPeriodoAndParticipante(Long id_periodo_evento, Long id_participante);

	@Query(value = "SELECT * FROM frequencia f WHERE f.id_periodo_evento in (SELECT pe.id_periodo_evento FROM periodo_evento pe where pe.id_evento = ?1)", nativeQuery = true)
	Page<Frequencia> findByEventoID(Long id_evento, Pageable pageable);
}
