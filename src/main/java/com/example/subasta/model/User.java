package com.example.subasta.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class User {
	
	//Aqui estan las variables del modelo para los usuarios registrados que quieran entrar a la pagina.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String roles;
	
	
}
