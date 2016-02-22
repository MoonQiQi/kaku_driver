package com.yichang.kaku.obj;

import java.io.Serializable;

public class SeriessObj implements Serializable {
	
	private String id_data;
	private String name_data;
	private String can_check;

	public String getId_data() {
		return id_data;
	}

	public void setId_data(String id_data) {
		this.id_data = id_data;
	}

	public String getName_data() {
		return name_data;
	}

	public void setName_data(String name_data) {
		this.name_data = name_data;
	}

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	@Override
	public String toString() {
		return "SeriessObj{" +
				"id_data='" + id_data + '\'' +
				", name_data='" + name_data + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
