package es.mdef.gestionpreguntas.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List; //Para la relacion uno a muchos. 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; //Para la relacion uno a muchos.
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "USUARIOS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;
	public static enum Role {
		ADMINISTRADOR, NO_ADMINISTRADOR
	}

// RF1 Un usuario debe contener como mínimo los campos nombre, nombre de usuario y contraseña.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String nombre;

	
	@OneToMany(mappedBy = "usuario")
	List<Pregunta> preguntas;
	//securizacion
	

	private String username;
	
    @NotBlank(message="La contraseña/password es obligatoria.")
    private String password;
    
	@Column(name="cuenta_activa")
	private boolean accountNonExpired = true;
	
	@Column(name="cuenta_desbloqueada")
	private boolean accountNonLocked = true;
	
	@Column(name="credenciales_activas")
	private boolean credentialsNonExpired = true;
	
	@Column(name="habilitada")
	private boolean enabled = true;
	
//	private Role role = Role.NO_ADMINISTRADOR; 
//	private Role role;
//	private Departamento departamento;
//	private Grupo grupo;
	public String getNombre() {
		return nombre;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
	}
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}


//securizacion

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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Usuario [getNombre()=" + getNombre() + ", getUsername()=" + getUsername() + ", getContrasena()="
				+ getPassword() + ", getId()=" + getId() + ", getRole()=" + getRole() + ", getPreguntas()="
				+ getPreguntas() + "]";
	}
	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<SimpleGrantedAuthority>(
				Arrays.asList(new SimpleGrantedAuthority(getRole().toString()))
				);
	}
}
