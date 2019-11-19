package br.ufrn.ePET.interfaces;

import br.ufrn.ePET.models.Tutoria;

public interface TutoriaInterface {

	void marcarTutoria(Long id_petiano, Long id_disciplina, Tutoria tutoria);
}
