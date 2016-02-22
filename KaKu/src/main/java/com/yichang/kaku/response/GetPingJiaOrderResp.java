package com.yichang.kaku.response;

import com.yichang.kaku.obj.WuLiaoObj;

import java.io.Serializable;
import java.util.List;

public class GetPingJiaOrderResp extends BaseResp implements Serializable {
	public List<WuLiaoObj> items;
}
