package es.mdef.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Familia;

@Component
public class FamiliaListaAssembler implements RepresentationModelAssembler<Familia, FamiliaListaModel> {

	@Override
	public FamiliaListaModel toModel(Familia entity) {
		FamiliaListaModel model = new FamiliaListaModel();
		model.setEnunciado(entity.getEnunciado());
		model.setTamano(entity.getTamano());
		model.add(linkTo(methodOn(FamiliaController.class).one(entity.getId())).withSelfRel());
		return model;
	}

	public CollectionModel<FamiliaListaModel> toCollection(List<Familia> lista) {
		CollectionModel<FamiliaListaModel> collection = CollectionModel
				.of(lista.stream().map(this::toModel).collect(Collectors.toList()));
		collection.add(linkTo(methodOn(FamiliaController.class).all()).withRel("familias"));
		return collection;
	}

}