package com.unilink.backend.unilink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unilink.backend.unilink.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	Project findById(int id); 
	
	List<Project> findByProjectNameContainingOrDescriptionContaining(String s,String s2);
}
