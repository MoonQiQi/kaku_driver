package com.yichang.kaku.request;

import java.io.Serializable;

public class UploadImageReq extends BaseReq implements Serializable {
	public String id_advert;
	public String id_driver;
	public String image0_advert;
	public String image1_advert;
	public String image2_advert;
	public String var_lon;
	public String var_lat;
}
