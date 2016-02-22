package com.yichang.kaku.obj;

import java.io.Serializable;

public class ActuatesObj implements Serializable {
	
	private String id_actuate;
	private String name_actuate;
	private String can_check;

	public String getId_actuate() {
		return id_actuate;
	}

	public void setId_actuate(String id_actuate) {
		this.id_actuate = id_actuate;
	}

	public String getName_actuate() {
		return name_actuate;
	}

	public void setName_actuate(String name_actuate) {
		this.name_actuate = name_actuate;
	}

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	@Override
	public String toString() {
		return "ActuatesObj{" +
				"id_actuate='" + id_actuate + '\'' +
				", name_actuate='" + name_actuate + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
