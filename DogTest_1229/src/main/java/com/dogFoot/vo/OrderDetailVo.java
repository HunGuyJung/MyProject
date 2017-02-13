package com.dogFoot.vo;

public class OrderDetailVo {
	private int od_no;
	private int o_no;
	private String g_no;
	private int count;
	private int subtotal;
	public int getOd_no() {
		return od_no;
	}
	public void setOd_no(int od_no) {
		this.od_no = od_no;
	}
	public int getO_no() {
		return o_no;
	}
	public void setO_no(int o_no) {
		this.o_no = o_no;
	}
	public String getG_no() {
		return g_no;
	}
	public void setG_no(String g_no) {
		this.g_no = g_no;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public OrderDetailVo(int od_no, int o_no, String g_no, int count, int subtotal) {
		super();
		this.od_no = od_no;
		this.o_no = o_no;
		this.g_no = g_no;
		this.count = count;
		this.subtotal = subtotal;
	}
	public OrderDetailVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
