package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Tutoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Tutoria_Ministrada;

public interface Tutoria_Ministrada_Repository extends JpaRepository<Tutoria_Ministrada, Long>{

	@Modifying
	@Query(value = "UPDATE tutoria_ministrada tm SET tm.ativo = 0 WHERE id_tutoria = ?1", nativeQuery = true)
	void desativarAtivos(Long id);

	@Modifying
	@Query(value = "UPDATE tutoria_ministrada SET tm.ativo = 0 FROM tutoria_ministrada tm, Tutoria t WHERE tm.id_tutoria = t.id_tutoria AND t.id_petiano =?1", nativeQuery = true)
	void desativarAtivosPorPetiano(Long id);
	
	@Query(value = "SELECT * FROM tutoria_ministrada tm WHERE tm.ativo = true", nativeQuery = true)
	Page<Tutoria_Ministrada> findByAtivos(Pageable pageable);

	@Query(value = "SELECT * FROM tutoria_ministrada tm WHERE tm.ativo = true AND tm.id_pessoa = ?1", nativeQuery = true)
	Page<Tutoria_Ministrada> findByPessoa(Long id_pessoa, Pageable pageable);

	@Query(value = "SELECT * FROM tutoria_ministrada tm WHERE tm.ativo = true AND tm.id_tutoria = ?1", nativeQuery = true)
	Page<Tutoria_Ministrada> findByTutoria(Long id_tutoria, Pageable pageable);

}
