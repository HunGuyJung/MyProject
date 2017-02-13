package com.dogFoot.vo;

import java.util.Date;

public class OrdersVo {
	private int o_no;
	private int m_no;
	private int pay_type;
	private Date pay_time;
	private String depositor;
	private String delivery_no;
	private String receiver;
	private String receiver_addr;
	private String receiver_phone;
	private int total;
	private String reason;
	private int o_code;
	private String create_time;
	private Date update_time;
	private Date delete_time;
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
	public String getDepositor() {
		return depositor;
	}
	public void setDepositor(String depositor) {
		this.depositor = depositor;
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
	public String getReceiver_addr() {
		return receiver_addr;
	}
	public void setReceiver_addr(String receiver_addr) {
		this.receiver_addr = receiver_addr;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public OrdersVo(int o_no, int m_no, int pay_type, Date pay_time, String depositor, String delivery_no,
			String receiver, String receiver_addr, String receiver_phone, int total, String reason, int o_code,
			String create_time, Date update_time, Date delete_time) {
		super();
		this.o_no = o_no;
		this.m_no = m_no;
		this.pay_type = pay_type;
		this.pay_time = pay_time;
		this.depositor = depositor;
		this.delivery_no = delivery_no;
		this.receiver = receiver;
		this.receiver_addr = receiver_addr;
		this.receiver_phone = receiver_phone;
		this.total = total;
		this.reason = reason;
		this.o_code = o_code;
		this.create_time = create_time;
		this.update_time = update_time;
		this.delete_time = delete_time;
	}
	public OrdersVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
