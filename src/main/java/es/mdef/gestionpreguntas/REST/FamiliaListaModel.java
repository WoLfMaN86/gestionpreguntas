package es.mdef.gestionpreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "familia", collectionRelation = "familias") // Para que entienda bien cuando es singular y
																		// plural, el primero se usa en el toModel y el
																		// segundo en el ToCollection
//@Relation(collectionRelation = "familias")
public class FamiliaListaModel extends RepresentationModel<FamiliaListaModel> {

	private String enunciado;
	private Long tamano;

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;

	}

	public Long getTamano() {
		return tamano;
	}

	public void setTamano(Long tamano) {
		this.tamano = tamano;
	}

	@Override
	public String toString() {
		return String.format("FamiliaListaModel [getEnunciado()=%s, getTamano()=%s]", getEnunciado(), getTamano());
	}
}
