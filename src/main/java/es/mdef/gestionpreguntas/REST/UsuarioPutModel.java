package es.mdef.gestionpreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.mdef.gestionpreguntas.entidades.Usuario.Role;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin.Departamento;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin.Tipo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;



@Relation(itemRelation = "usuario")
public class UsuarioPutModel extends RepresentationModel<UsuarioPutModel> {

	private String nombre;
//	private String username;
	private Role role;
	
//	//securizacion
//	private static final long serialVersionUID = 1L;
//	@NotBlank(message="username es un campo obligatorio de la clase empleado")
//	@Column(unique=true, name = "nombre_usuario")
	private String username;
	
	//quitamos la contraseña que no aparece en el enunciado
//	@NotBlank(message="contraseña es un campo obligatorio de la clase empleado")
//	@Column(name="contrasena")
//	private String password;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	// Lo del administrador
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	// Lo del no administrador

	private Departamento departamento;
	private Tipo tipo;

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	// Fin del añadido de la herencia

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


//	//securizacion

		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		public void setAccountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
		}

		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		public void setAccountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
		}

		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public void setCredentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	
	@Override
	public String toString() {
		return "UsuarioPutModel [getNombre()=" + getNombre() + ", getUsername()=" + getUsername() + ", getRole()="
				+ getRole() + "]";
	}

}
