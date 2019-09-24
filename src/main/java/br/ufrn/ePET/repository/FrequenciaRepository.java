package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Frequencia;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
	
	@Query(value = "SELECT SUM(assiduidade) FROM Frequencia i WHERE i.id_participante = ?1", nativeQuery = true)
	int findAssiduidadeByParticipante(Long id);
}
