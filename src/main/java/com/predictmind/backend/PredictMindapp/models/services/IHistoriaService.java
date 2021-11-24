package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.predictmind.backend.PredictMindapp.models.entity.Historia;
import com.predictmind.backend.PredictMindapp.models.entity.Paciente;
import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;

public interface IHistoriaService {
	
	public List<Historia> findAll();
	
	public Page<Historia> findByPaciente(Paciente paciente, Pageable pageable);
	
	public Historia findById(Long id);
	
	public Historia save(Historia historia);
	
	public void delete(Long id);
	
	public List<PreguntaHistoria> findAllPreguntas(Long id);
	
	public List<Historia> findByHistoria(Long id);
	
	public List<Historia> findByHistoriaOrden(Long id);
	
}
