package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

}
