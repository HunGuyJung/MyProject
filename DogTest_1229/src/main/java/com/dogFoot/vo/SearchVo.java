package com.dogFoot.vo;

import java.util.List;

public class SearchVo {
	
	private String keyword;
	private List<String> category;
	private List<String> brand;
	private List<String> color;
	private List<String> g_size;
	
	public SearchVo(String keyword, List<String> category, List<String> brand, List<String> color, List<String> g_size) {
		super();
		this.keyword = keyword;
		this.category = category;
		this.brand = brand;
		this.color = color;
		this.g_size = g_size;
	}

	public SearchVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getBrand() {
		return brand;
	}

	public void setBrand(List<String> brand) {
		this.brand = brand;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getG_size() {
		return g_size;
	}

	public void setG_size(List<String> g_size) {
		this.g_size = g_size;
	}
	
	
}
