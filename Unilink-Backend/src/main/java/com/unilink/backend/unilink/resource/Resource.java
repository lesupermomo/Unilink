package com.unilink.backend.unilink.resource;

import com.unilink.backend.unilink.dto.AuthenticationResponse;
import com.unilink.backend.unilink.dto.CredentialDto;
import com.unilink.backend.unilink.dto.ProjectDto;
import com.unilink.backend.unilink.dto.UserDto;
import com.unilink.backend.unilink.model.Label;
import com.unilink.backend.unilink.model.Person;
import com.unilink.backend.unilink.model.Project;
import com.unilink.backend.unilink.repository.LabelRepository;
import com.unilink.backend.unilink.repository.ProjectRepository;
import com.unilink.backend.unilink.repository.UserRepository;
import com.unilink.backend.unilink.service.MyUserDetailsService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(value = "/")
public class Resource {


	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	LabelRepository labelRepository;
	
	
//
//	@GetMapping("/login") //userDto {"email":"mail","password":"pass"}
//	public String loginTemp (@RequestBody final UserDto e){
//		
//		return("<h1> Welcome to Unilink <h1> ");	
//		
//		
//	}
//	
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


	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody CredentialDto authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping("/userFromJwt")
	public UserDto greeting(@RequestHeader("Authorization") String language) {
	    // code that uses the language variable
		
		String jwt= language.substring(7);
		JwtUtil jwtUtil=new JwtUtil();
		String email= jwtUtil.extractUsername(jwt);
		
		Person user= userRepository.findByEmail(email);
		return new UserDto(user);    
	}
		
	/**
	 * 
	 * Starting from here are REST methods to contact load and get projects
	 */

	@GetMapping(value = "/allUsers")
	public List<UserDto> getAllUsers(){
		return convertUsersToDto(userRepository.findAll());

	}	
	
	@GetMapping("/allProjects")
	public List<ProjectDto> getAllProjects(){
		return convertProjectsToDto(projectRepository.findAll());

	}

	@GetMapping("/allLabels")
	public List<Label> getAllLabels(){
		return labelRepository.findAll();

	}
	
	@GetMapping( "/projectsOfUser/{email}")
	public List<ProjectDto> getAllProjectsOfUser(@PathVariable("email")String email){
		Person user= userRepository.findByEmail(email);
		return convertProjectsToDto(user.getProjectMember());

	}

	@PostMapping("/loginUser") //userDto {"email":"mail","password":"pass"}
	public UserDto login (@RequestBody CredentialDto e){
		
		if(e!=null&&(e.getEmail().equals(null)==false)&&(e.getPassword().equals(null)==false)) {
		    Person currentUser=userRepository.findByEmail(e.getEmail());
			if(currentUser.getPassword().equals(e.getPassword())) {
				currentUser.setPassword("null");
				return new UserDto(currentUser);
			}else {
				return null;
			}
		}else{
			return null;
		}	
	}
	
	@PostMapping("/loadUser")
	public UserDto persistUser (@RequestBody final UserDto userDto){
		Person user=new Person(userDto);
		userRepository.save(user);
		return new UserDto(userRepository.findByEmail(user.getEmail()));
	}

	//creating a project.
	@PostMapping("/loadProject/{email}")
	public ProjectDto persistProject (@RequestBody final ProjectDto projectDto,@PathVariable("email") String email){

		Person user= userRepository.findByEmail(email);
		Project project= new Project(projectDto);
		
		project.setCreator(user);
		user.addProjectCreator(project);
		user.addProjectMember(project);
		userRepository.save(user);
		project.addMember(user);
		projectRepository.save(project);
		return new ProjectDto( project ); //making sure it actually worked 
	}
	
	
	// TODO: the contains method doesnt work, CHECK FOR DUPLICATES
	@PostMapping("/loadMemberToProject/{email}")
	public ProjectDto addMemberToProject (@RequestBody final ProjectDto projectDto,@PathVariable("email") String email){
		//can be improved!
		
		Optional<Project> projectt= projectRepository.findById(projectDto.getId());
		Project project = projectt.get();
		Person user= userRepository.findByEmail(email);
		
		user.addProjectMember(project);
		userRepository.save(user);
		project.addMember(user);
		projectRepository.save(project);
		return new ProjectDto(project); 
	
	}
	
	@PostMapping("/loadMemberToProject/{email}/{projectId}")
	public ProjectDto addMemberToProject (@PathVariable("projectId") String projectId,@PathVariable("email") String email){
		//can be improved!
		
		
		Project project =projectRepository.findById(Integer.parseInt(projectId));
		Person user= userRepository.findByEmail(email);
		
		user.addProjectMember(project);
		userRepository.save(user);
		project.addMember(user);
		projectRepository.save(project);
		return new ProjectDto(project); 
	
	}
	
	
	@PostMapping("/applyMemberToProject/{email}")
	public ProjectDto applyMemberToProject (@RequestBody final ProjectDto projectDto,@PathVariable("email") String email){
		

		Optional<Project> projectt= projectRepository.findById(projectDto.getId());
		Project project = projectt.get();
		Person user= userRepository.findByEmail(email);
		
		user.addProjectApplied(project);
		userRepository.save(user);
		project.addApplicant(user);;
		projectRepository.save(project);
		return new ProjectDto(project);
	}
	
	@PostMapping("/applyMemberToProject/{email}/{projectId}")
	public ProjectDto applyMemberToProject (@PathVariable("projectId") String projectId,@PathVariable("email") String email){
		

		Project project =projectRepository.findById(Integer.parseInt(projectId));
		Person user= userRepository.findByEmail(email);
		
		user.addProjectApplied(project);
		userRepository.save(user);
		project.addApplicant(user);;
		projectRepository.save(project);
		return new ProjectDto(project);
	}
	
	//WORKS
	@PostMapping("/acceptMemberToProject/{email}")
	public ProjectDto acceptMemberToProject (@RequestBody final ProjectDto projectDto,@PathVariable("email") String email){
		

		Optional<Project> projectt= projectRepository.findById(projectDto.getId());
		Project project = projectt.get();
		
		Person user= userRepository.findByEmail(email);
		user.removeProjectApplied(project);
		user.addProjectMember(project);
		userRepository.save(user);
		project.removeApplicant(user);
		project.addMember(user);
		projectRepository.save(project);
		
		return new ProjectDto(project);
	}
	
	@PostMapping("/acceptMemberToProject/{email}/{projectId}")
	public ProjectDto acceptMemberToProject (@PathVariable("projectId") String projectId,@PathVariable("email") String email){
		

		Project project= projectRepository.findById(Integer.parseInt(projectId));
		Person user= userRepository.findByEmail(email);
		
		user.removeProjectApplied(project);
		user.addProjectMember(project);
		userRepository.save(user);
		project.removeApplicant(user);
		project.addMember(user);
		projectRepository.save(project);
		
		return new ProjectDto(project);
	}
	
	

	@PostMapping("/loadLabel")
	public List<Label> persistProject (@RequestBody final Label label){
		labelRepository.save(label);
		return labelRepository.findAll();
	}
	
	
	
	//////////////////// CONVERTING TO DTOS ///////////////////////	
	
	
	
	public ArrayList<UserDto> convertUsersToDto(List<Person> l){
		ArrayList<UserDto> users= new ArrayList<UserDto>();
		for(int i=0; i<l.size(); i++){
			UserDto temp=new UserDto(l.get(i));
			temp.setPassword("null");
			users.add(new UserDto(l.get(i)));
		}
		return users;
	}
	
	public ArrayList<ProjectDto> convertProjectsToDto(List<Project> l){
		ArrayList<ProjectDto> projects= new ArrayList<ProjectDto>();
		for(int i=0; i<l.size(); i++){
			projects.add(new ProjectDto(l.get(i)));
		}
		return projects;
	}
	
	private List<ProjectDto> convertProjectsToDto(Optional<Project> list) {
		ArrayList<ProjectDto> projects= new ArrayList<ProjectDto>();
		if(list.isPresent()) {
			List<Project> l= (List<Project>) list.get();
			for(int i=0; i<l.size(); i++){
				projects.add(new ProjectDto(l.get(i)));
			}
			return projects;
		}else {
			return null;
		}
	}
	
	
}