package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.Tutoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TutoriaRepository extends JpaRepository<Tutoria, Long>{
	
	@Query(value = "UPDATE tutoria t SET t.ativo = 1 WHERE id_petiano = ?1", nativeQuery = true)
	void desativarAtivos(Long id);
	
	@Query(value = "SELECT * FROM tutoria t WHERE t.ativo = 0", nativeQuery = true)
	Page<Tutoria> findByAtivos(Pageable pageable);
}
