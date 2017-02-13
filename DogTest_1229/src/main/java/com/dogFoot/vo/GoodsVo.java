package com.dogFoot.vo;

import org.springframework.web.multipart.MultipartFile;

public class GoodsVo {
	private	String g_no; 
	private int g_code;      
	private String g_name;        
	private int price;     
	private String brand;        
	private String category;   
	private	String material;    
	private String g_img;       
	private int recommend;    
	private String color;        
	private String g_size;        
	private int source_no;    
	private int amount;
	private MultipartFile uploadFile;
	public GoodsVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoodsVo(String g_no, int g_code, String g_name, int price, String brand, String category, String material,
			String g_img, int recommend, String color, String g_size, int source_no, int amount) {
		super();
		this.g_no = g_no;
		this.g_code = g_code;
		this.g_name = g_name;
		this.price = price;
		this.brand = brand;
		this.category = category;
		this.material = material;
		this.g_img = g_img;
		this.recommend = recommend;
		this.color = color;
		this.g_size = g_size;
		this.source_no = source_no;
		this.amount = amount;
	}
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getG_no() {
		return g_no;
	}
	public void setG_no(String g_no) {
		this.g_no = g_no;
	}
	public int getG_code() {
		return g_code;
	}
	public void setG_code(int g_code) {
		this.g_code = g_code;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getG_img() {
		return g_img;
	}
	public void setG_img(String g_img) {
		this.g_img = g_img;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getG_size() {
		return g_size;
	}
	public void setG_size(String g_size) {
		this.g_size = g_size;
	}
	public int getSource_no() {
		return source_no;
	}
	public void setSource_no(int source_no) {
		this.source_no = source_no;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
