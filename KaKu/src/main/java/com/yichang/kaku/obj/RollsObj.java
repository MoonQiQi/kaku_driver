package com.yichang.kaku.obj;

import java.io.Serializable;

public class RollsObj implements Serializable {
	
	private String image_roll;
	private String url_roll;
	private String name_roll;

	public String getImage_roll() {
		return image_roll;
	}

	public void setImage_roll(String image_roll) {
		this.image_roll = image_roll;
	}

	public String getUrl_roll() {
		return url_roll;
	}

	public void setUrl_roll(String url_roll) {
		this.url_roll = url_roll;
	}

	public String getName_roll() {
		return name_roll;
	}

	public void setName_roll(String name_roll) {
		this.name_roll = name_roll;
	}

	@Override
	public String toString() {
		return "RollsObj{" +
				"image_roll='" + image_roll + '\'' +
				", url_roll='" + url_roll + '\'' +
				", name_roll='" + name_roll + '\'' +
				'}';
	}
}
