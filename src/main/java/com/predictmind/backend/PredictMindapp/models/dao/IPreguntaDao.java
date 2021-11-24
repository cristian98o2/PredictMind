package com.predictmind.backend.PredictMindapp.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.predictmind.backend.PredictMindapp.models.entity.Pregunta;

public interface IPreguntaDao extends JpaRepository<Pregunta, Long>{
	
	public List<Pregunta> findByArea(String area);

}
