package es.mdef.gestionpreguntas.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Familia;
import es.mdef.gestionpreguntas.GestionpreguntasApplication;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

@Component
public class FamiliaAssembler implements RepresentationModelAssembler<Familia, FamiliaModel> {
	
	//Añadimos lo del log
	
	public final Logger log;

	public FamiliaAssembler() {
		log = GestionpreguntasApplication.log;
	}
//Cambiamos el entityModel por FamiliaModel, ya que disponemos de un Modelo.
//	@Override
//	public EntityModel<Familia> toModel(Familia entity) {
//		EntityModel<Familia> model = EntityModel.of(entity);
//		WebMvcLinkBuilder selfLink = linkTo(methodOn(FamiliaController.class).one(entity.getId()));
//		model.add(
//			selfLink.withSelfRel(),
//			selfLink.slash("preguntas").withRel("preguntas")
//		);
//		return model;
//	}

	// Vamos a hacer lo de arriba con el Familia Model
	@Override
	public FamiliaModel toModel(Familia entity) {
		FamiliaModel model = new FamiliaModel();
//		FamiliaModel model = EntityModel.of(entity); Esto ya no haria falta porque creamos el objeto arriba y no usamos entity.
		model.setEnunciado(entity.getEnunciado());
		model.setTamano(entity.getTamano());
		WebMvcLinkBuilder selfLink = linkTo(methodOn(FamiliaController.class).one(entity.getId()));
		model.add(selfLink.withSelfRel(), selfLink.slash("preguntas").withRel("preguntas"));
		return model;
	}

	public Familia toEntity(FamiliaPostModel model) {
		Familia familia = new Familia();
		familia.setEnunciado(model.getEnunciado());
		return familia;
	}

	// Esto no lo entiendo.

//	public final Logger log;
//	
//	public FamiliaAssembler() {
//		log = GestionpreguntasApplication.log;
//	}
//	
//	public FamiliaModel toModel(Familia entity) {
//		FamiliaModel model = new FamiliaModel();
//		model.setEnunciado(entity.getEnunciado());
//		model.setTamano(entity.getTamano());
//		model.add(
//				linkTo(methodOn(FamiliaController.class).one(entity.getId())).withSelfRel()
//				, linkTo(methodOn(FamiliaController.class).usuarios(1)).withRel("usuarios")
//				, linkTo(methodOn(FamiliaController.class).preguntas(entity.getId())).withRel("preguntas")
//				);
//		return model;
//	}
//	
//	public Familia toEntity(FamiliaPostModel model) {
//		Familia familia = new Familia();
//		familia.setEnunciado(model.getEnunciado());
//		return familia;
//	}
}
