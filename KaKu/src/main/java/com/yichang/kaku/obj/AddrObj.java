package com.yichang.kaku.obj;

import java.io.Serializable;

public class AddrObj implements Serializable {

	private String id_addr;
	private String addr;
	private String flag_default;
	private String phone_addr;
	private String name_addr;

	public String getId_addr() {
		return id_addr;
	}

	public void setId_addr(String id_addr) {
		this.id_addr = id_addr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFlag_default() {
		return flag_default;
	}

	public void setFlag_default(String flag_default) {
		this.flag_default = flag_default;
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

	@Override
	public String toString() {
		return "AddrObj{" +
				"id_addr='" + id_addr + '\'' +
				", addr='" + addr + '\'' +
				", flag_default='" + flag_default + '\'' +
				", phone_addr='" + phone_addr + '\'' +
				", name_addr='" + name_addr + '\'' +
				'}';
	}
}
