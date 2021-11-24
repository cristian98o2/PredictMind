package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.predictmind.backend.PredictMindapp.models.entity.Pregunta;

public interface IPreguntaService {
	
	public List<Pregunta> findAll();
	
	public Page<Pregunta> findAll(Pageable pageable);
	
	public Pregunta findById(Long id);
	
	public Pregunta save(Pregunta pregunta);
	
	public void delete(Long id);
	
	public List<Pregunta> findByArea(String area);
}
