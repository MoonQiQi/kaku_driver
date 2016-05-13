package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddObj;
import com.yichang.kaku.obj.CheTiePingJiaObj;

import java.io.Serializable;
import java.util.List;

public class GetAddResp extends BaseResp implements Serializable {
	public AddObj advert;
	public List<CheTiePingJiaObj> evals;
	public List<WeekResp> calendars;
	public String index_x;
	public String index_y;

}
