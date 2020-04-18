package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Anexo_Noticia;

public interface Anexo_NoticiaRepository extends JpaRepository<Anexo_Noticia, Long>{
	
	@Query(value = "SELECT * FROM anexo_noticia a WHERE a.id_noticia = ?1", nativeQuery = true)
	List<Anexo_Noticia> findById_noticia(Long id_noticia);

	@Modifying
	@Query(value = "DELETE FROM anexo_noticia a WHERE a.id_noticia = ?1", nativeQuery = true)
	void deleteByIdNoticia(Long id_noticia);
}
