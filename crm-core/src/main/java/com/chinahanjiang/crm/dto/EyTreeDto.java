package com.chinahanjiang.crm.dto;

import java.util.List;
import java.util.Map;

public class EyTreeDto {

	private int id;
	
	private String text;
	
	private String state;
	
	private int isF;
	
	private Map<String,String> attributes;
	
	private List<EyTreeDto> children;

	public EyTreeDto(){
		
		state = "closed";
	}
	
	public EyTreeDto(int id, int isF,String text, String status,Map<String,String> attributes) {
		super();
		this.id = id;
		this.isF = isF;
		this.text = text;
		this.state = status;
		this.attributes = attributes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsF() {
		return isF;
	}

	public void setIsF(int isF) {
		this.isF = isF;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<EyTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<EyTreeDto> children) {
		this.children = children;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
