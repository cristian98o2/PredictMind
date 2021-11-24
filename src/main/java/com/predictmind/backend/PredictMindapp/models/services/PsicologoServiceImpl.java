package com.predictmind.backend.PredictMindapp.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.predictmind.backend.PredictMindapp.models.dao.IPsicologoDao;
import com.predictmind.backend.PredictMindapp.models.entity.Psicologo;

@Service
public class PsicologoServiceImpl implements IPsicologoService {
	
	@Autowired
	private IPsicologoDao psicologoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Psicologo> findAll() {
		return (List<Psicologo>) psicologoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Psicologo> findAll(Pageable pageable) {
		return psicologoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Psicologo findById(Long id) {
		return psicologoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Psicologo save(Psicologo psicologo) {
		return psicologoDao.save(psicologo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		psicologoDao.deleteById(id);
	}

}
