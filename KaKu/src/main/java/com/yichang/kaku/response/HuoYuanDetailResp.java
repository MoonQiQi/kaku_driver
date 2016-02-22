package com.yichang.kaku.response;

import com.yichang.kaku.obj.HuoYuanDetailObj;
import com.yichang.kaku.obj.RollsObj;

import java.io.Serializable;
import java.util.List;

public class HuoYuanDetailResp extends BaseResp implements Serializable {
	public HuoYuanDetailObj supply;
	public List<RollsObj> rolls;
}
