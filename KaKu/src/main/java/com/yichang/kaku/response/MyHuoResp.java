package com.yichang.kaku.response;

import com.yichang.kaku.obj.MyHuoObj;

import java.io.Serializable;
import java.util.List;

public class MyHuoResp extends BaseResp implements Serializable {
	public List<MyHuoObj> supplys;
}
