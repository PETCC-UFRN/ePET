package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
	@Query(value = "SELECT * FROM pagamento WHERE referencia_pagseguro = ?", nativeQuery = true)
	Pagamento findByReferencia(String referencia);
	
	@Query(value = "SELECT * FROM pagamento WHERE id_participante = ?", nativeQuery = true)
	Pagamento findByParticipante(Long id_participante);
}
