package com.yichang.kaku.obj;

import java.io.Serializable;

public class ImageHisObj implements Serializable {

	private String id_driver_advert;
	private String flag_type;
	private String flag_position;
	private String flag_show;
	private String flag_type_shoot;
	private String upload_time;
	private String create_time;
	private String approve_time;
	private String approve_opinions;
	private String image0_advert;
	private String image1_advert;
	private String image2_advert;
	private String image0_approve;
	private String image1_approve;
	private String image2_approve;
	private String isJia = "Y";

	public String getId_driver_advert() {
		return id_driver_advert;
	}

	public void setId_driver_advert(String id_driver_advert) {
		this.id_driver_advert = id_driver_advert;
	}

	public String getFlag_type() {
		return flag_type;
	}

	public void setFlag_type(String flag_type) {
		this.flag_type = flag_type;
	}

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_opinions() {
		return approve_opinions;
	}

	public void setApprove_opinions(String approve_opinions) {
		this.approve_opinions = approve_opinions;
	}

	public String getImage0_advert() {
		return image0_advert;
	}

	public void setImage0_advert(String image0_advert) {
		this.image0_advert = image0_advert;
	}

	public String getImage1_advert() {
		return image1_advert;
	}

	public void setImage1_advert(String image1_advert) {
		this.image1_advert = image1_advert;
	}

	public String getImage2_advert() {
		return image2_advert;
	}

	public void setImage2_advert(String image2_advert) {
		this.image2_advert = image2_advert;
	}

	public String getImage0_approve() {
		return image0_approve;
	}

	public void setImage0_approve(String image0_approve) {
		this.image0_approve = image0_approve;
	}

	public String getImage1_approve() {
		return image1_approve;
	}

	public void setImage1_approve(String image1_approve) {
		this.image1_approve = image1_approve;
	}

	public String getImage2_approve() {
		return image2_approve;
	}

	public void setImage2_approve(String image2_approve) {
		this.image2_approve = image2_approve;
	}

	public String getFlag_position() {
		return flag_position;
	}

	public void setFlag_position(String flag_position) {
		this.flag_position = flag_position;
	}

	public String getFlag_show() {
		return flag_show;
	}

	public void setFlag_show(String flag_show) {
		this.flag_show = flag_show;
	}

	public String getFlag_type_shoot() {
		return flag_type_shoot;
	}

	public void setFlag_type_shoot(String flag_type_shoot) {
		this.flag_type_shoot = flag_type_shoot;
	}

	public String getIsJia() {
		return isJia;
	}

	public void setIsJia(String isJia) {
		this.isJia = isJia;
	}

	@Override
	public String toString() {
		return "ImageHisObj{" +
				"id_driver_advert='" + id_driver_advert + '\'' +
				", flag_type='" + flag_type + '\'' +
				", flag_position='" + flag_position + '\'' +
				", flag_show='" + flag_show + '\'' +
				", flag_type_shoot='" + flag_type_shoot + '\'' +
				", upload_time='" + upload_time + '\'' +
				", create_time='" + create_time + '\'' +
				", approve_time='" + approve_time + '\'' +
				", approve_opinions='" + approve_opinions + '\'' +
				", image0_advert='" + image0_advert + '\'' +
				", image1_advert='" + image1_advert + '\'' +
				", image2_advert='" + image2_advert + '\'' +
				", image0_approve='" + image0_approve + '\'' +
				", image1_approve='" + image1_approve + '\'' +
				", image2_approve='" + image2_approve + '\'' +
				", isJia='" + isJia + '\'' +
				'}';
	}
}
