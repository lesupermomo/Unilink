package com.unilink.backend.unilink.dto;
import com.unilink.backend.unilink.model.Label;
public class LabelDto {
	
	private String labelName;
	private int numTags;
	
	public LabelDto() {
		
	}
	
	public LabelDto(Label l) {
		this.labelName=l.getLabelName();
		this.numTags=l.getNumTags();
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public int getNumTags() {
		return numTags;
	}

	public void setNumTags(int numTags) {
		this.numTags = numTags;
	}
	
	
	
}
