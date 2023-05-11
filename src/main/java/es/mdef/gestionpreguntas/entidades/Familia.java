package es.mdef.gestionpreguntas.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "familias")
public class Familia extends es.mdef.support.Familia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true, name = "id_familia")
	// En teoria esto lo coge de la clase externa
//	@Column(name="enunciado")
//	private String enunciado;

	@OneToMany(mappedBy = "familia")
	List<Pregunta> preguntas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// En teoria lo que pongo abajo lo coge de la clase externa.
//	public String getEnunciado() {
//		return enunciado;
//	}
//	public void setEnunciado(String enunciado) {
//		this.enunciado = enunciado;
//	}
	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	// El tamaño es la cantidad de preguntas por lo tanto
	public long getTamano() {
		return getPreguntas().size();
	}

	@Override
	public String toString() {
		return "Familia [getId()=" + getId() + ", getEnunciado()=" + getEnunciado() + ", getPreguntas()="
				+ getPreguntas() + "]";
	}

}
