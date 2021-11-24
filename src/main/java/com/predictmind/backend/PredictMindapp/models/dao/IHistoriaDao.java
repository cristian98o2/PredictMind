package com.predictmind.backend.PredictMindapp.models.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.predictmind.backend.PredictMindapp.models.entity.Historia;
import com.predictmind.backend.PredictMindapp.models.entity.Paciente;
import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;


public interface IHistoriaDao extends JpaRepository<Historia, Long>{
	
	public Page<Historia> findByPaciente(Paciente paciente, Pageable pageable);
	
	@Query("SELECT p FROM PreguntaHistoria p left join fetch p.historia h WHERE h.id =?1")
	public List<PreguntaHistoria> findByHistoria(Long historia);
	
	@Query("SELECT h FROM Historia h left join fetch h.paciente p WHERE p.id =?1 ORDER BY h.fecha desc")
	public List<Historia> findByPaciente(Long paciente);
	
	@Query("SELECT h FROM Historia h left join fetch h.paciente p WHERE p.id =?1 ORDER BY h.fecha asc")
	public List<Historia> findByPacienteOrden(Long paciente);
}
