package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return petianoRepository.findById(id).get();
	}
	
	public List<Petiano> buscarAtuais(){
		List<Petiano> listPetianos = petianoRepository.findAll();
		for(Petiano petiano : listPetianos) {
			if (petiano.getPessoa().getTipo_usuario().getNome().equalsIgnoreCase("petiano")) {
				listPetianos.remove(petiano);
			}
		}
		return listPetianos;
	}
	
	public List<Petiano> buscarAntigos(){
		List<Petiano> listPetianos = petianoRepository.findAll();
		for(Petiano petiano : listPetianos) {
			if (!petiano.getPessoa().getTipo_usuario().getNome().equalsIgnoreCase("petiano")) {
				listPetianos.remove(petiano);
			}
		}
		return listPetianos;
	}
	
	public Petiano salvar(Long id, Petiano petiano){
		Pessoa pessoa = pessoaRepository.findById(id).get();
		pessoa.setTipo_usuario(tipoUsuarioRepository.findByNome("petiano"));
		petiano.setPessoa(pessoaRepository.save(pessoa));	
		return petianoRepository.save(petiano);
		
	}
	
	
	public Petiano remover(Long id){
		Petiano petiano = petianoRepository.findById(id).get();
		Pessoa pessoa = petiano.getPessoa();
		pessoa.setTipo_usuario(tipoUsuarioRepository.findByNome("comum"));
		petiano.setPessoa(pessoaRepository.save(pessoa));
		petiano.setData_egresso(LocalDate.now());
		return petianoRepository.save(petiano);	
	}
	
	public Petiano editar(Long id, Petiano petiano){
		petiano.setPessoa(pessoaRepository.findById(id).get());
		return petianoRepository.save(petiano);
	}
}
