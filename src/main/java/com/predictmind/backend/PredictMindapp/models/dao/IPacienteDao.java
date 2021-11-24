package com.predictmind.backend.PredictMindapp.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.predictmind.backend.PredictMindapp.models.entity.Paciente;

public interface IPacienteDao extends JpaRepository<Paciente, Long>{
	
	@Query("SELECT p FROM Paciente p left join fetch p.psicologo s WHERE s.id=?1")
	public List<Paciente> findByPsicologo(Long psicologo);
	
	
}
