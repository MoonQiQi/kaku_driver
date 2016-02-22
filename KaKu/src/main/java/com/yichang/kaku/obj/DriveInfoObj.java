package com.yichang.kaku.obj;

import java.io.Serializable;

public class DriveInfoObj implements Serializable {
	private String head;
	private String phone_driver;
	private String name_driver;
	private String flag_approve;
	private String code_url;
	private String name_real;
	private String card_driver;

	public String getCode_recommended() {
		return code_recommended;
	}

	public void setCode_recommended(String code_recommended) {
		this.code_recommended = code_recommended;
	}

	private String code_recommended;

	public String getName_real() {
		return name_real;
	}

	public void setName_real(String name_real) {
		this.name_real = name_real;
	}

	public String getCard_driver() {
		return card_driver;
	}

	public void setCard_driver(String card_driver) {
		this.card_driver = card_driver;
	}

	@Override
	public String toString() {
		return "DriveInfoObj{" +
				"head='" + head + '\'' +
				", phone_driver='" + phone_driver + '\'' +
				", name_driver='" + name_driver + '\'' +
				", flag_approve='" + flag_approve + '\'' +
				", code_url='" + code_url + '\'' +
				", name_real='" + name_real + '\'' +
				", card_driver='" + card_driver + '\'' +
				'}';
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getPhone_driver() {
		return phone_driver;
	}

	public void setPhone_driver(String phone_driver) {
		this.phone_driver = phone_driver;
	}

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getFlag_approve() {
		return flag_approve;
	}

	public void setFlag_approve(String flag_approve) {
		this.flag_approve = flag_approve;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
}
