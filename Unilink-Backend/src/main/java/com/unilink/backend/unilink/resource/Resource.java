package com.unilink.backend.unilink.resource;

import com.unilink.backend.unilink.model.Label;
import com.unilink.backend.unilink.model.Project;
import com.unilink.backend.unilink.model.User;
import com.unilink.backend.unilink.repository.ProjectRepository;
import com.unilink.backend.unilink.repository.UserRepository;
import com.unilink.backend.unilink.repository.labelRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class Resource {
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	labelRepository labelRepository;

	
	@GetMapping("/")
	public String home() {
		return("<h1> Welcome to Unilink <h1> ");
	}
	
	@GetMapping("/user")
	public String user() {
		return("<h1> Welcome User to Unilink ;)<h1> ");
	}
	
	@GetMapping("/admin")
	public String admin() {
		return("<h1> HELLO ADMIN, GOOD LUCK MA MAN<h1> ");
	}
	
	
	/**
	 * 
	 * Starting from here are REST methods to contact load and get projects
	 */
	
	@GetMapping(value = "/allUsers")
	public List<User> getAllUsers(){
		return userRepository.findAll();
		
	}
	
	@GetMapping(value = "/allProjects")
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
		
	}
	
	@GetMapping(value = "/allLabels")
	public List<Project> getAllLabels(){
		return projectRepository.findAll();
		
	}
	
	@PostMapping(value="/loadUser")
	public List<User> persistUser (@RequestBody final User users){
		userRepository.save(users);
		return userRepository.findAll();
	}
	
	@PostMapping(value="/loadProject")
	public List<User> persistProject (@RequestBody final Project project){
		projectRepository.save(project);
		return userRepository.findAll();
	}
	
	@PostMapping(value="/loadLabel")
	public List<Label> persistProject (@RequestBody final Label label){
		labelRepository.save(label);
		return labelRepository.findAll();
	}
}
