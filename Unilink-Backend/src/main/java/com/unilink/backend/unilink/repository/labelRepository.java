package com.unilink.backend.unilink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unilink.backend.unilink.model.Label;

public interface labelRepository  extends JpaRepository<Label, Integer> {

	
}
