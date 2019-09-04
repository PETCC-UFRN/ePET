package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	Pessoa findByNome(String nome);
}
