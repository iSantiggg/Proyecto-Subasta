package com.example.subasta.model;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="logging")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	String nombre;
	
	String ap_paterno;
	
	String ap_materno;
	
}
