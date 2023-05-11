package es.mdef.gestionpreguntas.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionpreguntas.GestionpreguntasApplication;
import es.mdef.gestionpreguntas.entidades.Familia;
import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.entidades.Pregunta;
import es.mdef.gestionpreguntas.repositorios.FamiliaRepositorio;
import es.mdef.gestionpreguntas.repositorios.PreguntaRepositorio;

@RestController()
@RequestMapping("/familias")

public class FamiliaController {

	private final FamiliaRepositorio repositorio;
	private final PreguntaRepositorio preguntaRepositorio;
	private final FamiliaAssembler assembler;
	private final FamiliaListaAssembler listaAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler;
	private final UsuarioListaAssembler usuarioListaAssembler;
	private final Logger log;

	FamiliaController(FamiliaRepositorio repositorio, PreguntaRepositorio preguntaRepositorio,
			FamiliaAssembler assembler, PreguntaAssembler preguntaAssembler, FamiliaListaAssembler listaAssembler,
			PreguntaListaAssembler preguntaListaAssembler, UsuarioListaAssembler usuarioListaAssembler) {
		this.repositorio = repositorio;
		this.preguntaRepositorio = preguntaRepositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.preguntaListaAssembler = preguntaListaAssembler;
		this.usuarioListaAssembler = usuarioListaAssembler;
		this.log = GestionpreguntasApplication.log;
	}

	@GetMapping("{id}")
	public FamiliaModel one(@PathVariable Long id) {
		Familia familia = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Recuperado " + familia);
		return assembler.toModel(familia);
	}

	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntas(@PathVariable Long id) {
	
	//Esta es una posible opcion
//		return CollectionModel.of(
//				preguntaRepositorio.findByFamiliaId(id).stream()
//						.map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),
//				linkTo(methodOn(FamiliaController.class).one(id)).slash("preguntas").withSelfRel());
		
		//Esta opcion esta más clara
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "familia")).getPreguntas();
		
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),
				linkTo(methodOn(UsuarioController.class).one(id)).slash("preguntas").withSelfRel());
	}
	// NO SE SI ESTA CORRECTO, EN EL CROQUIS PONE <FamiliaListaModel> y no
	// <UsuarioListaModel>

	@GetMapping({ "{id}/usuarios" })
	public CollectionModel<UsuarioListaModel> usuarios(@PathVariable long id) {
		System.err.println("usuarios");
		List<Pregunta> preguntas = preguntaRepositorio.findByFamiliaId(id);
		Set<Usuario> usuarios = new HashSet<>();
		for (Pregunta pregunta : preguntas) {
			System.err.println(pregunta);
			usuarios.add(pregunta.getUsuario());
		}
		System.err.println(usuarios);
		return usuarioListaAssembler.toCollection(new ArrayList<>(usuarios));
	}

	@GetMapping
	public CollectionModel<FamiliaListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public FamiliaModel add(@RequestBody FamiliaPostModel model) {
		// creamos una entidad con el modelo para guardarla en el repositorio de
		// Familias.
		Familia nueva = assembler.toEntity(model);
		Familia otra = repositorio.save(nueva);
		log.info(otra + "Añadida correctamente.");
		;
		return assembler.toModel(otra);
	}

	@PutMapping("{id}")
	public FamiliaModel edit(@PathVariable Long id, @RequestBody Familia familia) {
		Familia nuevoFamilia = repositorio.findById(id).map(fam -> {
			fam.setEnunciado(familia.getEnunciado());

			return repositorio.save(fam);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Actualizado " + nuevoFamilia);
		
		return assembler.toModel(nuevoFamilia);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrada familia " + id);
		repositorio.deleteById(id);
	}

}