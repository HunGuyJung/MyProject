package com.dogFoot.vo;

import java.util.Date;

public class BoardLikeVo {
	private int l_no;
	private int m_no;
	private int b_no;
	private Date create_time;
	private Date delete_time;
	public int getL_no() {
		return l_no;
	}
	public void setL_no(int l_no) {
		this.l_no = l_no;
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
	public BoardLikeVo(int l_no, int m_no, int b_no, Date create_time, Date delete_time) {
		super();
		this.l_no = l_no;
		this.m_no = m_no;
		this.b_no = b_no;
		this.create_time = create_time;
		this.delete_time = delete_time;
	}
	public BoardLikeVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
