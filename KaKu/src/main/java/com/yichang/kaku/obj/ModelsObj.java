package com.yichang.kaku.obj;

import java.io.Serializable;

public class ModelsObj implements Serializable {
	
	private String id_model;
	private String name_model;
	private String can_check;

	public String getId_model() {
		return id_model;
	}

	public void setId_model(String id_model) {
		this.id_model = id_model;
	}

	public String getName_model() {
		return name_model;
	}

	public void setName_model(String name_model) {
		this.name_model = name_model;
	}

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	@Override
	public String toString() {
		return "ModelsObj{" +
				"id_model='" + id_model + '\'' +
				", name_model='" + name_model + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
