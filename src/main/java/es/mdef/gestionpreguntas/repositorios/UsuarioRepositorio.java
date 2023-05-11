package es.mdef.gestionpreguntas.repositorios;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.entidades.Usuario.Role;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	List<Usuario> findUserByUsername(String username);
	Optional<Usuario> findByUsername(String username);

}
