package br.ufrn.ePET.service;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.ResetDTO;
import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.models.ValidadorUsuario;
import br.ufrn.ePET.repository.UsuarioRepository;
import br.ufrn.ePET.repository.ValidadorUsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ValidadorUsuarioService {

    private final ValidadorUsuarioRepository validadorUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ValidadorUsuarioService(ValidadorUsuarioRepository validadorUsuarioRepository, UsuarioRepository usuarioRepository) {
        this.validadorUsuarioRepository = validadorUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(String code){
        ValidadorUsuario validadorUsuario = validadorUsuarioRepository.findByCode(code);
        if(validadorUsuario != null){
            Usuario usuario = usuarioRepository.findById(validadorUsuario.getUsuario().getidUsuario()).get();
            if(usuario != null){
                usuario.setValidado(true);
                usuarioRepository.save(usuario);
                validadorUsuarioRepository.delete(validadorUsuario);
            } else {
                throw new ResourceNotFoundException("Usuario nao encontrado!");
            }
        } else {
            throw new CustomException("Código inválido ou inexistente", HttpStatus.BAD_REQUEST);
        }
    }

    public void validarSenha(ResetDTO resetDTO){
        ValidadorUsuario validadorUsuario = validadorUsuarioRepository.findByCode(resetDTO.getCode());
        if(validadorUsuario != null){
            Usuario usuario = usuarioRepository.findById(validadorUsuario.getUsuario().getidUsuario()).get();
            if(usuario != null){
                String senha = new BCryptPasswordEncoder().encode(resetDTO.getSenha());
                System.out.println(senha);
                usuario.setSenha(senha);
                usuarioRepository.save(usuario);
                validadorUsuarioRepository.delete(validadorUsuario);
            } else {
                throw new ResourceNotFoundException("Usuario nao encontrado!");
            }
        } else {
            throw new CustomException("Código inválido ou inexistente", HttpStatus.BAD_REQUEST);
        }
    }
}
