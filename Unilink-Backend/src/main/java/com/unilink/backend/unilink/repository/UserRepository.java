package com.unilink.backend.unilink.repository;
import com.unilink.backend.unilink.model.User;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);
	
}
