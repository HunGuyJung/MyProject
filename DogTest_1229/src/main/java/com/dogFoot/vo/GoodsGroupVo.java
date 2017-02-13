package com.dogFoot.vo;

public class GoodsGroupVo {
	private int b_no;
	private String g_no;
	private String g_img;
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getG_no() {
		return g_no;
	}
	public void setG_no(String g_no) {
		this.g_no = g_no;
	}
	public String getG_img() {
		return g_img;
	}
	public void setG_img(String g_img) {
		this.g_img = g_img;
	}
	public GoodsGroupVo(int b_no, String g_no, String g_img) {
		super();
		this.b_no = b_no;
		this.g_no = g_no;
		this.g_img = g_img;
	}
	public GoodsGroupVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
