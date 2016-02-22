package com.yichang.kaku.response;

import com.yichang.kaku.obj.CarListObj;

import java.io.Serializable;
import java.util.List;

public class CarListResp extends BaseResp implements Serializable {
	public List<CarListObj> cars;
}
