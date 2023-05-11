package es.mdef.gestionpreguntas.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionpreguntas.entidades.Familia;

public interface FamiliaRepositorio extends JpaRepository<Familia, Long> {
//	Optional<Familia> findById(Long id);
}
