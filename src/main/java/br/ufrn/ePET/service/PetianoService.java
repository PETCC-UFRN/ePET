package br.ufrn.ePET.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;
import br.ufrn.ePET.repository.TutoriaRepository;
import br.ufrn.ePET.repository.Tutoria_Ministrada_Repository;

@Service
public class PetianoService {

	private final PetianoRepository petianoRepository;
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipoUsuarioRepository;
	private final TutoriaRepository tutoriaRepository;
	private final Tutoria_Ministrada_Repository tutoriaMinistrada_Repository;
	@Autowired
	public PetianoService(PetianoRepository petianoRepository, PessoaRepository pessoaRepository, 
			Tipo_UsuarioRepository tipoUsuarioRepository, TutoriaRepository tutoriaRepository, Tutoria_Ministrada_Repository tutoriaMinistrada_Repository) {
		this.petianoRepository = petianoRepository;
		this.pessoaRepository = pessoaRepository;
		this.tipoUsuarioRepository = tipoUsuarioRepository;
		this.tutoriaRepository = tutoriaRepository;
		this.tutoriaMinistrada_Repository = tutoriaMinistrada_Repository;
	}

	
	public Petiano buscar(Long id) {
		return petianoRepository.findById(id).isPresent() ? 
				petianoRepository.findById(id).get(): null;
	}
	
	public Petiano buscarPorPessoa(Long id) {
		return petianoRepository.findByPessoa(id);
	}
	
	public Page<Petiano> buscarAtuais(Pageable pageable){
		return petianoRepository.findByAtuais(pageable); 
	}
	
	public Page<Petiano> buscarAntigos(Pageable pageable){
		return petianoRepository.findByAntigos(pageable);
	}

	public Page<Petiano> buscarPorNomeOuCpf(String search, Pageable pageable){
		return petianoRepository.findByNomeOuCpf(search, pageable);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Petiano salvar(Long id, Petiano petiano){
		Pessoa pessoa = new Pessoa();
		if(pessoaRepository.findById(id).isPresent())
			pessoa = pessoaRepository.findById(id).get();
		else throw new ResourceNotFoundException("Pessoa com id "+ id + " não encontrada");
		
		//Pessoa pessoa = pessoaRepository.findById(id).get();
		pessoa.setTipo_usuario(tipoUsuarioRepository.findByNome("petiano"));
		petiano.setPessoa(pessoaRepository.save(pessoa));	
		return petianoRepository.save(petiano);
		
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Petiano remover(Long id){
		Petiano petiano = new Petiano();
		if(petianoRepository.findById(id).isPresent())
			petiano = petianoRepository.findById(id).get();
		else throw new ResourceNotFoundException("Petiano com id "+ id + " não encontrada");
			
		
		//Petiano petiano = petianoRepository.findById(id).get();
		Pessoa pessoa = petiano.getPessoa();
		pessoa.setTipo_usuario(tipoUsuarioRepository.findByNome("comum"));
		tutoriaRepository.desativarAtivos(id);
		tutoriaMinistrada_Repository.desativarAtivosPorPetiano(id);
		petiano.setPessoa(pessoaRepository.save(pessoa));
		petiano.setData_egresso(LocalDate.now());
		return petianoRepository.save(petiano);	
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Petiano editar(Long id_pessoa, Petiano petiano){
		Pessoa pessoa = new Pessoa();
		if(pessoaRepository.findById(id_pessoa).isPresent())
			pessoa = pessoaRepository.findById(id_pessoa).get();
		else throw new ResourceNotFoundException("Pessoa com id "+ id_pessoa + " não encontrada");
		petiano.setPessoa(pessoa);
		return petianoRepository.save(petiano);
	}
}