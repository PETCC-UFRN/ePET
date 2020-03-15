package br.ufrn.ePET.repository;

import br.ufrn.ePET.models.ValidadorUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidadorUsuarioRepository extends JpaRepository<ValidadorUsuario, Long> {
    ValidadorUsuario findByCode(String uuid);
}
