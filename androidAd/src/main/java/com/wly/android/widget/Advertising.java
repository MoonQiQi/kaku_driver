package com.wly.android.widget;

/**
 * 广告实体类
 * @author zl
 * @date 2014-9-23
 *
 */
public class Advertising {

	private String picURL; //图片地址
	private String id; //单击跳转地址
	private String title; //标题
	
	public Advertising(String picURL, String id, String title) {
		super();
		this.picURL = picURL;
		this.id = id;
		this.title = title;
	}

	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
