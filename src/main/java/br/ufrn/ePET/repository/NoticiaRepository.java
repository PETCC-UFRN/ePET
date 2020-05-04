package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.ufrn.ePET.models.Noticia;
import org.springframework.data.jpa.repository.Query;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @Query(value = "SELECT * FROM noticia n ORDER BY n.id_noticia DESC", nativeQuery = true)
    Page<Noticia> findNews(Pageable pageable);

    @Query(value = "SELECT * FROM noticia n WHERE n.titulo LIKE %?1%", nativeQuery = true)
    Page<Noticia> findByTitulo(String titulo, Pageable pageable);
}
