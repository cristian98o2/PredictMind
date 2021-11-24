package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;

public interface IPreguntaHistoriaService {
	
	public List<PreguntaHistoria> findAll();
	
	public Page<PreguntaHistoria> findAll(Pageable pageable);
	
	public PreguntaHistoria findById(Long id);
	
	public  PreguntaHistoria save(PreguntaHistoria preguntaHistoria);
	
	public void delete(Long id);
}
