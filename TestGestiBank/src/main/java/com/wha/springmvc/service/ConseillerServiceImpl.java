package com.wha.springmvc.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wha.springmvc.dao.ConseillerDao;
import com.wha.springmvc.model.Conseiller;
@Service("conseillerService")
@Transactional
public class ConseillerServiceImpl implements ConseillerService {
	
	
	@Autowired
	private ConseillerDao dao;

	@Override
	public Conseiller findById(long id) {
		return dao.findById((int)id);
	}

	@Override
	public Conseiller findByName(String name) {
		return dao.findByName((String) name);
	}

	@Override
	public void saveConseiller(Conseiller conseiller) {
		dao.save(conseiller);
	}

	@Override
	public void updateConseiller(Conseiller conseiller) {
		Conseiller entity = dao.findById((int)conseiller.getId());
		if(entity!=null){
			entity.setUsername(conseiller.getUsername());
			entity.setAddress(conseiller.getAddress());
			entity.setEmail(conseiller.getEmail());
			
		}
		dao.save(entity);

	}

	@Override
	public void deleteConseillerById(long id) {
		dao.deleteConseillerById((int) id);

	}

		



	@Override
	public void deleteAllConseillers() {
		dao.deleteAllConseillers();

	}

	@Override
	public boolean isConseillerExist(Conseiller conseiller) {
		return dao.findByName((String) conseiller.getUsername())!=null;
	}

	@Override
	public Collection<Conseiller> findAllConseillers() {
		
		return dao.findAllConseillers();
	}

	

}
