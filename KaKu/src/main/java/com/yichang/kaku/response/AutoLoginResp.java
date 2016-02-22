package com.yichang.kaku.response;

import com.yichang.kaku.obj.DriveObj;

import java.io.Serializable;

public class AutoLoginResp extends BaseResp implements Serializable {
	public DriveObj driver;
	public String id_car;
	public String flag_show;
	public String title;
	public String content;
	public String url;
}
