package com.yichang.kaku.obj;

import java.io.Serializable;

public class DropObj implements Serializable {
	
	private String id_drop;
	private String name_drop;
	private String flag_check;

	public String getId_drop() {
		return id_drop;
	}

	public void setId_drop(String id_drop) {
		this.id_drop = id_drop;
	}

	public String getName_drop() {
		return name_drop;
	}

	public void setName_drop(String name_drop) {
		this.name_drop = name_drop;
	}

	public String getFlag_check() {
		return flag_check;
	}

	public void setFlag_check(String flag_check) {
		this.flag_check = flag_check;
	}

	@Override
	public String toString() {
		return "DropObj{" +
				"id_drop='" + id_drop + '\'' +
				", name_drop='" + name_drop + '\'' +
				", flag_check='" + flag_check + '\'' +
				'}';
	}
}
