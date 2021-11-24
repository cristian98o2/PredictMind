package com.predictmind.backend.PredictMindapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/paciente")
public class PacienteRestController {
	
	@Autowired
	private IPacienteService pacienteService;
	
	@GetMapping("/pacientes")
	public List<Paciente> index(){
		List<Paciente> paciente = pacienteService.findAll();
		return paciente;
	}
	
	@GetMapping("/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		Paciente paciente = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			paciente = pacienteService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(paciente == null) {
			response.put("mensaje", "El paciente con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Paciente paciente, BindingResult result) {
		
		Paciente pacienteN = null;
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
			pacienteN = pacienteService.save(paciente);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con exito!");
		response.put("cliente", pacienteN);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable Long id) {
		
		Paciente pacienteA = pacienteService.findById(id);
		Paciente pacienteU = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(pacienteA == null) {
			response.put("mensaje", "No se pudo editar, el paciente con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			pacienteA.setNombre(paciente.getNombre());
			pacienteA.setIdentificacion(paciente.getIdentificacion());
			pacienteA.setEmail(paciente.getEmail());
			pacienteA.setFechaNacimiento(paciente.getFechaNacimiento());
			pacienteA.setFechaIngreso(paciente.getFechaIngreso());
			pacienteA.setDireccion(paciente.getDireccion());
			pacienteA.setNumero(paciente.getNumero());
			pacienteA.setDescripcion(paciente.getDescripcion());
			pacienteA.setAnsiedad(paciente.isAnsiedad());
			pacienteA.setDepresion(paciente.isDepresion());
			pacienteA.setPsicologo(paciente.getPsicologo());
			pacienteA.setPrograma(paciente.getPrograma());
			pacienteA.setFacultad(paciente.getFacultad());
			pacienteA.setSemestre(paciente.getSemestre());
			pacienteA.setEdad(paciente.getEdad());
			pacienteA.setCiudad(paciente.getCiudad());
			pacienteA.setDiagnostico(paciente.isDiagnostico());
			pacienteA.setPrioridad(paciente.isPrioridad());
			
			pacienteU = pacienteService.save(pacienteA);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el paciente en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ah sido actualizado con exito!");
		response.put("paciente", pacienteU);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Paciente paciente = pacienteService.findById(id);
			
			pacienteService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el paciente de la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El paciente eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/psicologo/{id}")
	public List<Paciente> showById(@PathVariable Long id){
		
		List<Paciente> paciente = null; 
		paciente = pacienteService.findPaciente(id);
		return paciente;
	}
}
