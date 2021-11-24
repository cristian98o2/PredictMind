package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.predictmind.backend.PredictMindapp.models.dao.IPacienteDao;
import com.predictmind.backend.PredictMindapp.models.entity.Paciente;

@Service
public class PacienteServiceImpl implements IPacienteService{
	
	@Autowired
	private IPacienteDao pacienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Paciente> findAll() {
		return (List<Paciente>) pacienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Paciente> findAll(Pageable pageable) {
		return pacienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Paciente findById(Long id) {
		return pacienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Paciente save(Paciente paciente) {
		return pacienteDao.save(paciente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pacienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Paciente> findPaciente(Long id) {
		return pacienteDao.findByPsicologo(id);
	}
	
	

}
