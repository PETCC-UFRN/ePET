package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.CertificadoOrganizador;
import br.ufrn.ePET.models.Organizadores;

public interface CertificadoOrganizadoresRepository extends JpaRepository<CertificadoOrganizador, Long>{
	
	CertificadoOrganizador findByHash(String hash);
	
	CertificadoOrganizador findByOrganizadores(Organizadores organizador);
	
}
