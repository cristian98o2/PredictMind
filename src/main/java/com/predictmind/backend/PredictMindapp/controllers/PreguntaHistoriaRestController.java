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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;
import com.predictmind.backend.PredictMindapp.models.entity.Psicologo;
import com.predictmind.backend.PredictMindapp.models.services.IPreguntaHistoriaService;

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/preguntah")
public class PreguntaHistoriaRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PsicologoRestController.class);
	
	@Autowired
	private IPreguntaHistoriaService preguntaHService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody PreguntaHistoria preguntaHistoria, BindingResult result) {
		
		PreguntaHistoria preguntaHistoriaN = null;
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
			preguntaHistoriaN = preguntaHService.save(preguntaHistoria);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El registro ha sido creado con exito!");
		response.put("cliente", preguntaHistoriaN);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		
		try {
			
			PreguntaHistoria preguntaHistoria= preguntaHService.findById(id);
			
			preguntaHService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro de la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El registro fue eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
