package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.Tutoria;
import br.ufrn.ePET.models.TutoriaOnline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoriaRepository extends JpaRepository<TutoriaOnline, Long>{

}
