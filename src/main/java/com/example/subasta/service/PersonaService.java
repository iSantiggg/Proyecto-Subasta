package com.example.subasta.service;

import java.util.List;

import com.example.subasta.model.Persona;

public interface PersonaService {
	
	public List<Persona>listausuarios();
	
	public Persona save(Persona persona);
	
	public Persona findById(Long id);
	
	public boolean delete(Long id);
	
	public Persona edit(Persona persona);

}
