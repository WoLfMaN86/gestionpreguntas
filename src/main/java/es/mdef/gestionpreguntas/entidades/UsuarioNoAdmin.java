package es.mdef.gestionpreguntas.entidades;

import es.mdef.gestionpreguntas.entidades.Usuario.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("NO_ADMINISTRADOR")
public class UsuarioNoAdmin extends Usuario {
	private static final long serialVersionUID = 1L;
	public static enum Departamento {
		EMIES,
	    CCESP
	}
	public static enum Tipo{
		ALUMNO,
		DOCENTE,
		ADMINISTRACION
	}
	@NotNull(message="El campo departamento es obligatorio para NoAdmin.")
	private Departamento departamento;
	@NotNull(message="El campo Tipo es obligatorio para NoAdmin.")
	private Tipo tipo;
	

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	@Override
	public Role getRole() {
		return Role.NO_ADMINISTRADOR;
	}
	@Override
	public String toString() {
		return "UsuarioNoAdmin [getDepartamento()=" + getDepartamento() + ", getTipo()=" + getTipo() + ", getRole()="
				+ getRole() + "]";
	}


	

}
