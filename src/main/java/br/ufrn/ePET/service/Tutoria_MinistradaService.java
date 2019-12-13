package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.config.EmailConfig;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria_Ministrada;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.TutoriaRepository;
import br.ufrn.ePET.repository.Tutoria_Ministrada_Repository;

@Service
public class Tutoria_MinistradaService {
	
	private final Tutoria_Ministrada_Repository tutoria_Ministrada_Repository;
	private final PessoaRepository pessoaRepository;
	private final TutoriaRepository tutoriaRepository;
	
	
	public Tutoria_MinistradaService(Tutoria_Ministrada_Repository tutoria_Ministrada_Repository,
									 PessoaRepository pessoaRepository, TutoriaRepository tutoriaRepository) {
		this.tutoria_Ministrada_Repository = tutoria_Ministrada_Repository;
		this.pessoaRepository = pessoaRepository;
		this.tutoriaRepository = tutoriaRepository;
		
	}
	
	public Tutoria_Ministrada buscar( Long id ) {
		return tutoria_Ministrada_Repository.findById(id).get();
	}
	
	public Page<Tutoria_Ministrada> buscar(Pageable pageable){
		return tutoria_Ministrada_Repository.findAll(pageable);
	}
	
	public Tutoria_Ministrada salvar(Long id_pessoa, Long id_tutoria, Tutoria_Ministrada tutoriaMinistrada) {
		if(this.pessoaRepository.findById(id_pessoa).isPresent())
			tutoriaMinistrada.setPessoa(this.pessoaRepository.findById(id_pessoa).get());
		else
			throw new ResourceNotFoundException("ID: " + id_pessoa + " não encontrado!");
		
		if(this.tutoriaRepository.findById(id_tutoria).isPresent())
			tutoriaMinistrada.setTutoria(this.tutoriaRepository.findById(id_tutoria).get());
		else
			throw new ResourceNotFoundException("ID:  " + id_tutoria + " não encontrado!");
		
		marcarTutoria(tutoriaMinistrada);
		return tutoria_Ministrada_Repository.save(tutoriaMinistrada);
		
	}
	
	public void marcarTutoria(Tutoria_Ministrada tm) {
		EmailConfig emailConfig = new EmailConfig();
		emailConfig.enviarEmail(tm.getTutoria().getPetiano().getPessoa().getUsuario().getEmail(), tm.getPessoa().getUsuario().getEmail(), 
				tm.getTutoria().getDisciplina().getCodigo() + " " + tm.getTutoria().getDisciplina().getNome(), 
				tm.getTutoria().getPetiano().getPessoa().getNome());
	}
	
	public void ativar(Long id_tutoria_ministrada) {
		if (tutoria_Ministrada_Repository.findById(id_tutoria_ministrada).isPresent()) {
			Tutoria_Ministrada tm = tutoria_Ministrada_Repository.findById(id_tutoria_ministrada).get();
			tm.setAtivo(true);
			tutoria_Ministrada_Repository.save(tm);
		} else {
			throw new ResourceNotFoundException("ID:  " + id_tutoria_ministrada + " não encontrado!");
		}
			
	}
	
	public List<Tutoria_Ministrada> tutoriasAtivas(){
		List<Tutoria_Ministrada> tutorias = tutoria_Ministrada_Repository.findAll();
		LocalDate today = LocalDate.now();
		
		for(Tutoria_Ministrada tutoria : tutorias) {
			if(tutoria.getData().isBefore(today)) {
				tutorias.remove(tutoria);
			}
		}
		
		return tutorias;
	}
	
	public List<Tutoria_Ministrada> tutoriasAtivas(Long id_tutoria){
		List<Tutoria_Ministrada> tutorias = tutoria_Ministrada_Repository.findAll();
		
		for(Tutoria_Ministrada tutoria: tutorias) {
			if(tutoria.getData().isBefore(LocalDate.now()) && 
					!(tutoria.getTutoria().getIdTutoria() == id_tutoria)
					&& !tutoria.getAtivo()) {
				tutorias.remove(tutoria);
			}
		}
		
		return tutorias;
	}
	
	public void removerTutoria(Long id_tutoria) {
		Tutoria_Ministrada tutoria = tutoria_Ministrada_Repository.findById(id_tutoria).get();
		tutoria.setAtivo(false);
		
		tutoria_Ministrada_Repository.save(tutoria);
		
	}

}
