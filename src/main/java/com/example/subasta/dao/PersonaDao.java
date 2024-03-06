package com.example.subasta.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.subasta.model.Persona;

public interface PersonaDao extends JpaRepository<Persona, Long> {

	
}
