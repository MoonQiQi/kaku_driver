package com.yichang.kaku.obj;

import java.io.Serializable;

public class AreaObj implements Serializable {

	private String id_area;
	private String name_area;
	private String id_sup;

	public String getId_area() {
		return id_area;
	}

	public void setId_area(String id_area) {
		this.id_area = id_area;
	}

	public String getName_area() {
		return name_area;
	}

	public void setName_area(String name_area) {
		this.name_area = name_area;
	}

	public String getId_sup() {
		return id_sup;
	}

	public void setId_sup(String id_sup) {
		this.id_sup = id_sup;
	}

	@Override
	public String toString() {
		return "AreaObj{" +
				"id_area='" + id_area + '\'' +
				", name_area='" + name_area + '\'' +
				", id_sup='" + id_sup + '\'' +
				'}';
	}
}
