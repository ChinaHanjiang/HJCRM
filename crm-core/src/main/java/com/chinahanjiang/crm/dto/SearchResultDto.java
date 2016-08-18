package com.chinahanjiang.crm.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDto {
	
	private List<Object> rows = new ArrayList<Object>();

	private int total;

	public SearchResultDto() {

	}

	public SearchResultDto(List<Object> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
