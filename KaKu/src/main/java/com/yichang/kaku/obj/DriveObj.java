package com.yichang.kaku.obj;

import java.io.Serializable;

public class DriveObj implements Serializable {
	
	private String id_driver;
	private String name_driver;
	private String flag_freeze;
	private String sid;

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

	public String getFlag_freeze() {
		return flag_freeze;
	}

	public void setFlag_freeze(String flag_freeze) {
		this.flag_freeze = flag_freeze;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "DriveObj{" +
				"id_driver='" + id_driver + '\'' +
				", name_driver='" + name_driver + '\'' +
				", flag_freeze='" + flag_freeze + '\'' +
				", sid='" + sid + '\'' +
				'}';
	}
}
