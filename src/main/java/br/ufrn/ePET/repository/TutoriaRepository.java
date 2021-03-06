package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.Tutoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TutoriaRepository extends JpaRepository<Tutoria, Long>{

	@Modifying
	@Query(value = "UPDATE tutoria t SET t.ativo = 0 WHERE id_petiano = ?1", nativeQuery = true)
	void desativarAtivos(Long id);
	
	@Query(value = "SELECT t FROM Tutoria t WHERE t.ativo = 1")
	Page<Tutoria> findByAtivos(Pageable pageable);

	@Query(value = "SELECT t.id_tutoria, t.id_disciplina, t.id_petiano, t.ativo FROM ((tutoria t INNER JOIN petiano p ON p.id_petiano = t.id_petiano) INNER JOIN pessoa e ON e.id_pessoa = p.id_pessoa) WHERE e.nome LIKE %?1% OR e.cpf LIKE %?1%", nativeQuery = true)
	Page<Tutoria> findByNomeOuCpf(String search, Pageable pageable);

	@Query(value = "SELECT t.id_tutoria, t.id_disciplina, t.id_petiano, t.ativo FROM tutoria t INNER JOIN disciplina d ON d.id_disciplina = t.id_disciplina WHERE (d.codigo LIKE %?1% OR d.nome LIKE %?1%) AND t.ativo = 1", nativeQuery = true)
	Page<Tutoria> findByCodigoOuNome(String search, Pageable pageable);
	
	@Query(value = "SELECT * FROM tutoria t WHERE t.id_petiano = ?1 AND t.id_disciplina = ?2", nativeQuery = true)
	Tutoria findByPetianoDisciplina(Long id_petiano, Long id_disciplina);

}
