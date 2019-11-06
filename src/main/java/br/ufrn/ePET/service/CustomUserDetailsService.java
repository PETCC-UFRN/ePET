package br.ufrn.ePET.service;
/*
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	private final PessoaRepository pessoaRepository;
	
	public CustomUserDetailsService(UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = Optional.ofNullable(usuarioRepository.findByEmail(username))
			.orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado")); 
		
		Pessoa pessoa = Optional.ofNullable(pessoaRepository.findByUsuario(usuario))
				.orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));
		
		List<GrantedAuthority> listuser = AuthorityUtils.createAuthorityList("ROLE_" + pessoa.getTipo_usuario().getNome());
		//System.out.println(pessoa.getTipo_usuario().getNome());
		return new User(usuario.getEmail(), usuario.getSenha(), listuser);
	}

}*/
