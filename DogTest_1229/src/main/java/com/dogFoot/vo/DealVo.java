package com.dogFoot.vo;

import java.sql.Date;

public class DealVo {
	private int d_no; 
	private int source_no; 
	private String g_no; 
	private Date d_date; 
	private int d_qty; 
	private int d_total;
	private Date f_date;
	
	public DealVo(int d_no, int source_no, String g_no, Date d_date, int d_qty, int d_total, Date f_date) {
		super();
		this.d_no = d_no;
		this.source_no = source_no;
		this.g_no = g_no;
		this.d_date = d_date;
		this.d_qty = d_qty;
		this.d_total = d_total;
		this.f_date = f_date;
	}

	public DealVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getD_no() {
		return d_no;
	}

	public void setD_no(int d_no) {
		this.d_no = d_no;
	}

	public int getSource_no() {
		return source_no;
	}

	public void setSource_no(int source_no) {
		this.source_no = source_no;
	}

	public String getG_no() {
		return g_no;
	}

	public void setG_no(String g_no) {
		this.g_no = g_no;
	}

	public Date getD_date() {
		return d_date;
	}

	public void setD_date(Date d_date) {
		this.d_date = d_date;
	}

	public int getD_qty() {
		return d_qty;
	}

	public void setD_qty(int d_qty) {
		this.d_qty = d_qty;
	}

	public int getD_total() {
		return d_total;
	}

	public void setD_total(int d_total) {
		this.d_total = d_total;
	}

	public Date getF_date() {
		return f_date;
	}

	public void setF_date(Date f_date) {
		this.f_date = f_date;
	}
	
	
}
