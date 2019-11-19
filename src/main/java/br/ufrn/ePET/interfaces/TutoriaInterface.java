package br.ufrn.ePET.interfaces;

import br.ufrn.ePET.models.Tutoria_Ministrada;

public interface TutoriaInterface {

	void marcarTutoria(Long id_petiano, Long id_disciplina, Tutoria_Ministrada tutoria_Ministrada);
}
