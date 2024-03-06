package com.example.subasta.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.subasta.dao.PersonaDao;
import com.example.subasta.model.Persona;
import com.example.subasta.service.PersonaService;


@Service
public class PersonaServiceImpl implements PersonaService{
	
	@Autowired
	private PersonaDao personaDao;

	@Override
	public List<Persona> listausuarios() {
		return (List<Persona>) personaDao.findAll();
	}

	@Override
	public Persona save(Persona persona) {
		return personaDao.save(persona);
	}

	@Override
	public Persona findById(Long id) {
		return personaDao.findById(id).orElse(null);
	}

	@Override
	public boolean delete(Long id) {
		Persona persona = findById(id);
		personaDao.deleteById(persona.getId());
		return true;
	}

	@Override
	public Persona edit(Persona persona) {
		Persona editPerso = findById(persona.getId());
		editPerso.setNombre(persona.getNombre());
		editPerso.setAp_materno(persona.getAp_paterno());
		editPerso.setAp_paterno(persona.getAp_paterno());
		save(editPerso);
		return editPerso;
	}
	
	

}
