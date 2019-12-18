package br.ufrn.ePET.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
	@Query(value = "SELECT * FROM pagamento WHERE referencia_pagseguro = ?", nativeQuery = true)
	List<Pagamento> findByReferencia(String referencia);
}
