package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.Informacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InformacoesRepository extends JpaRepository<Informacoes, Long> {

    @Query(value = "SELECT * FROM informacoes LIMIT 1", nativeQuery = true)
    Informacoes findInfo();
}
