package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.predictmind.backend.PredictMindapp.models.dao.IHistoriaDao;
import com.predictmind.backend.PredictMindapp.models.entity.Historia;
import com.predictmind.backend.PredictMindapp.models.entity.Paciente;
import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;

@Service
public class HistoriaServiceImpl implements IHistoriaService{

	@Autowired
	private IHistoriaDao historiaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Historia> findAll() {
		return (List<Historia>) historiaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Historia> findByPaciente(Paciente paciente,Pageable pageable) {
		return historiaDao.findByPaciente(paciente, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Historia findById(Long id) {
		return historiaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Historia save(Historia historia) {
		return historiaDao.save(historia);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		historiaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreguntaHistoria> findAllPreguntas(Long id) {
		return historiaDao.findByHistoria(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Historia> findByHistoria(Long id) {
		return historiaDao.findByPaciente(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Historia> findByHistoriaOrden(Long id) {
		return historiaDao.findByPacienteOrden(id);
	}


}
