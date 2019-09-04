package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;

public interface PetianoRepository extends JpaRepository<Petiano, Long>{
	//Petiano findbyPessoa(Pessoa pessoa);
}
