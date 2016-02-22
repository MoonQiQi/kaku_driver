package com.yichang.kaku.response;

import com.yichang.kaku.obj.PinPaiXuanZeObj;

import java.io.Serializable;
import java.util.List;

public class PinPaiXuanZeResp extends BaseResp implements Serializable {
	public List<PinPaiXuanZeObj> brands;
	public List<PinPaiXuanZeObj> brands_hot;
}
