package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;

@Service
public class PetianoService {

	private final PetianoRepository petianoRepository;
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipoUsuarioRepository;
	@Autowired
	public PetianoService(PetianoRepository petianoRepository, PessoaRepository pessoaRepository, Tipo_UsuarioRepository tipoUsuarioRepository) {
		this.petianoRepository = petianoRepository;
		this.pessoaRepository = pessoaRepository;
		this.tipoUsuarioRepository = tipoUsuarioRepository;
	}

	
	public Petiano buscar(Long id) {
		return petianoRepository.findById(id).isPresent() ? 
				petianoRepository.findById(id).get(): null;
	}
	
	public List<Petiano> buscarAtuais(){
		List<Petiano> listPetianos = petianoRepository.findAll();
		for(Petiano petiano : listPetianos) {
			System.out.println(petiano.getPessoa().getTipo_usuario().getNome());
			if (!petiano.getPessoa().getTipo_usuario().getNome().equalsIgnoreCase("petiano") &&
					!petiano.getPessoa().getTipo_usuario().getNome().equalsIgnoreCase("tutor")) {
				listPetianos.remove(petiano);
			}
		}
		if (listPetianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum petiano cadastrado");
		return listPetianos;
	}
	
	public List<Petiano> buscarAntigos(){
		List<Petiano> listPetianos = petianoRepository.findAll();
		List<Petiano> listPetianos_aux = petianoRepository.findAll();
		for(Petiano petiano : listPetianos_aux) {
			if (petiano.getData_egresso() == null) {
				listPetianos.remove(petiano);
			}
		}
		listPetianos_aux.clear();
		if (listPetianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum ex-petiano cadastrado");
		return listPetianos;
	}
	
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
	
	
	public Petiano remover(Long id){
		Petiano petiano = new Petiano();
		if(petianoRepository.findById(id).isPresent())
			petiano = petianoRepository.findById(id).get();
		else throw new ResourceNotFoundException("Petiano com id "+ id + " não encontrada");
			
		
		//Petiano petiano = petianoRepository.findById(id).get();
		Pessoa pessoa = petiano.getPessoa();
		pessoa.setTipo_usuario(tipoUsuarioRepository.findByNome("comum"));
		petiano.setPessoa(pessoaRepository.save(pessoa));
		petiano.setData_egresso(LocalDate.now());
		return petianoRepository.save(petiano);	
	}
	
	public Petiano editar(Long id_pessoa, Petiano petiano){
		Pessoa pessoa = new Pessoa();
		if(pessoaRepository.findById(id_pessoa).isPresent())
			pessoa = pessoaRepository.findById(id_pessoa).get();
		else throw new ResourceNotFoundException("Pessoa com id "+ id_pessoa + " não encontrada");
		petiano.setPessoa(pessoa);
		return petianoRepository.save(petiano);
	}
}
