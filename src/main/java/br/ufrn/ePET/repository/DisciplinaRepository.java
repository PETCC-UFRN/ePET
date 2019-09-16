package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
		Disciplina findByCode(String codigo);
}
