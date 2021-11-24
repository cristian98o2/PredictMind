package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.predictmind.backend.PredictMindapp.models.entity.Paciente;

public interface IPacienteService {
	
	public List<Paciente> findAll();
	
	public Page<Paciente> findAll(Pageable pageable);
	
	public Paciente findById(Long id);
	
	public Paciente save(Paciente paciente);
	
	public void delete(Long id);
	
	public List<Paciente> findPaciente(Long id);
}
