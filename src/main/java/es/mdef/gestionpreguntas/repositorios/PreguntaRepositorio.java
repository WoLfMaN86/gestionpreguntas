package es.mdef.gestionpreguntas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionpreguntas.entidades.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long> {
//Solo rellenamos el repositorio de quien tiene el listado.
	List<Pregunta> findPreguntaByUsuarioId(Long id);

	List<Pregunta> findByFamiliaId(Long id);
	
	List<Pregunta> findByEnunciado(String enunciado);
}
