package es.mdef.gestionpreguntas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class UsuarioAdmin extends Usuario {
	private static final long serialVersionUID = 1L;
	@NotBlank(message="El campo telefono es obligatorio para Admin.")
	private String telefono;
	

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Override
	public Role getRole() {
		return Role.ADMINISTRADOR;
	}

	@Override
	public String toString() {
		return "UsuarioAdmin [telefono=" + telefono + ", getTelefono()=" + getTelefono() + ", getRole()=" + getRole()
				+ "]";
	}

	
	
	
	
}
