package com.dogFoot.vo;

import java.sql.Date;

public class CommentsVo {
	private int c_no;
	private int m_no; 
	private int b_no; 
	private String id; 
	private String c_content; 
	private Date create_time; 
	private Date delete_time;
	private int c_ref;
	private int c_level;
	private int c_step;
	
	public CommentsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsVo(int c_no, int m_no, int b_no, String id, String c_content, Date create_time, Date delete_time,
			int c_ref, int c_level, int c_step) {
		super();
		this.c_no = c_no;
		this.m_no = m_no;
		this.b_no = b_no;
		this.id = id;
		this.c_content = c_content;
		this.create_time = create_time;
		this.delete_time = delete_time;
		this.c_ref = c_ref;
		this.c_level = c_level;
		this.c_step = c_step;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getC_no() {
		return c_no;
	}

	public void setC_no(int c_no) {
		this.c_no = c_no;
	}

	public int getM_no() {
		return m_no;
	}

	public void setM_no(int m_no) {
		this.m_no = m_no;
	}

	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

	public int getC_ref() {
		return c_ref;
	}

	public void setC_ref(int c_ref) {
		this.c_ref = c_ref;
	}

	public int getC_level() {
		return c_level;
	}

	public void setC_level(int c_level) {
		this.c_level = c_level;
	}

	public int getC_step() {
		return c_step;
	}

	public void setC_step(int c_step) {
		this.c_step = c_step;
	}

	
}
