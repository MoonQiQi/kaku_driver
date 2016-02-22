package com.yichang.kaku.response;

import com.yichang.kaku.obj.OilBrandObj;

import java.io.Serializable;
import java.util.List;

public class OilBrandResp extends BaseResp implements Serializable {
	public List<OilBrandObj> brands;
}
