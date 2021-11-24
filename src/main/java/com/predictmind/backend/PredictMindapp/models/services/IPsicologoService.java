package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.predictmind.backend.PredictMindapp.models.entity.Psicologo;

public interface IPsicologoService {
	
	public List<Psicologo> findAll();
	
	public Page<Psicologo> findAll(Pageable pageable);
	
	public Psicologo findById(Long id);
	
	public  Psicologo save(Psicologo psicologo);
	
	public void delete(Long id);
}
