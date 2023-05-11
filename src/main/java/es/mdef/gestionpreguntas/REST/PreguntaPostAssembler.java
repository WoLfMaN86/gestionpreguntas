package es.mdef.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Pregunta;

@Component
public class PreguntaPostAssembler implements RepresentationModelAssembler<Pregunta, PreguntaPostModel> {

	@Override
	public PreguntaPostModel toModel(Pregunta entity) {
		PreguntaPostModel model = new PreguntaPostModel();
		model.setEnunciado(entity.getEnunciado());
		model.add(linkTo(methodOn(PreguntaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(entity.getUsuario().getId()))
						.withRel("usuariosssssssssssss")
		// Hubiera sido en vez del one en este caso diria "usuarios" y no hariamos el
		// getUsuario.
		);
		return model;
	}

	public Pregunta toEntity(PreguntaPostModel model) {
		Pregunta pregunta = new Pregunta();
		pregunta.setEnunciado(model.getEnunciado());
		pregunta.setUsuario(model.getUsuario());

		return pregunta;

	}

}
