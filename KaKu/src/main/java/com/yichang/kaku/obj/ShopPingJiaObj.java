package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopPingJiaObj implements Serializable {
	
	private String name_driver;
	private String evaluation_order;
	private String time_evaluation;
	private String star_order;
	private String image_evaluation1;
	private String image_evaluation2;
	private String image_evaluation3;
	private String image_evaluation4;

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getEvaluation_order() {
		return evaluation_order;
	}

	public void setEvaluation_order(String evaluation_order) {
		this.evaluation_order = evaluation_order;
	}

	public String getTime_evaluation() {
		return time_evaluation;
	}

	public void setTime_evaluation(String time_evaluation) {
		this.time_evaluation = time_evaluation;
	}

	public String getStar_order() {
		return star_order;
	}

	public void setStar_order(String star_order) {
		this.star_order = star_order;
	}

	public String getImage_evaluation1() {
		return image_evaluation1;
	}

	public void setImage_evaluation1(String image_evaluation1) {
		this.image_evaluation1 = image_evaluation1;
	}

	public String getImage_evaluation2() {
		return image_evaluation2;
	}

	public void setImage_evaluation2(String image_evaluation2) {
		this.image_evaluation2 = image_evaluation2;
	}

	public String getImage_evaluation3() {
		return image_evaluation3;
	}

	public void setImage_evaluation3(String image_evaluation3) {
		this.image_evaluation3 = image_evaluation3;
	}

	public String getImage_evaluation4() {
		return image_evaluation4;
	}

	public void setImage_evaluation4(String image_evaluation4) {
		this.image_evaluation4 = image_evaluation4;
	}

	@Override
	public String toString() {
		return "ShopPingJiaObj{" +
				"name_driver='" + name_driver + '\'' +
				", evaluation_order='" + evaluation_order + '\'' +
				", time_evaluation='" + time_evaluation + '\'' +
				", star_order='" + star_order + '\'' +
				", image_evaluation1='" + image_evaluation1 + '\'' +
				", image_evaluation2='" + image_evaluation2 + '\'' +
				", image_evaluation3='" + image_evaluation3 + '\'' +
				", image_evaluation4='" + image_evaluation4 + '\'' +
				'}';
	}
}
