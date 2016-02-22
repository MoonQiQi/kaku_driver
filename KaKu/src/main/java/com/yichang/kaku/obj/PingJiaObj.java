package com.yichang.kaku.obj;

import java.io.Serializable;

public class PingJiaObj implements Serializable {
	
	private String name_driver;
	private String content_eval;
	private String time_eval;
	private String star_eval;
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

	public String getContent_eval() {
		return content_eval;
	}

	public void setContent_eval(String content_eval) {
		this.content_eval = content_eval;
	}

	public String getTime_eval() {
		return time_eval;
	}

	public void setTime_eval(String time_eval) {
		this.time_eval = time_eval;
	}

	public String getStar_eval() {
		return star_eval;
	}

	public void setStar_eval(String star_eval) {
		this.star_eval = star_eval;
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
		return "PingJiaObj{" +
				"name_driver='" + name_driver + '\'' +
				", content_eval='" + content_eval + '\'' +
				", time_eval='" + time_eval + '\'' +
				", star_eval='" + star_eval + '\'' +
				", image1_eval='" + image1_eval + '\'' +
				", image2_eval='" + image2_eval + '\'' +
				", image3_eval='" + image3_eval + '\'' +
				", image4_eval='" + image4_eval + '\'' +
				'}';
	}
}
