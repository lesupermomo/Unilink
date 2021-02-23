package com.unilink.backend.unilink.resource;

import com.unilink.backend.unilink.dto.AuthenticationResponse;
import com.unilink.backend.unilink.dto.CredentialDto;
import com.unilink.backend.unilink.dto.LabelDto;
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
import java.util.Iterator;
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
public class Resource {


	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	LabelRepository labelRepository;



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
		//set the password to the hashcode like it is in the database 
		if(authenticationRequest.equals(null)==false) {
			authenticationRequest.setPassword(Integer.toString(authenticationRequest.getPassword().hashCode()));
		}

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
	public UserDto userFromJwt(@RequestHeader("Authorization") String language) {
		// code that uses the language variable

		String jwt= language.substring(7);
		JwtUtil jwtUtil=new JwtUtil();
		String email= jwtUtil.extractUsername(jwt);

		Person user= userRepository.findByEmail(email);
		user.setPassword("");
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
	
	@GetMapping( "/searchBylabel/{labelName}")
	public List<ProjectDto> searchBylabel(@PathVariable("labelName")String labelName){
		Label label;
		label= labelRepository.findBylabelName(labelName);
		if(label==null||label.equals(null)) {
			return new ArrayList<ProjectDto>();
		}else {
			return convertProjectsToDto(label.getProjects());
		}

	}
	
	@GetMapping( "/searchByWord/{word}")
	public List<ProjectDto> searchByWord(@PathVariable("word")String word){	
			return convertProjectsToDto(projectRepository.findByProjectNameContainingOrDescriptionContaining(word, word));
	}
	
	@GetMapping( "/searchByWords/{words}")
	public List<ProjectDto> searchByWords(@PathVariable("words")String words){
		String[] wordList = words.split("\\s+");
		ArrayList<Integer> ids=new ArrayList<Integer>();
		ArrayList<ProjectDto> projects=new ArrayList<ProjectDto>();
		for (int i = 0; i < wordList.length; i++) {
			ArrayList<ProjectDto> projectsFound=convertProjectsToDto(projectRepository.findByProjectNameContainingOrDescriptionContaining(wordList[i], wordList[i]));
			for (ProjectDto x : projectsFound) {
				if(ids.contains(x.getId())==false) {
					projects.add(x);
					ids.add(x.getId());
				}
				
			}
			
		}
			return projects;
	}
	

	@GetMapping( "/searchFull/{words}")
	public List<ProjectDto> searchFull(@PathVariable("words")String words){
		ArrayList<Integer> ids=new ArrayList<Integer>();
		ArrayList<ProjectDto> projects=new ArrayList<ProjectDto>();
		String[] wordList = words.split("\\s+");
		List<Project> list=projectRepository.findAll();
		
		for (int i = 0; i < wordList.length; i++) {
			//searches for the currently seen word
			
			
			for (Project x : list) {
				
				String[] descriptionWords = x.getDescription().split("\\s+");
				String title =  x.getProjectName();
				String[] projectWords = title.split("\\s+");
				List<Label> labelList = x.getLabels();
				
				
				//description 
				for (int j = 0; j < descriptionWords.length; j++) {
					if(wordList[i].toLowerCase().compareTo(descriptionWords[j].toLowerCase())==0) {
						if(ids.contains(x.getId())==false) {
							projects.add(new ProjectDto(x));
							ids.add(x.getId());
						}	
					}
				}
				
				//Project title
				for (int j = 0; j < projectWords.length; j++) {
					if(wordList[i].toLowerCase().compareTo(projectWords[j].toLowerCase())==0) {
						if(ids.contains(x.getId())==false) {
							projects.add(new ProjectDto(x));
							ids.add(x.getId());
						}	
					}
				}
				
				//Project full title
//				if(i==0) {
//					if(words.toLowerCase().compareTo(title.toLowerCase())==0) {
//						if(ids.contains(x.getId())==false) {
//							projects.add(new ProjectDto(x));
//							ids.add(x.getId());
//						}	
//					}
//				}
//				
				//Project labels
				for (Label label : labelList) {
					if(label.getLabelName().toLowerCase().compareTo(wordList[i].toLowerCase())==0) {
						if(ids.contains(x.getId())==false) {
							projects.add(new ProjectDto(x));
							ids.add(x.getId());
						}	
					}
				}
//				
//				//Project full 
				if(i==0) {
					for (Label label : labelList) {
						if(label.getLabelName().toLowerCase().compareTo(words.toLowerCase())==0) {
							if(ids.contains(x.getId())==false) {
								projects.add(new ProjectDto(x));
								ids.add(x.getId());
							}	
						}
					}
				}
				
			}
		
			
		}
		
			return projects;
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
			if(Integer.toString(currentUser.getPassword().hashCode()).equals(e.getPassword())) {
				currentUser.setPassword("null");
				return new UserDto(currentUser);
			}else {
				return null;
			}
		}else{
			return null;
		}	
	}

	//TODO : fix veriffying login
	@PostMapping("/loadUser")
	public UserDto persistUser (@RequestBody final UserDto userDto) throws Exception{
		Person user= userRepository.findByEmail(userDto.getEmail());
		if(user==null || user.equals(null)) {
			user=new Person(userDto);
			user.setPassword(Integer.toString(user.getPassword().hashCode()));
			userRepository.save(user);
			return new UserDto(userRepository.findByEmail(user.getEmail()));
		}else {
			throw new Exception("Email already used");
		}

	}

	@PostMapping("/updateUser")
	public UserDto updateUser (@RequestHeader("Authorization") String language, @RequestBody final UserDto userDto) throws Exception{
		
		String jwt= language.substring(7);
		JwtUtil jwtUtil=new JwtUtil();
		String email= jwtUtil.extractUsername(jwt);
		if(email.equals(userDto.getEmail())) {
			Person user=new Person(userDto);
			user.setPassword(Integer.toString(user.getPassword().hashCode()));
			userRepository.save(user);
			return new UserDto(userRepository.findByEmail(user.getEmail()));

		}else {
			throw new Exception("in order to update information jwt must be of the user being modified");
		}

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


	@PostMapping("/addLabelToProject/{projectId}/{labelName}")
	public ProjectDto addLabelToProject (@PathVariable("projectId") String projectId,@PathVariable("labelName") String labelName){


		Project project= projectRepository.findById(Integer.parseInt(projectId));
		Label label= labelRepository.findBylabelName(labelName);



		project.addLabel(label);
		projectRepository.save(project);
		label.addProject(project);
		labelRepository.save(label);

		return new ProjectDto(project);
	}

	@PostMapping("/removeLabelFromProject/{projectId}/{labelName}")
	public ProjectDto removeLabelFromProject (@PathVariable("projectId") String projectId,@PathVariable("labelName") String labelName){


		Project project= projectRepository.findById(Integer.parseInt(projectId));
		Label label= labelRepository.findBylabelName(labelName);



		project.removeLabel(label);
		projectRepository.save(project);
		label.removeProject(project);
		labelRepository.save(label);

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

	//TODO : check if the label already exists
	@PostMapping("/loadLabel")
	public List<LabelDto> persistLabel (@RequestBody final Label label){
		label.setLabelName(label.getLabelName().toUpperCase()); //all labels are capital letter
		labelRepository.save(label);
		return convertLabelsToDto(labelRepository.findAll());
	}



	//////////////////// CONVERTING TO DTOS ///////////////////////	



	public ArrayList<UserDto> convertUsersToDto(List<Person> l){
		ArrayList<UserDto> users= new ArrayList<UserDto>();
		for(int i=0; i<l.size(); i++){
			UserDto temp=new UserDto(l.get(i));
			temp.setPassword("null");
			users.add(temp);
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


	public ArrayList<LabelDto> convertLabelsToDto(List<Label> l){
		ArrayList<LabelDto> labels= new ArrayList<LabelDto>();
		if(l!=null) {
			for(int i=0; i<l.size(); i++){
				LabelDto temp=new LabelDto(l.get(i));
				labels.add(temp);
			}
		}

		return labels;
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