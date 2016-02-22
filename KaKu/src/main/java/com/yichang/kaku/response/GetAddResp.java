package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddObj;
import com.yichang.kaku.obj.RollsAddObj;

import java.io.Serializable;
import java.util.List;

public class GetAddResp extends BaseResp implements Serializable {
	public AddObj advert;
	public List<RollsAddObj> rolls;

	public String flag_show;
}
