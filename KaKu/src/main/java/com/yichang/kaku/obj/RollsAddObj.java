package com.yichang.kaku.obj;

import java.io.Serializable;

public class RollsAddObj implements Serializable {
	
	private String image_roll;

	public String getImage_roll() {
		return image_roll;
	}

	public void setImage_roll(String image_roll) {
		this.image_roll = image_roll;
	}

	@Override
	public String toString() {
		return "RollsAddObj{" +
				"image_roll='" + image_roll + '\'' +
				'}';
	}
}
