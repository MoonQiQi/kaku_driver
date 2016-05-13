package com.yichang.kaku.obj;

import java.io.Serializable;

public class MemberDriverObj implements Serializable {
	private String id_driver;
	private String name_driver;
	private String phone_driver;
	private String point_now;
	private String head;
	private String num_shop;
	private String flag_advert;
	private String flag_kkxz;
	private String flag_ddexz;

	public String getMoney_balance() {
		return money_balance;
	}

	public void setMoney_balance(String money_balance) {
		this.money_balance = money_balance;
	}

	private String money_balance;


	public String getId_driver() {
		return id_driver;
	}

	public void setId_driver(String id_driver) {
		this.id_driver = id_driver;
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

	public String getPoint_now() {
		return point_now;
	}

	public void setPoint_now(String point_now) {
		this.point_now = point_now;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNum_shop() {
		return num_shop;
	}

	public void setNum_shop(String num_shop) {
		this.num_shop = num_shop;
	}


	public String getFlag_advert() {
		return flag_advert;
	}

	public void setFlag_advert(String flag_advert) {
		this.flag_advert = flag_advert;
	}

	public String getFlag_kkxz() {
		return flag_kkxz;
	}

	public void setFlag_kkxz(String flag_kkxz) {
		this.flag_kkxz = flag_kkxz;
	}

	public String getFlag_ddexz() {
		return flag_ddexz;
	}

	public void setFlag_ddexz(String flag_ddexz) {
		this.flag_ddexz = flag_ddexz;
	}

	@Override
	public String toString() {
		return "MemberDriverObj{" +
				"id_driver='" + id_driver + '\'' +
				", name_driver='" + name_driver + '\'' +
				", phone_driver='" + phone_driver + '\'' +
				", point_now='" + point_now + '\'' +
				", head='" + head + '\'' +
				", num_shop='" + num_shop + '\'' +
				", flag_advert='" + flag_advert + '\'' +
				", flag_kkxz='" + flag_kkxz + '\'' +
				", flag_ddexz='" + flag_ddexz + '\'' +
				", money_balance='" + money_balance + '\'' +
				'}';
	}
}
