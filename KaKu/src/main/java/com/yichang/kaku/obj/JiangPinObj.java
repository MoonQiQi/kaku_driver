package com.yichang.kaku.obj;

import java.io.Serializable;

public class JiangPinObj implements Serializable {
	
	private String id_wheel_win;
	private String id_driver;
	private String name_prize;
	private String type;
	private String id_wheel;
	private String time_wheel;
	private String flag_exchange;
	private String time_exchange;
	private String time_send;
	private String name_driver;
	private String phone_driver;
	private String addr_driver;
	private String pass_card;
	private String no_card;
	private String company_delivery;
	private String no_delivery;
	private String image_prize;

	public String getId_wheel_win() {
		return id_wheel_win;
	}

	public void setId_wheel_win(String id_wheel_win) {
		this.id_wheel_win = id_wheel_win;
	}

	public String getId_driver() {
		return id_driver;
	}

	public void setId_driver(String id_driver) {
		this.id_driver = id_driver;
	}

	public String getName_prize() {
		return name_prize;
	}

	public void setName_prize(String name_prize) {
		this.name_prize = name_prize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId_wheel() {
		return id_wheel;
	}

	public void setId_wheel(String id_wheel) {
		this.id_wheel = id_wheel;
	}

	public String getTime_wheel() {
		return time_wheel;
	}

	public void setTime_wheel(String time_wheel) {
		this.time_wheel = time_wheel;
	}

	public String getFlag_exchange() {
		return flag_exchange;
	}

	public void setFlag_exchange(String flag_exchange) {
		this.flag_exchange = flag_exchange;
	}

	public String getTime_exchange() {
		return time_exchange;
	}

	public void setTime_exchange(String time_exchange) {
		this.time_exchange = time_exchange;
	}

	public String getTime_send() {
		return time_send;
	}

	public void setTime_send(String time_send) {
		this.time_send = time_send;
	}

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getPhone_driver() {
		return phone_driver;
	}

	public void setPhone_driver(String phone_driver) {
		this.phone_driver = phone_driver;
	}

	public String getAddr_driver() {
		return addr_driver;
	}

	public void setAddr_driver(String addr_driver) {
		this.addr_driver = addr_driver;
	}

	public String getPass_card() {
		return pass_card;
	}

	public void setPass_card(String pass_card) {
		this.pass_card = pass_card;
	}

	public String getNo_card() {
		return no_card;
	}

	public void setNo_card(String no_card) {
		this.no_card = no_card;
	}

	public String getCompany_delivery() {
		return company_delivery;
	}

	public void setCompany_delivery(String company_delivery) {
		this.company_delivery = company_delivery;
	}

	public String getNo_delivery() {
		return no_delivery;
	}

	public void setNo_delivery(String no_delivery) {
		this.no_delivery = no_delivery;
	}

	public String getImage_prize() {
		return image_prize;
	}

	public void setImage_prize(String image_prize) {
		this.image_prize = image_prize;
	}

	@Override
	public String toString() {
		return "JiangPinObj{" +
				"id_wheel_win='" + id_wheel_win + '\'' +
				", id_driver='" + id_driver + '\'' +
				", name_prize='" + name_prize + '\'' +
				", type='" + type + '\'' +
				", id_wheel='" + id_wheel + '\'' +
				", time_wheel='" + time_wheel + '\'' +
				", flag_exchange='" + flag_exchange + '\'' +
				", time_exchange='" + time_exchange + '\'' +
				", time_send='" + time_send + '\'' +
				", name_driver='" + name_driver + '\'' +
				", phone_driver='" + phone_driver + '\'' +
				", addr_driver='" + addr_driver + '\'' +
				", pass_card='" + pass_card + '\'' +
				", no_card='" + no_card + '\'' +
				", company_delivery='" + company_delivery + '\'' +
				", no_delivery='" + no_delivery + '\'' +
				", image_prize='" + image_prize + '\'' +
				'}';
	}
}
