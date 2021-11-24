package com.predictmind.backend.PredictMindapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.predictmind.backend.PredictMindapp.models.entity.Paciente;
import com.predictmind.backend.PredictMindapp.models.entity.Psicologo;
import com.predictmind.backend.PredictMindapp.models.services.IPacienteService;
import com.predictmind.backend.PredictMindapp.models.services.IPsicologoService;

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/psicologo")
public class PsicologoRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PsicologoRestController.class);
	
	@Autowired
	private IPsicologoService psicologoService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@GetMapping("/psicologos")
	public List<Psicologo> index(){
		List<Psicologo> psicologo = psicologoService.findAll();
		return psicologo;
	}
	
	@GetMapping("/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		Psicologo psicologo= null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			psicologo = psicologoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(psicologo == null) {
			response.put("mensaje", "El psicologo con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Psicologo>(psicologo, HttpStatus.OK);
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Psicologo psicologo, BindingResult result) {
		
		Psicologo psicologoN = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
//			List<String> errors = new ArrayList<>();
//			
//			for(FieldError err: result.getFieldErrors()) {
//				errors.add("El campo '"+err.getField()+"' "+err.getDefaultMessage());
//			}
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			psicologoN = psicologoService.save(psicologo);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El psicologo ha sido creado con exito!");
		response.put("cliente", psicologoN);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Psicologo psicologo, BindingResult result, @PathVariable Long id) {
		
		Psicologo psicologoA = psicologoService.findById(id);
		Psicologo psicologoU = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(psicologoA == null) {
			response.put("mensaje", "No se pudo editar, el psicologo con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			psicologoA.setNombre(psicologo.getNombre());
			psicologoA.setIdentificacion(psicologo.getIdentificacion());
			psicologoA.setEmail(psicologo.getEmail());
			
			psicologoU = psicologoService.save(psicologoA);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el psicologo en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El psicologo ah sido actualizado con exito!");
		response.put("psicologo", psicologoU);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Psicologo psicologo = psicologoService.findById(id);
			
			psicologoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el psicologo de la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El psicologo eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/pacientes/{id}")
	public List<Paciente> findPacientes(@PathVariable Long id){
		
		return pacienteService.findPaciente(id);
		
	}
}
