package com.unilink.backend.unilink.repository;
import com.unilink.backend.unilink.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<Person, String> {
	Person findByEmail(String email);
	
}
