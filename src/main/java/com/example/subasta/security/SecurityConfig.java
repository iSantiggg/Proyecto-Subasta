package com.example.subasta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	//Este metodo es el principal para la configuracion de filtros de la seguridad en el programa
	//Se da acceso a ciertas URL sin necesidad de autenticación, en las demás se necesita.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/", "/usuario/guardado").permitAll()
				.anyRequest().authenticated())
				.httpBasic(withDefaults())
				.formLogin(withDefaults())
				.csrf(AbstractHttpConfigurer::disable);
		return http.build();
		
	}
		//Se declara este metodo para traer los detalles del usuario durante la autenticación
		@Bean
		public UserDetailsService userDetailsService() {
			return new UserInfoUserDetailsService();
		}
		//Este metodo es para autenticar a los usuarios, se utiliza el "DaoAuthenticationProvider" que es el que provee la autenticacion
		// que utiliza el "DetailsService" para traer los detalles del usuario.
		@Bean
		public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider daoAutheticationProvider = new DaoAuthenticationProvider();
			daoAutheticationProvider.setUserDetailsService(userDetailsService());
			daoAutheticationProvider.setPasswordEncoder(passwordEncoder());
			return daoAutheticationProvider;
		}
		//Este metodo es unicamente para que la password que ingrese el usuario, al ser guardada en la base, este encriptada
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
				
				
}
