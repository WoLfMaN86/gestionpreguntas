package es.mdef.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.entidades.Pregunta;
import es.mdef.gestionpreguntas.entidades.UsuarioAdmin;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin;
import es.mdef.gestionpreguntas.GestionpreguntasApplication;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

	public final Logger log;

	public UsuarioAssembler() {
		log = GestionpreguntasApplication.log;
	}
	@Override
	public UsuarioModel toModel(Usuario entity) {
		entity.setPassword(null); //esto lo añadimos en la securizacion
		UsuarioModel model = new UsuarioModel();
		
		switch (entity.getRole()) {
		case ADMINISTRADOR:
			model.setTelefono(((UsuarioAdmin) entity).getTelefono());
			break;
		case NO_ADMINISTRADOR:
			model.setDepartamento(((UsuarioNoAdmin) entity).getDepartamento());
			model.setTipo(((UsuarioNoAdmin) entity).getTipo());
			break;
		default:
			model = new UsuarioModel();

		}
		model.setNombre(entity.getNombre());

		model.setUsername(entity.getUsername());
		model.setRole(entity.getRole());
        model.setAccountNonExpired(entity.isAccountNonExpired());
        model.setAccountNonLocked(entity.isAccountNonLocked());
        model.setCredentialsNonExpired(entity.isCredentialsNonExpired());
        model.setEnabled(entity.isEnabled());

		model.add(linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).preguntas(entity.getId())).withRel("preguntas"),
				linkTo(methodOn(UsuarioController.class).familias(entity.getId())).withRel("familias"));
		return model;
	}

	public Usuario toEntity(UsuarioPostModel model) {
		Usuario usuario = new Usuario();
		switch (model.getRole()) {
		case ADMINISTRADOR:
			UsuarioAdmin admin = new UsuarioAdmin();
			admin.setTelefono(model.getTelefono());
			usuario = admin;
			break;
		case NO_ADMINISTRADOR:
			UsuarioNoAdmin noAdmin = new UsuarioNoAdmin();
			noAdmin.setDepartamento(model.getDepartamento());
			noAdmin.setTipo(model.getTipo());
			usuario = noAdmin;
			break;
		default:
			usuario = new Usuario();

		}
		usuario.setNombre(model.getNombre());
		usuario.setUsername(model.getUsername());
		usuario.setPassword(new BCryptPasswordEncoder().encode(model.getPassword())); //Añadido en la securizacion
//		System.out.println(((UsuarioNoAdmin)usuario).getDepartamento());
		return usuario;
	}

	// sobrecarga del método
//	public Usuario toEntity(UsuarioPutModel model) {
//		Usuario usuario;
//		
//		switch (model.getRole()) {
//		case ADMINISTRADOR:
//			UsuarioAdmin admin = new UsuarioAdmin();
//			admin.setTelefono(model.getTelefono());
//			usuario = admin;
//			break;
//		case NO_ADMINISTRADOR: 
//			UsuarioNoAdmin noAdmin = new UsuarioNoAdmin();
//			noAdmin.setTipo(model.getTipo());
//			noAdmin.setDepartamento(model.getDepartamento());
//			usuario = noAdmin;
//			break;				
//		default:
//			usuario = new Usuario();
//		}
//		
//		usuario.setNombre(model.getNombre());
//		usuario.setUsername(model.getUsername());
//		return usuario;
//	}

}
