package es.mdef.gestionpreguntas.REST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionpreguntas.GestionpreguntasApplication;
import es.mdef.gestionpreguntas.entidades.Pregunta;
import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.entidades.Usuario.Role;
import es.mdef.gestionpreguntas.repositorios.PreguntaRepositorio;
import es.mdef.gestionpreguntas.repositorios.UsuarioRepositorio;
import jakarta.validation.Valid;
import es.mdef.gestionpreguntas.repositorios.FamiliaRepositorio;
import es.mdef.gestionpreguntas.entidades.Familia;

import es.mdef.gestionpreguntas.entidades.UsuarioAdmin;
import es.mdef.gestionpreguntas.entidades.UsuarioNoAdmin;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final PreguntaRepositorio repositorioPreguntas;
	private final UsuarioAssembler assembler;
	private final UsuarioPutAssembler assemblerPut;
	private final FamiliaListaAssembler familiaListaAssembler;
	private final UsuarioListaAssembler listaAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler; // esto se lo añadimos para que cuadre
	private final Logger log;

	public UsuarioController(UsuarioRepositorio repositorio, PreguntaRepositorio repositorioPreguntas,
			UsuarioAssembler assembler, UsuarioPutAssembler assemblerPut, UsuarioListaAssembler listaAssembler,
			PreguntaListaAssembler preguntaListaAssembler, FamiliaListaAssembler familiaListaAssembler) {

		this.repositorio = repositorio;
		this.assembler = assembler;
		this.assemblerPut = assemblerPut;
		this.repositorioPreguntas = repositorioPreguntas;

		this.listaAssembler = listaAssembler;
		this.preguntaListaAssembler = preguntaListaAssembler;
		this.log = GestionpreguntasApplication.log;
		this.familiaListaAssembler = familiaListaAssembler;
	}

	@GetMapping("/{id}")
	public UsuarioModel one(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}

	@GetMapping
	public CollectionModel<UsuarioListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}
	// El metodo anterior se podia haber puesto de esta otra manera.
//	@GetMapping
//	public CollectionModel<ArticuloListaModel> all () {
//		List<Articulo> lista = repositorio.findAll();
//		return listaAssembler.toCollection(lista);
//	}
	@GetMapping("porUsername")
	public CollectionModel<UsuarioListaModel> usuariosPorUsername(@RequestParam String username) {
		return listaAssembler.toCollection(
				repositorio.findUserByUsername(username)
				);
	}
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntas(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario")).getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta))
						.collect(Collectors.toList()),
				linkTo(methodOn(UsuarioController.class).one(id)).slash("preguntas").withSelfRel());
	}

	@GetMapping("{id}/familias")
	public CollectionModel<FamiliaListaModel> familias(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorioPreguntas.findPreguntaByUsuarioId(id);
		Set<Familia> familias = new HashSet<>();
		for (Pregunta pregunta : preguntas) {
			familias.add(pregunta.getFamilia());
		}
		return familiaListaAssembler.toCollection(new ArrayList<>(familias));
	}
// No se puede usar porque el repo es Optional y no List.
//	@GetMapping("porUsername")
//	public CollectionModel<UsuarioListaModel> UsuariosByUsername(@RequestParam String username) {
//		return listaAssembler.toCollection(repositorio.findByUsername(username));
//	}

	@PostMapping
	public UsuarioModel add(@Valid @RequestBody UsuarioPostModel model) {
//		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		//Lo hacemos paso a paso
		Usuario nuevo=assembler.toEntity(model);
		Usuario usuario=repositorio.save(nuevo);
		
		
//		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword())); //Securizacion
//		Usuario usuario = repositorio.save(assembler.toEntity(model));
		// Lo anterior podria separarse en dos lineas para una mayor claridad
		// Usuario nuevo =assembler.toEntity(model);
		// Usuario usuario=repositorio.save(nuevo);
		log.info("Añadido " + usuario);
		return assembler.toModel(usuario);
	}

	@PutMapping("{id}")
	public UsuarioModel edit(@PathVariable Long id, @Valid @RequestBody UsuarioPutModel model) {
		Usuario usuario = repositorio.findById(id).map(user -> {
			user.setNombre(model.getNombre());
			user.setUsername(model.getUsername());
			user.setAccountNonExpired(model.isAccountNonExpired());
			user.setAccountNonLocked(model.isAccountNonLocked());
			user.setCredentialsNonExpired(model.isCredentialsNonExpired());
			user.setEnabled(model.isEnabled());
			//securizacion -> NO SE SI ESTO SE METE O NO. 
//			user.setAccountNonExpired(model.isAccountNonExpired());
//			user.setAccountNonLocked(model.isAccountNonLocked());
//			user.setCredentialsNonExpired(model.isCredentialsNonExpired());
//			user.setEnabled(model.isEnabled());

			if (user.getRole() == Role.ADMINISTRADOR) {
				UsuarioAdmin admin = (UsuarioAdmin) user;
				admin.setTelefono(model.getTelefono());
			} else if (user.getRole() == Role.NO_ADMINISTRADOR) {
				UsuarioNoAdmin noAdmin = (UsuarioNoAdmin) user;
				noAdmin.setDepartamento(model.getDepartamento());
				noAdmin.setTipo(model.getTipo());

			}
			return repositorio.save(user);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}
	
	

	@PatchMapping("{id}/cambiarContraseña")
	public UsuarioModel edit(@PathVariable Long id, @RequestBody String contrasenaNueva) {
		Usuario usuario = repositorio.findById(id).map(user -> {
		user.setPassword(new BCryptPasswordEncoder().encode(contrasenaNueva));
			

			return repositorio.save(user);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado usuario " + id);
		repositorio.deleteById(id);
	}
}
