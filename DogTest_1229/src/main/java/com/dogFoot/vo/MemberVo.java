package com.dogFoot.vo;

import java.util.Date;

public class MemberVo {
	private int m_no;
	private String id;
	private String pwd;
	private String m_name;
	private String phone;
	private String e_mail;
	private String addr;
	private int m_level;
	private int sns_type;
	private String sns_id;
	private Date create_time;
	private Date update_time;
	private Date delete_time;
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getM_level() {
		return m_level;
	}
	public void setM_level(int m_level) {
		this.m_level = m_level;
	}
	public int getSns_type() {
		return sns_type;
	}
	public void setSns_type(int sns_type) {
		this.sns_type = sns_type;
	}
	public String getSns_id() {
		return sns_id;
	}
	public void setSns_id(String sns_id) {
		this.sns_id = sns_id;
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
	public MemberVo(int m_no, String id, String pwd, String m_name, String phone, String e_mail, String addr,
			int m_level, int sns_type, String sns_id, Date create_time, Date update_time, Date delete_time) {
		super();
		this.m_no = m_no;
		this.id = id;
		this.pwd = pwd;
		this.m_name = m_name;
		this.phone = phone;
		this.e_mail = e_mail;
		this.addr = addr;
		this.m_level = m_level;
		this.sns_type = sns_type;
		this.sns_id = sns_id;
		this.create_time = create_time;
		this.update_time = update_time;
		this.delete_time = delete_time;
	}
	public MemberVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
