package com.unilink.backend.unilink.resource;

import com.unilink.backend.unilink.dto.UserDto;
import com.unilink.backend.unilink.model.Label;
import com.unilink.backend.unilink.model.Project;
import com.unilink.backend.unilink.model.User;
import com.unilink.backend.unilink.repository.ProjectRepository;
import com.unilink.backend.unilink.repository.UserRepository;
import com.unilink.backend.unilink.repository.labelRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(value = "/projectsOfUser/{email}")
	public List<Project> getAllProjectsOfUser(@PathVariable("email")String email){
		User user= userRepository.findByEmail(email);
		return user.getProjectMember();

	}

	@PostMapping(value="/loginUser") //userDto {"email":"mail","password":"pass"}
	public User login (@RequestBody final UserDto e){
		
		if(e!=null&&e.getEmail()!=null&&e.getPassword()!=null) {
			User currentUser=userRepository.findByEmail(e.getEmail());
			if(currentUser.getPassword().equals(e.getPassword())) {
				return currentUser;
			}else {
				return null;
			}
		}else{
			return null;
		}	
				
		
		
	}
	
	@PostMapping(value="/loadUser")
	public List<User> persistUser (@RequestBody final User users){
		userRepository.save(users);
		return userRepository.findAll();
	}

	@PostMapping(value="/loadProject/{email}")
	public Optional<Project> persistProject (@RequestBody final Project project,@PathVariable("email") String email){
		User user= userRepository.findByEmail(email);
		project.setCreator(user);
		user.addProjectCreator(project);
		userRepository.save(user);
		projectRepository.save(project);
		return projectRepository.findById(project.getId());
	}
	
	@PostMapping(value="/loadMemberToProject/{email}")
	public Optional<Project> addMemberToProject (@RequestBody final Project project,@PathVariable("email") String email){
		//can be improved!
		User user= userRepository.findByEmail(email);
		user.addProjectMember(project);
		userRepository.save(user);
		project.addMember(user);
		projectRepository.save(project);
		return projectRepository.findById(project.getId());
	}
	
	@PostMapping(value="/applyMemberToProject/{email}")
	public Optional<Project> applyMemberToProject (@RequestBody final Project project,@PathVariable("email") String email){
		User user= userRepository.findByEmail(email);
		user.addProjectApplied(project);
		userRepository.save(user);
		project.addApplicant(user);;
		projectRepository.save(project);
		return projectRepository.findById(project.getId());
	}
	
	@PostMapping(value="/acceptMemberToProject/{email}")
	public Optional<Project> acceptMemberToProject (@RequestBody final Project project,@PathVariable("email") String email){
		User user= userRepository.findByEmail(email);
		user.removeProjectApplied(project);
		user.addProjectMember(project);
		userRepository.save(user);
		project.removeApplicant(user);
		project.addMember(user);
		projectRepository.save(project);
		return projectRepository.findById(project.getId());
	}
	
	
	
	
	
	
	

	@PostMapping(value="/loadLabel")
	public List<Label> persistProject (@RequestBody final Label label){
		labelRepository.save(label);
		return labelRepository.findAll();
	}
}