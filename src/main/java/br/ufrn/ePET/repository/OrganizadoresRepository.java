package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Organizadores;

public interface OrganizadoresRepository extends JpaRepository<Organizadores, Long> {
	
	@Query(value = "SELECT * FROM organizadores u WHERE u.id_pessoa = ?1", nativeQuery = true)
	List<Organizadores> findByPessoa(Long id_pessoa);
	
	@Query(value = "SELECT * FROM organizadores u WHERE u.id_evento = ?1", nativeQuery = true)
	List<Organizadores> findByEvento(Long id_evento);

	@Query(value = "SELECT u.id_organizadores, u.id_evento, u.id_pessoa FROM organizadores u INNER JOIN pessoa p ON p.id_pessoa = u.id_pessoa WHERE p.nome LIKE %?1% OR p.cpf LIKE %?1%", nativeQuery = true)
	Page<Organizadores> findByNomeOuCpfPessoa(String nome, Pageable pageable);

	@Query(value = "SELECT u.id_organizadores, u.id_evento, u.id_pessoa FROM organizadores u INNER JOIN evento p ON p.id_evento = u.id_evento WHERE p.titulo LIKE %?1%", nativeQuery = true)
	Page<Organizadores> findByTituloEvento(String titulo, Pageable pageable);

	@Query(value = "SELECT * FROM organizadores u WHERE u.id_pessoa = ?1 AND u.id_evento = ?2", nativeQuery = true)
	Organizadores findByPessoaAndEvento(Long id_pessoa, Long id_evento);
}
