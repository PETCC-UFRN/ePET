package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Atividades;

public interface AtividadesRepository extends JpaRepository<Atividades, Long>{
	
	Atividades findByTitulo(String titulo);
	
	Atividades findByConteudo(String conteudo);
}
