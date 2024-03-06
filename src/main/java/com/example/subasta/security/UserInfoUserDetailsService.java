package com.example.subasta.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.subasta.dao.UserRepository;
import com.example.subasta.model.User;

@Configuration
public class UserInfoUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepo.findByEmail(username);
		return user.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
	}
}
