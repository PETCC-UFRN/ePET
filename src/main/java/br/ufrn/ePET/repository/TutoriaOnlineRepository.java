package br.ufrn.ePET.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.TutoriaOnline;

public interface TutoriaOnlineRepository extends JpaRepository<TutoriaOnline, Long>{
	
	@Query(value = "SELECT * FROM tutoria ORDER BY nota DESC", nativeQuery = true)
	Page<TutoriaOnline> findAllNota(Pageable pageable);

}
