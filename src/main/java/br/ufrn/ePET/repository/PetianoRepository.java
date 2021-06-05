package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;

public interface PetianoRepository extends JpaRepository<Petiano, Long>{

	@Query(value = "SELECT p.id_petiano, p.area_interesse, p.data_egresso, p.data_ingresso, p.foto, p.lattes, p.site_pessoal, p.id_pessoa FROM petiano p, pessoa u WHERE p.id_pessoa = u.id_pessoa AND p.data_egresso IS NULL ORDER BY u.nome", nativeQuery = true)
	Page<Petiano> findByAtuais(Pageable pageable);
	
	@Query(value = "SELECT p.id_petiano, p.area_interesse, p.data_egresso, p.data_ingresso, p.foto, p.lattes, p.site_pessoal, p.id_pessoa FROM petiano p, pessoa u WHERE p.id_pessoa = u.id_pessoa AND p.data_egresso IS NOT NULL ORDER BY u.nome", nativeQuery = true)
	Page<Petiano> findByAntigos(Pageable pageable);
	
	@Query(value = "SELECT * FROM petiano p WHERE p.id_pessoa = ?1", nativeQuery = true)
	Petiano findByPessoa(Long id);

	@Query(value = "SELECT p.id_petiano, p.area_interesse, p.data_egresso, p.data_ingresso, p.foto, p.lattes, p.site_pessoal, p.id_pessoa FROM petiano p, pessoa u WHERE p.id_pessoa = u.id_pessoa AND u.nome LIKE %?1% OR u.cpf LIKE %?1%", nativeQuery = true)
	Page<Petiano> findByNomeOuCpf(String search, Pageable pageable);

	@Query(value = "SELECT p.id_petiano, p.area_interesse, p.data_egresso, p.data_ingresso, p.foto, p.lattes, p.site_pessoal, p.id_pessoa FROM petiano p, pessoa u, tipo_usuario t WHERE p.id_pessoa = u.id_pessoa AND t.id_tipo_usuario = u.id_tipo_usuario AND t.nome = 'tutor'", nativeQuery = true)
	Page<Petiano> findTutores(Pageable pageable);
}
