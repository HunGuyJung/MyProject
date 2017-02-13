package com.dogFoot.vo;

public class SourceVo {
	private int source_no;
	private String s_name;
	private String s_location;
	private String s_phone;
	public int getSource_no() {
		return source_no;
	}
	public void setSource_no(int source_no) {
		this.source_no = source_no;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_location() {
		return s_location;
	}
	public void setS_location(String s_location) {
		this.s_location = s_location;
	}
	public String getS_phone() {
		return s_phone;
	}
	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}
	public SourceVo(int source_no, String s_name, String s_location, String s_phone) {
		super();
		this.source_no = source_no;
		this.s_name = s_name;
		this.s_location = s_location;
		this.s_phone = s_phone;
	}
	public SourceVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
