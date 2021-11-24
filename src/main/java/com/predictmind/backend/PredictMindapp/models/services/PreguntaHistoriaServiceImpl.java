package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.predictmind.backend.PredictMindapp.models.dao.IPreguntaHistoria;
import com.predictmind.backend.PredictMindapp.models.entity.PreguntaHistoria;

@Service
public class PreguntaHistoriaServiceImpl implements IPreguntaHistoriaService  {
	
	@Autowired
	private IPreguntaHistoria preguntaHistoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PreguntaHistoria> findAll() {
		return (List<PreguntaHistoria>) preguntaHistoriaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PreguntaHistoria> findAll(Pageable pageable) {
		return preguntaHistoriaDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public PreguntaHistoria findById(Long id) {
		return preguntaHistoriaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PreguntaHistoria save(PreguntaHistoria psicologo) {
		return preguntaHistoriaDao.save(psicologo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		preguntaHistoriaDao.deleteById(id);
	}
}
