package es.mdef.gestionpreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.entidades.UsuarioAdmin;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin;

@Component
public class UsuarioPutAssembler implements RepresentationModelAssembler<Usuario, UsuarioPutModel> {

	public UsuarioPutModel toModel(Usuario entity) {
		UsuarioPutModel model = new UsuarioPutModel();
		model.setNombre(entity.getNombre());
		model.setUsername(entity.getUsername());
		
		model.setAccountNonExpired(entity.isAccountNonExpired());
		model.setAccountNonLocked(entity.isAccountNonLocked());
		model.setCredentialsNonExpired(entity.isCredentialsNonExpired());
		model.setEnabled(entity.isEnabled());
		
		model.setRole(entity.getRole());
		model.add(linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel());
		return model;
	}

	public Usuario toEntity(UsuarioPutModel model) {
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

		return usuario;
	}

}