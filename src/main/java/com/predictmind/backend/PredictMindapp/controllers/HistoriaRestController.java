package com.predictmind.backend.PredictMindapp.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.predictmind.backend.PredictMindapp.models.entity.Historia;
import com.predictmind.backend.PredictMindapp.models.entity.Paciente;
import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;
import com.predictmind.backend.PredictMindapp.models.services.EmailService;
import com.predictmind.backend.PredictMindapp.models.services.IHistoriaService;
import com.predictmind.backend.PredictMindapp.models.services.IPacienteService;
import com.predictmind.backend.PredictMindapp.models.services.IPreguntaHistoriaService;

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/historia")
public class HistoriaRestController {
	
	Logger logger = LoggerFactory.getLogger(HistoriaRestController.class);

	@Autowired
	private IHistoriaService historiaService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private  IPreguntaHistoriaService preguntaHService;
	
	@Autowired
	private EmailService email;
	
	@GetMapping("/all")
	public List<Historia> index(){
		return historiaService.findAll();
	}
	
	@GetMapping("/{id}/page/{page}")
	public Page<Historia> index(@PathVariable Long id, @PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 5, Sort.by("fecha").descending());
		Paciente paciente = pacienteService.findById(id);
		return historiaService.findByPaciente(paciente, pageable);
	}
	
	@GetMapping("/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		Historia historia = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			historia = historiaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(historia == null) {
			response.put("mensaje", "La historia con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Historia>(historia, HttpStatus.OK);
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Historia historia, BindingResult result) {
		
		Historia historiaN = null;
		Map<String, Object> response = new HashMap<>();
		historia.setFecha(new Date());
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			historiaN = historiaService.save(historia);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La historia ha sido creado con exito!");
		response.put("historia", historiaN);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Historia historia, BindingResult result, @PathVariable Long id) {
		
		Historia historiaA = historiaService.findById(id);
		Historia historiaU = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(historiaA == null) {
			response.put("mensaje", "No se pudo editar, el cliente con el ID: "+id+" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			historiaA.setDescripcion(historia.getDescripcion());
			historiaA.setPrediccion(prediccion(id));
			historiaA.setArea(historia.getArea());
			historiaA.setPsicologo(historia.getPsicologo());
			historiaA.setPaciente(historia.getPaciente());
			
			historiaU = historiaService.save(historiaA);
			
			if(historiaU.getPrediccion().equals("Alto")) {
				historiaU.getPaciente().setPrioridad(true);
				updatePaciente(historiaU.getPaciente(), result, historiaU.getPaciente().getId());
				email.enviarEmail(historiaU.getPsicologo().getEmail(), "¡ATENCIÓN! "+historiaU.getPaciente().getNombre(), "El paciente "+historiaU.getPaciente().getNombre()+" acaba de responder la encuesta de "+historiaU.getArea()+" en la aplicación y puede requerir de su atención para mejorar su estado.\n\nGracias por su atención.\nPredictMind");
			} else {
				historiaU.getPaciente().setPrioridad(false);
				updatePaciente(historiaU.getPaciente(), result, historiaU.getPaciente().getId());
			}
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la historia en la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La historia ah sido actualizado con exito!");
		response.put("historia", historiaU);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		List<PreguntaHistoria> preguntaHistoria = findPreguntas(id);
		
		for (PreguntaHistoria preguntaHistoria2 : preguntaHistoria) {
			
			deletePregunta(preguntaHistoria2.getId());
		}
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Historia historia = historiaService.findById(id);
			
			historiaService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la historia de la base de datos");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La historia eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/preguntas")
	public List<PreguntaHistoria> findPreguntas(@PathVariable Long id){
		
		return historiaService.findAllPreguntas(id);
	}
	
	@GetMapping("/paciente/{id}")
	public List<Historia> findByPacientes(@PathVariable Long id){
		List<Historia> historias = historiaService.findByHistoria(id);
		//logger.info(id+' '+historias.get(0).getPaciente().getNombre());
		return historias;
	}
	
	@GetMapping("/pacienteOrden/{id}")
	public List<Historia> findByPacientesOrden(@PathVariable Long id){
		List<Historia> historias = historiaService.findByHistoriaOrden(id);
		int diferencia = historias.size()-8;
		if(diferencia>0) {
			for (int i=0;i<diferencia;i++) {
				
					historias.remove(0);
					
			}
		}
		return historias;
	}
	
	@GetMapping("/verificacion/{id}")
	public boolean verificacion(@PathVariable Long id) {
		
		List<Historia> historias = findByPacientes(id);
		boolean verificacion = true;
		
		for (Historia historia : historias) {
			if(historia.getFecha().getDate()==new Date().getDate() && historia.getFecha().getMonth()==new Date().getMonth() && historia.getFecha().getYear()==new Date().getYear()) {
				
				verificacion = false;
			}
		}
		return verificacion;
	}
	
	@DeleteMapping("/deletePregunta/{id}")
	public ResponseEntity<?> deletePregunta(@PathVariable Long id) {

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
	
	public String prediccion(Long id) {
		
		int contador=0;
		int prioridad=0;
		String prediccion;
		List<PreguntaHistoria> preguntas = findPreguntas(id);
		
		
		for (PreguntaHistoria preguntaHistoria : preguntas) {
			if(preguntaHistoria.getPregunta().isPrioridad() && preguntaHistoria.getRespuesta().equals("Si") && preguntaHistoria.getPregunta().isPrediccion()) {
				prioridad++;
			}
			if(preguntaHistoria.getRespuesta().equals("Si")) {
				contador++;
			}
			if(prioridad==3) {
				contador=contador+5;
			}
		}
		if (contador<=3) {
			prediccion = "Bajo";
		} else if (contador>3 && contador<8) {
			prediccion = "Medio";
		} else if(contador>=8 && contador<10) {
			prediccion = "Moderado";
		} else {
			prediccion = "Alto";
		}
		
		return prediccion;		
	}
	
public ResponseEntity<?> updatePaciente(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable Long id) {
		
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

}
