package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
	
	@Query(value = "SELECT count(id_participante) FROM participante WHERE id_evento = 1 AND confirmado = true;", nativeQuery = true)
	int countAtivos(Long id);
	
	List<Participante> findByEspera(boolean espera);
	
	@Query(value = "SELECT * FROM participante u WHERE u.id_pessoa = ?1 AND ORDER BY u.id_participante DESC", nativeQuery = true)
	Page<Participante> findByPessoa(Long id, Pageable pageable);

	@Query(value = "SELECT u.id_participante, u.confirmado, u.espera, u.id_evento, u.id_pessoa FROM participante u INNER JOIN pessoa p ON p.id_pessoa = u.id_pessoa WHERE p.nome LIKE %?1% OR p.cpf LIKE %?1%", nativeQuery = true)
	Page<Participante> findByNomeOuCpfPessoa(String nome, Pageable pageable);

	@Query(value = "SELECT u.id_participante, u.confirmado, u.espera, u.id_evento, u.id_pessoa FROM participante u INNER JOIN evento e ON e.id_evento = u.id_evento WHERE e.titulo LIKE %?1%", nativeQuery = true)
	Page<Participante> findByTituloEvento(String titulo, Pageable pageable);
}
