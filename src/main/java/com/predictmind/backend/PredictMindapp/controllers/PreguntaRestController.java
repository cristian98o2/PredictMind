package com.predictmind.backend.PredictMindapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.predictmind.backend.PredictMindapp.models.entity.Pregunta;
import com.predictmind.backend.PredictMindapp.models.services.IPreguntaService;

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/pregunta")
public class PreguntaRestController {
	
	@Autowired
	private IPreguntaService preguntaService;
	
	@GetMapping("/all")
	public List<Pregunta> index(){
		return preguntaService.findAll();
	}
	
	@GetMapping("/page/{page}")
	public Page<Pregunta> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 1);
		return preguntaService.findAll(pageable);
	}
	
	@GetMapping("/area/{area}")
	public List<Pregunta> findByArea(@PathVariable String area){
		return preguntaService.findByArea(area);
	}
	
	@GetMapping("/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		Pregunta pregunta = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			pregunta = preguntaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(pregunta == null) {
			response.put("mensaje", "El pregunta con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pregunta>(pregunta, HttpStatus.OK);
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Pregunta pregunta, BindingResult result) {
		
		Pregunta preguntaN = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			preguntaN = preguntaService.save(pregunta);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La pregunta ha sido creado con exito!");
		response.put("pregunta", preguntaN);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Pregunta pregunta, BindingResult result, @PathVariable Long id) {
		
		Pregunta preguntaA = preguntaService.findById(id);
		Pregunta preguntaU = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(preguntaA == null) {
			response.put("mensaje", "No se pudo editar, el cliente con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			preguntaA.setPregunta(pregunta.getPregunta());
			preguntaA.setArea(pregunta.getArea());
			preguntaA.setPrediccion(pregunta.isPrediccion());
			preguntaA.setPrioridad(false);
			
			preguntaU = preguntaService.save(preguntaA);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la pregunta en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La pregunta ah sido actualizado con exito!");
		response.put("pregunta", preguntaU);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Pregunta cliente = preguntaService.findById(id);
						
			preguntaService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la pregunta de la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La pregunta eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
