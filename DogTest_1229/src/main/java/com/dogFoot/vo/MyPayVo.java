package com.dogFoot.vo;

import java.util.Date;

public class MyPayVo {
	private int o_no;
	private int m_no;
	private int pay_type;
	private Date pay_time;
	private String delivery_no;
	private String receiver;
	private int total;
	private int o_code;
	private String create_time;
	private String g_no;
	private String g_name;
	private String g_img;
	public MyPayVo(int o_no, int m_no, int pay_type, Date pay_time, String delivery_no, String receiver, int total,
			int o_code, String create_time, String g_no, String g_name, String g_img) {
		super();
		this.o_no = o_no;
		this.m_no = m_no;
		this.pay_type = pay_type;
		this.pay_time = pay_time;
		this.delivery_no = delivery_no;
		this.receiver = receiver;
		this.total = total;
		this.o_code = o_code;
		this.create_time = create_time;
		this.g_no = g_no;
		this.g_name = g_name;
		this.g_img = g_img;
	}
	public MyPayVo() {
		super();
	}
	public int getO_no() {
		return o_no;
	}
	public void setO_no(int o_no) {
		this.o_no = o_no;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public int getPay_type() {
		return pay_type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getDelivery_no() {
		return delivery_no;
	}
	public void setDelivery_no(String delivery_no) {
		this.delivery_no = delivery_no;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getO_code() {
		return o_code;
	}
	public void setO_code(int o_code) {
		this.o_code = o_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getG_no() {
		return g_no;
	}
	public void setG_no(String g_no) {
		this.g_no = g_no;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public String getG_img() {
		return g_img;
	}
	public void setG_img(String g_img) {
		this.g_img = g_img;
	}
	
	
}
