package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Certificado;
import br.ufrn.ePET.models.Participante;

public interface CertificadoRepository extends JpaRepository<Certificado, Long>{
	
	Certificado findByHash(String hash);
	
	Certificado findByParticipante(Participante participante);
	
}
