package com.chinahanjiang.crm.dto;

public class ComboResultDto {

	private int id;
	
	private String text;
	
	private boolean selected;
	
	public ComboResultDto(){
		
	}
	
	public ComboResultDto(int id, String text, boolean selected) {
		super();
		this.id = id;
		this.text = text;
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
