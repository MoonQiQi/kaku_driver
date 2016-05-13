package com.yichang.kaku.response;

import com.yichang.kaku.obj.MyCarObj;

import java.io.Serializable;
import java.util.List;

public class MyCarResp extends BaseResp implements Serializable {
	public List<MyCarObj> driver_cars;
}
