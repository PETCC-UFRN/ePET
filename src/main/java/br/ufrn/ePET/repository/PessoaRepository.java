package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Usuario;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	Pessoa findByNome(String nome);
	
	Pessoa findByUsuario(Usuario usuario);
}
