package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufrn.ePET.models.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

}
