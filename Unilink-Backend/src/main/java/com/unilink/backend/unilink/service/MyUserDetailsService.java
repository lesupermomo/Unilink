package com.unilink.backend.unilink.service;

//import io.javabrains.springsecurityjpa.models.MyUserDetails;

//import io.javabrains.springsecurityjpa.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unilink.backend.unilink.repository.UserRepository;
//import com.unilink.backend.unilink.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      com.unilink.backend.unilink.model.Person user = userRepository.findByEmail(email);
      
      if(user.equals(null)) {
      	throw new UsernameNotFoundException("Username or password not found");
      }
      
      ArrayList<String> array=new ArrayList<String>();
      array.add(user.getRoles());
      Collection<String> s=array;
      UserDetails userDetails= new User(user.getEmail(), user.getPassword(), new ArrayList<>());
//	  UserDetails userDetails= new User("test", "test", new ArrayList<>());
      return userDetails;
      
//
//      user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
//
//      return user.map(MyUserDetails::new).get();
  }
}