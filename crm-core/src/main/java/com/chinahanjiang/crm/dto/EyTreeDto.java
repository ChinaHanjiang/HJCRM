package com.chinahanjiang.crm.dto;

import java.util.List;

public class EyTreeDto {

	private int id;
	
	private String text;
	
	private String state;
	
	private int isF;
	
	private List<EyTreeDto> children;

	public EyTreeDto(){
		
	}
	
	public EyTreeDto(int id, int isF,String text, String status) {
		super();
		this.id = id;
		this.isF = isF;
		this.text = text;
		this.state = status;
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
}
