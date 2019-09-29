package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Anexo_Noticia;

public interface Anexo_NoticiaRepository extends JpaRepository<Anexo_Noticia, Long>{
	List<Anexo_Noticia> findById_noticia(Long id_noticia);
}
