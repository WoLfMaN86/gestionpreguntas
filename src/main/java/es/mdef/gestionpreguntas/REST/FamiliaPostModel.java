package es.mdef.gestionpreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.gestionpreguntas.entidades.Familia;

//import jakarta.validation.constraints.NotBlank;

@Relation(itemRelation = "familia")
public class FamiliaPostModel extends RepresentationModel<FamiliaPostModel> {
//	@NotBlank(message="Pone que el enunciado es un campo obligatorio")
	private String enunciado;

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	@Override
	public String toString() {
		return String.format("FamiliaPostModel [getEnunciado()=%s]", getEnunciado());
	}

}
