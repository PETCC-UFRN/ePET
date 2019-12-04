package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Anexo_Tutoria;

public interface Anexo_TutoriaRepository  extends JpaRepository<Anexo_Tutoria, Long>{

	@Query(value = "SELECT * FROM anexo_tutoria a WHERE a.id_tutoria = ?1", nativeQuery = true)
	List<Anexo_Tutoria> findById_tutoria(Long id_tutoria);
	
	@Query(value = "DELETE FROM anexo_tutoria WHERE a.id_tutoria = ?1", nativeQuery = true)
	void deleteByIdTutoria(Long id_tutoria);
}
