package es.mdef.gestionpreguntas.REST;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionpreguntas.GestionpreguntasApplication;
import es.mdef.gestionpreguntas.entidades.Pregunta;
import es.mdef.gestionpreguntas.entidades.Usuario;
import es.mdef.gestionpreguntas.repositorios.PreguntaRepositorio;

@RestController
@RequestMapping("/preguntas")
public class PreguntaController {
	private final PreguntaRepositorio repositorio;
	private final PreguntaAssembler assembler;
	private final PreguntaListaAssembler listaAssembler;
	private final PreguntaPostAssembler postAssembler;
//	private final UsuarioListaAssembler usuarioListaAssembler;
	private final Logger log;

	public PreguntaController(PreguntaRepositorio repositorio, PreguntaAssembler assembler,
			PreguntaListaAssembler listaAssembler, PreguntaPostAssembler postAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.postAssembler = postAssembler;
//		this.usuarioListaAssembler = usuarioListaAssembler; Lo quitamos porque esto va en el uno de la relación uno a muchos
		this.log = GestionpreguntasApplication.log;
	}

	@PostMapping
	public PreguntaModel add(@RequestBody PreguntaPostModel model) {
		Pregunta pregunta = repositorio.save(postAssembler.toEntity(model));
		// La linea superior se puede ampliar en dos lineas de la siguiente manera
		// Pregunta preguntaNew = postAssembler.toEntity(model);
		// Pregunta pregunta=repositorio.save(preguntaNew);

		log.info("Añadido " + pregunta);
		System.err.println(pregunta.getUsuario().getId());
		return assembler.toModel(pregunta);
	}

	@GetMapping("{id}")
	public PreguntaModel one(@PathVariable Long id) { // Antes era EntityModel<Pregunta>
		Pregunta pregunta = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
		log.info("Recuperado " + pregunta);
		return assembler.toModel(pregunta);
	}

	@GetMapping
	public CollectionModel<PreguntaListaModel> all() {
		List<Pregunta> lista = repositorio.findAll();
		return listaAssembler.toCollection(lista);
	}

//	@GetMapping("{id}/usuarios")
//	public CollectionModel<UsuarioListaModel> usuarios(@PathVariable Long id) {
//		List<Usuario> usuarios = repositorio.findById(id)
//				.orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"))
//				.getUsuarios();
//		return CollectionModel.of(
//				usuarios.stream().map(usuario -> usuarioListaAssembler.toModel(usuario)).collect(Collectors.toList()),
//				linkTo(methodOn(PreguntaController.class).one(id)).slash("usuarios").withSelfRel()
//				);
//	} Lo quitamos porque va en el uno de las relaciones uno a muchos

	@PutMapping("{id}")
	public PreguntaModel edit(@PathVariable Long id, @RequestBody PreguntaModel model) { // Antes EntityModel<Pregunta>
		Pregunta pregunta = repositorio.findById(id).map(art -> {
			art.setEnunciado(model.getEnunciado());

			return repositorio.save(art);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "artículo"));
		log.info("Actualizado " + pregunta);
		return assembler.toModel(pregunta);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado artículo " + id);
		repositorio.deleteById(id);
	}

}
