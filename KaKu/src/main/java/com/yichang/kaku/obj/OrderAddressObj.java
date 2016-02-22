package com.yichang.kaku.obj;

import java.io.Serializable;

public class OrderAddressObj implements Serializable {


	private String addr;
	private String id_addr;
	private String phone_addr;
	private String name_addr;

	@Override
	public String toString() {
		return "OrderAddressObj{" +
				"addr='" + addr + '\'' +
				", id_addr='" + id_addr + '\'' +
				", phone_addr='" + phone_addr + '\'' +
				", name_addr='" + name_addr + '\'' +
				'}';
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getId_addr() {
		return id_addr;
	}

	public void setId_addr(String id_addr) {
		this.id_addr = id_addr;
	}

	public String getPhone_addr() {
		return phone_addr;
	}

	public void setPhone_addr(String phone_addr) {
		this.phone_addr = phone_addr;
	}

	public String getName_addr() {
		return name_addr;
	}

	public void setName_addr(String name_addr) {
		this.name_addr = name_addr;
	}
}
