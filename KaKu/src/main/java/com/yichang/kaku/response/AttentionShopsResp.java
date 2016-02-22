package com.yichang.kaku.response;

import com.yichang.kaku.obj.AttentionShopObj;
import com.yichang.kaku.obj.PointHistoryObj;

import java.io.Serializable;
import java.util.List;

public class AttentionShopsResp extends BaseResp implements Serializable {

	public List<AttentionShopObj> shops;

}
