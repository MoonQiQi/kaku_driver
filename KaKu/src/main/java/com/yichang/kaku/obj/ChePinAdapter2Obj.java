package com.yichang.kaku.obj;

import java.io.Serializable;

public class ChePinAdapter2Obj implements Serializable {
	
	private String name_type;
	private String id_goods_type;
	private String color_type;

	public String getName_type() {
		return name_type;
	}

	public void setName_type(String name_type) {
		this.name_type = name_type;
	}

	public String getId_goods_type() {
		return id_goods_type;
	}

	public void setId_goods_type(String id_goods_type) {
		this.id_goods_type = id_goods_type;
	}

	public String getColor_type() {
		return color_type;
	}

	public void setColor_type(String color_type) {
		this.color_type = color_type;
	}

	@Override
	public String toString() {
		return "ChePinAdapter2Obj{" +
				"name_type='" + name_type + '\'' +
				", id_goods_type='" + id_goods_type + '\'' +
				", color_type='" + color_type + '\'' +
				'}';
	}
}
