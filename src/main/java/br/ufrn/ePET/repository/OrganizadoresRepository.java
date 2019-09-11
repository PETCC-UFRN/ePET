package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Organizadores;

public interface OrganizadoresRepository extends JpaRepository<Organizadores, Long> {
	
	@Query(value = "SELECT * FROM ORGANIZADORES i WHERE u.id_pessoa = ?1", nativeQuery = true)
	List<Organizadores> findByPessoa(Long id);
	
	@Query(value = "SELECT * FROM ORGANIZADORES i WHERE u.id_evento = ?1", nativeQuery = true)
	List<Organizadores> findByEvento(Long id);
	
}
