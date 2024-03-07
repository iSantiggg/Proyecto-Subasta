package com.example.subasta.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.subasta.model.User;

public class UserInfoDetails implements UserDetails{
	//Atributos para los detalles del usuario
	private String email;
	private String password;
	private List<GrantedAuthority> roles;
	//Constructor unicamente que trae los parametros del usuario
	//Encapsula los detalles del usuario junto con Spring Security para la autenticación y autorización para que UserDetails de acceso
	//a la información necesaria para realizar estas tareas.
	public UserInfoDetails(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = Arrays.stream(user.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	//Regresa el rol del usuario "ADMIN" o "USER"
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}
	//Regresa la contraseña proporcionado
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	//Regresa el email proporcionado
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	//Indica si la cuenta del usuario ha expirado o no
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	//Indica si la cuenta del usuario está bloqueada o no
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	//Indica si las credenciales del usuario han caducado o no
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	//Indica si la cuenta del usuario está habilitado o no
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
