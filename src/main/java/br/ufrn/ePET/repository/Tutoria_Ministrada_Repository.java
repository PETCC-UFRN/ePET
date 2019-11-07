package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Tutoria_Ministrada;

public interface Tutoria_Ministrada_Repository extends JpaRepository<Tutoria_Ministrada, Long>{
	
	@Query(value = "UPDATE tutoria_ministrada tm SET tm.ativo = false WHERE id_tutoria = ?1", nativeQuery = true)
	void desativarAtivos(Long id);
	
	@Query(value = "UPDATE tutoria_ministrada SET tm.ativo =false FROM tutoria_ministrada tm, Tutoria t WHERE tm.id_tutoria = t.id_tutoria AND t.id_petiano =?1", nativeQuery = true)
	void desativarAtivosPorPetiano(Long id);
	
	@Query(value = "SELECT * FROM tutoria_ministrada tm WHERE tm.ativo = true", nativeQuery = true)
	Page<Tutoria_Ministrada> findByAtivos(Pageable pageable);
}
