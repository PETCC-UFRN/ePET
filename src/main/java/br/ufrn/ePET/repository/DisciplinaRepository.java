package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	@Query(value = "SELECT * FROM disciplina u WHERE u.codigo = ?1", nativeQuery = true)	
	Disciplina findByCode(String codigo);

	@Query(value = "SELECT * FROM disciplina u WHERE u.nome LIKE %?1% OR u.codigo LIKE %?1%", nativeQuery = true)
	Page<Disciplina> findbyNomeOuCodigo(String search, Pageable pageable);

	@Query(value = "SELECT * FROM disciplina u WHERE u.ativo = true AND (u.nome LIKE %?1% OR u.codigo LIKE %?1%)", nativeQuery = true)
	Page<Disciplina> findbyNomeOuCodigoAtivo(String search, Pageable pageable);

	@Query(value = "SELECT * FROM disciplina u WHERE u.ativo = true", nativeQuery = true)
	Page<Disciplina> findByAtivos(Pageable pageable);
}
