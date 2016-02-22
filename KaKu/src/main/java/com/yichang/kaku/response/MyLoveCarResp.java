package com.yichang.kaku.response;

import com.yichang.kaku.obj.MyLoveCarObj;

import java.io.Serializable;
import java.util.List;

public class MyLoveCarResp extends BaseResp implements Serializable {
	public List<MyLoveCarObj>  driver_cars;
}
