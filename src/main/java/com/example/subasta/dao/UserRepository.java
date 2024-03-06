package com.example.subasta.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.subasta.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "SELECT * FROM usuarios WHERE email = ?1", nativeQuery = true)
	Optional<User> findByEmail(String email);
}
