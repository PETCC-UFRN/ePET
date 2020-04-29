package br.ufrn.ePET.service;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.*;
import br.ufrn.ePET.repository.ValidadorUsuarioRepository;
import br.ufrn.ePET.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.DuplicatedEntryException;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;
import br.ufrn.ePET.repository.UsuarioRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final Tipo_UsuarioRepository tipoUsuarioRepository;
	private final PessoaRepository pessoaRepository;
	private final ValidadorUsuarioRepository validadorUsuarioRepository;
	private JavaMailSender javaMailSender;
	//@Autowired
	//private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, Tipo_UsuarioRepository tipoUsuarioRepository,
			PessoaRepository pessoaRepository, ValidadorUsuarioRepository validadorUsuarioRepository, JavaMailSender javaMailSender) {
		this.usuarioRepository = usuarioRepository;
		this.tipoUsuarioRepository = tipoUsuarioRepository;
		this.pessoaRepository = pessoaRepository;
		this.validadorUsuarioRepository = validadorUsuarioRepository;
		this.javaMailSender = javaMailSender;
	}

	public String signin(String username, String password){
		try{
			Usuario u = usuarioRepository.findByEmail(username);
			if(u.isValidado()){
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				return "Bearer " + jwtTokenProvider.createToken(username, pessoaRepository.findByUsuario(u).getTipo_usuario().getNome());
			} else {
				throw new CustomException("Usuario nao validado", HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).isPresent() ? 
				usuarioRepository.findById(id).get(): null;
	}
	
	public Page<Usuario> buscar(Pageable pageable){
		return usuarioRepository.findAll(pageable);
	}

	public Usuario buscarByEmail( String email ){
		return usuarioRepository.findByEmail(email);
	}
	
	public void signup(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
		if(usuario == null) {
			usuario = new Usuario();
			usuario.setEmail(usuarioDTO.getEmail());
			String senha = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
			usuario.setSenha(senha);
			usuarioRepository.save(usuario);
		} else {
			throw new DuplicatedEntryException("Já existe um usuário cadastrado com esses dados!");
		}
		
		Tipo_Usuario tipo_Usuario = tipoUsuarioRepository.findByNome("comum");
		
		Pessoa pessoa = pessoaRepository.findByCpf(usuarioDTO.getCpf());
		if(pessoa == null) {
			pessoa = new Pessoa();
			pessoa.setNome(usuarioDTO.getNome());
			pessoa.setCpf(usuarioDTO.getCpf());
			pessoa.setTipo_usuario(tipo_Usuario);
			pessoa.setUsuario(usuario);
			pessoaRepository.save(pessoa);
		} else {
			throw new DuplicatedEntryException("Já existe uma pessoa cadastrada com esses dados!");
		}

		ValidadorUsuario validadorUsuario = new ValidadorUsuario();
		validadorUsuario.setUsuario(usuario);
		validadorUsuario.setCode(generateCode());
		validadorUsuarioRepository.save(validadorUsuario);
		enviarEmail(validadorUsuario, pessoa);
	}

	public void esqueceuSenha(String email){
		Usuario usuario =  usuarioRepository.findByEmail(email);
		if(usuario != null){
			ValidadorUsuario validadorUsuario = new ValidadorUsuario();
			validadorUsuario.setUsuario(usuario);
			validadorUsuario.setCode(generateCode());
			validadorUsuarioRepository.save(validadorUsuario);
			enviarEmail(validadorUsuario, email);
		}
	}

	public void enviarEmail(ValidadorUsuario validadorUsuario, Pessoa pessoa){
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(pessoa.getUsuario().getEmail());
		smm.setText("Olá " + pessoa.getNome() + "!\n"
				+ "Esse é o seu código de ativação!\n" + "https://epet.imd.ufrn.br:8443/api/validation/?code=" + validadorUsuario.getCode());
		try {
			javaMailSender.send(smm);
		} catch (Exception e) {
			throw new RuntimeException("Houve algum erro no envio do seu email!\n" + e);
		}
	}

	public void enviarEmail(ValidadorUsuario validadorUsuario, String email){
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(email);
		smm.setText("Olá!\n"
				+ "Esse é o link para mudança da sua senha!\n" + "https://epet.imd.ufrn.br/mudarSenha/?code=" + validadorUsuario.getCode());
		try {
			javaMailSender.send(smm);
		} catch (Exception e) {
			throw new RuntimeException("Houve algum erro no envio do seu email!\n" + e);
		}
	}

	public String generateCode(){
		SecureRandom secureRandom = new SecureRandom(); //threadsafe
		Base64.Encoder base64Encoder = Base64.getUrlEncoder();
		byte[] randomBytes = new byte[24];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}

	public void atualizar(String email, String senha){
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario != null){
			usuario.setEmail(email);
			usuario.setSenha(senha);
			usuarioRepository.save(usuario);
		} else {
			throw new ResourceNotFoundException("Nenhum usuario encontrado!");
		}
	}
	
	
}
