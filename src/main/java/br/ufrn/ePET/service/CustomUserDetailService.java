package br.ufrn.ePET.service;

/*import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;

/*@Component
public class CustomUserDetailService implements UserDetailsService{
	
	private final PessoaRepository pessoaRepository;
	
	@Autowired
	public CustomUserDetailService(PessoaRepository pessoaRepository) {
		//super();
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
		Pessoa pessoa = Optional.ofNullable(pessoaRepository.findByNome(nome))
				.orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority> authorityListTutor = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_TUTOR");
		if(pessoa.getTipo_usuario().getNome() == "TUTOR") {
			return new User(pessoa.getEmail(), pessoa.getSenha(),authorityListTutor);		
		}
		return new User(pessoa.getNome(), pessoa.getSenha(), pessoa.getTipo_usuario().getNome().equalsIgnoreCase("petiano") ? authorityListAdmin : authorityListUser);
	}

}*/
