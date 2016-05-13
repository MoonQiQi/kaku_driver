package com.yichang.kaku.response;

import com.yichang.kaku.obj.BaoYangObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangDetailResp extends BaseResp implements Serializable {
	public List<BaoYangObj> upkeeps;
	public BaoYangObj upkeep;
}
