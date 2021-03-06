package com.dogFoot.vo;

import java.sql.Date;

public class BoardVo {
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
	public BoardVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardVo(int b_no, int m_no, int b_type, String title,
			String content, Date create_time, Date update_time,
			Date delete_time, int hit, int c_count, int like_count, int g_code,
			String b_img) {
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
	
}
