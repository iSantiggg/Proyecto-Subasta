package com.example.subasta.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.subasta.dao.UserRepository;
import com.example.subasta.model.Persona;
import com.example.subasta.model.User;
import com.example.subasta.service.PersonaService;

@RestController
@RequestMapping
public class PersonaController {
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	///////////////////////// Aqui estarán las solicitudes para la autenticación /////////////////////////
	@GetMapping("/")
	public String goHome() {
		return "Esta página es para todo el público, si necesitas ingresar a otra página, necesitarás autenticación";
	}
	
	@GetMapping("/usuarios/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> getAllUsers(){
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@GetMapping("/usuario/detalles")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Object> getMyDetails(){
		return ResponseEntity.ok(userRepository.findByEmail(getLoggedInUserDetails().getUsername()));
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<Object> saveUser(@RequestBody User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result = userRepository.save(user);
		if(result.getId() > 0) {
			return ResponseEntity.ok("Usuario guardado");
		}
		return ResponseEntity.status(404).body("Usuario no guardado");
	}
	
	public UserDetails getLoggedInUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return (UserDetails) authentication.getPrincipal();
		}
		return null;
	}
	
	///////////////////////// Aqui estarán las solicitudes para los productos de la subasta /////////////////////////
	@GetMapping("/lista")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<List<Persona>>lista(){
		List<Persona> person = new ArrayList<>();
		
		try {
			person = personaService.listausuarios();
			if(person != null) {
				return new ResponseEntity<>(person,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/agregar")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Persona>agregar(@RequestBody Persona logging){
		Persona persoSave;
		try {
			persoSave = personaService.save(logging);
			if(persoSave != null) {
				return new ResponseEntity<>(persoSave,HttpStatus.CREATED);
			}
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
		return null;
	}
	
	@DeleteMapping("/eliminar/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Boolean>eliminar(@PathVariable Long id){
		try {
			personaService.delete(id);
			return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
		}catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping("/editar/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Persona> editar(@PathVariable Long id, @RequestBody Persona persona) {
		
	    persona.setId(id); // Asigna el id al objeto Logging antes de editarlo
	    
	    Persona editado = personaService.edit(persona);
	    
	    try {
	        if (editado != null) {
	            return new ResponseEntity<>(editado, HttpStatus.ACCEPTED);
	        } else {
	            return new ResponseEntity<>(editado, HttpStatus.NOT_ACCEPTABLE);
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
