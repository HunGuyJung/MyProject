package com.dogFoot.vo;

public class DetailGoodsVo {
	private int b_no; 
	private String title;
	private String content; 
	private int g_code; 
	private String b_img;
	private String g_name;
	private int price;
	private int amount;
	private String color;
	private String g_size;
	private int total;
	
	public DetailGoodsVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DetailGoodsVo(int b_no, String title, String content, int g_code, String b_img, String g_name, int price,
			int amount, String color, String g_size, int total) {
		super();
		this.b_no = b_no;
		this.title = title;
		this.content = content;
		this.g_code = g_code;
		this.b_img = b_img;
		this.g_name = g_name;
		this.price = price;
		this.amount = amount;
		this.color = color;
		this.g_size = g_size;
		this.total = total;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getG_code() {
		return g_code;
	}
	public void setG_code(int g_code) {
		this.g_code = g_code;
	}
	public String getB_img() {
		return b_img;
	}
	public void setB_img(String b_img) {
		this.b_img = b_img;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
