package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	@Query(value = "SELECT * FROM disciplina u WHERE u.codigo = ?1", nativeQuery = true)	
	Disciplina findByCode(String codigo);
}
