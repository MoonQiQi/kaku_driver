package com.yichang.kaku.response;

import com.yichang.kaku.obj.ImageHisObj;

import java.io.Serializable;
import java.util.List;

public class ImageHisResp extends BaseResp implements Serializable {
	public List<ImageHisObj> driver_advert;
	public String num_privilege;
}
