package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopPingJiaObj implements Serializable {
	
	private String name_driver;
	private String evaluation_order;
	private String time_evaluation;
	private String star_order;
	private String image1_eval;
	private String image2_eval;
	private String image3_eval;
	private String image4_eval;

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

	public String getImage1_eval() {
		return image1_eval;
	}

	public void setImage1_eval(String image1_eval) {
		this.image1_eval = image1_eval;
	}

	public String getImage2_eval() {
		return image2_eval;
	}

	public void setImage2_eval(String image2_eval) {
		this.image2_eval = image2_eval;
	}

	public String getImage3_eval() {
		return image3_eval;
	}

	public void setImage3_eval(String image3_eval) {
		this.image3_eval = image3_eval;
	}

	public String getImage4_eval() {
		return image4_eval;
	}

	public void setImage4_eval(String image4_eval) {
		this.image4_eval = image4_eval;
	}

	@Override
	public String toString() {
		return "ShopPingJiaObj{" +
				"name_driver='" + name_driver + '\'' +
				", evaluation_order='" + evaluation_order + '\'' +
				", time_evaluation='" + time_evaluation + '\'' +
				", star_order='" + star_order + '\'' +
				", image1_eval='" + image1_eval + '\'' +
				", image2_eval='" + image2_eval + '\'' +
				", image3_eval='" + image3_eval + '\'' +
				", image4_eval='" + image4_eval + '\'' +
				'}';
	}
}
