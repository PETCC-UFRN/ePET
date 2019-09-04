package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.PetianoRepository;

@Service
public class PetianoService {

	private final PetianoRepository petianoRepository;
	private final PessoaRepository pessoaRepository;
	private final PessoaService pessoaservice;

	@Autowired
	public PetianoService(PetianoRepository petianoRepository, PessoaRepository pessoaRepository, PessoaService pessoaservice) {
		this.petianoRepository = petianoRepository;
		this.pessoaRepository = pessoaRepository;
		this.pessoaservice = pessoaservice;
	}

	
	public Petiano buscar(Long id) {
		Petiano petiano = petianoRepository.findById(id).get();
		return petiano instanceof Petiano ? petiano : null;
	}
	
	public List<Petiano> buscar(){
		List<Petiano> listPetianos = petianoRepository.findAll();
		List<Petiano> listPetianosativos = new ArrayList<Petiano>();
		for(Petiano petiano : listPetianos) {
			if (petiano.getPessoa().getTipo_usuario().getNome() != "petiano") {
				listPetianosativos.add(petiano);
			}
		}
		return petianoRepository.findAll();
	}
	
	public Petiano salvar(Long id, Petiano petiano){
		Pessoa pessoa = pessoaRepository.findById(id).get();
		/*
		List<Petiano> listPetianos = petianoRepository.findAll();
		for (Petiano pet : listPetianos) {
			if (pet.getPessoa().equals(pessoa)) {
				petiano = pet;
			}
			
		}*/
		pessoa = pessoaservice.salvar(Long.valueOf(1), pessoa);
		petiano.setPessoa(pessoa);	
		return petianoRepository.save(petiano);
		
	}
	
	public Petiano remover(Long id){
		Petiano petiano = petianoRepository.findById(id).get();
		Pessoa pessoa = petiano.getPessoa();
		pessoa = pessoaservice.salvar(Long.valueOf(3), pessoa);
		petiano.setPessoa(pessoa);
		petiano.setData_egresso(LocalDate.now());

		return petianoRepository.save(petiano);
		
	}
	
	public Petiano editar(Long id, Petiano petiano){
		Petiano petianoAntigo = petianoRepository.findById(id).get();
		Pessoa pessoa = petianoAntigo.getPessoa();
		petiano.setPessoa(pessoa);
		petiano.setIdPetiano(petianoAntigo.getIdPetiano());

		return petianoRepository.save(petiano);
		
	}
}
