package com.dogFoot.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class CodiVo {
	private int b_no;
	private int m_no; 
	private int b_type;
	private String title; 
	private String content; 
	private Date create_time; 
	private Date update_time; 
	private Date delete_time; 
	private int hit; 
	private int c_count; 
	private int like_count;
	private int g_code;
	private String b_img;
	private String cap;
	private String outer;
	private String top;
	private String bottom;
	private String shoes;
	private String cap_init;
	private String outer_init;
	private String top_init;
	private String bottom_init;
	private String shoes_init;
	private String id;
	private MultipartFile uploadFile;
	public CodiVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CodiVo(int b_no, int m_no, int b_type, String title, String content, Date create_time, Date update_time,
			Date delete_time, int hit, int c_count, int like_count, int g_code, String b_img, String cap, String outer,
			String top, String bottom, String shoes, String cap_init, String outer_init, String top_init,
			String bottom_init, String shoes_init,String id, MultipartFile uploadFile) {
		super();
		this.b_no = b_no;
		this.m_no = m_no;
		this.b_type = b_type;
		this.title = title;
		this.content = content;
		this.create_time = create_time;
		this.update_time = update_time;
		this.delete_time = delete_time;
		this.hit = hit;
		this.c_count = c_count;
		this.like_count = like_count;
		this.g_code = g_code;
		this.b_img = b_img;
		this.cap = cap;
		this.outer = outer;
		this.top = top;
		this.bottom = bottom;
		this.shoes = shoes;
		this.cap_init = cap_init;
		this.outer_init = outer_init;
		this.top_init = top_init;
		this.bottom_init = bottom_init;
		this.shoes_init = shoes_init;
		this.id = id;
		this.uploadFile = uploadFile;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public int getB_type() {
		return b_type;
	}
	public void setB_type(int b_type) {
		this.b_type = b_type;
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
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Date getDelete_time() {
		return delete_time;
	}
	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getC_count() {
		return c_count;
	}
	public void setC_count(int c_count) {
		this.c_count = c_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
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
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getOuter() {
		return outer;
	}
	public void setOuter(String outer) {
		this.outer = outer;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getBottom() {
		return bottom;
	}
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}
	public String getShoes() {
		return shoes;
	}
	public void setShoes(String shoes) {
		this.shoes = shoes;
	}
	public String getCap_init() {
		return cap_init;
	}
	public void setCap_init(String cap_init) {
		this.cap_init = cap_init;
	}
	public String getOuter_init() {
		return outer_init;
	}
	public void setOuter_init(String outer_init) {
		this.outer_init = outer_init;
	}
	public String getTop_init() {
		return top_init;
	}
	public void setTop_init(String top_init) {
		this.top_init = top_init;
	}
	public String getBottom_init() {
		return bottom_init;
	}
	public void setBottom_init(String bottom_init) {
		this.bottom_init = bottom_init;
	}
	public String getShoes_init() {
		return shoes_init;
	}
	public void setShoes_init(String shoes_init) {
		this.shoes_init = shoes_init;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
}
