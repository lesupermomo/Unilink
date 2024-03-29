package com.unilink.backend.unilink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unilink.backend.unilink.model.Label;
import com.unilink.backend.unilink.model.Project;

public interface LabelRepository  extends JpaRepository<Label, String> {

	Label findBylabelName(String labelName ); 
	
}
