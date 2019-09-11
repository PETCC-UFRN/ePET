package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import br.ufrn.ePET.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
	
}
