package es.mdef.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Pregunta;
import es.mdef.gestionpreguntas.entidades.UsuarioAdmin;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin;
import es.mdef.gestionpreguntas.entidades.Usuario;

import es.mdef.gestionpreguntas.GestionpreguntasApplication;
import es.mdef.gestionpreguntas.REST.PreguntaModel;

@Component
public class PreguntaAssembler implements RepresentationModelAssembler<Pregunta, PreguntaModel> {
//	public final Logger log;
//
//	public PreguntaAssembler() {
//		log = GestionpreguntasApplication.log;
//	}
	@Override
	public PreguntaModel toModel(Pregunta entity) {
		PreguntaModel model = new PreguntaModel();
		model.setEnunciado(entity.getEnunciado());
		model.add(linkTo(methodOn(PreguntaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(entity.getUsuario().getId())).withRel("usuariosssssss")
		// Hubiera sido en vez del one en este caso diria "usuarios" y no hariamos el
		// getUsuario.
		);
		return model;
	}
//	public Pregunta toEntity(PreguntaPostModel model) {
//		Pregunta pregunta = new Pregunta();
//		pregunta.setEnunciado(model.getEnunciado());
//		pregunta.setUsuario(model.getUsuario());
//		pregunta.setFamilia(model.getFamilia());
//		
//		return pregunta;
//	}
}
