package com.yichang.kaku.response;

import com.yichang.kaku.obj.RecommendedsObj;

import java.io.Serializable;
import java.util.List;

public class JiangLiMingXiResp extends BaseResp implements Serializable {
	public List<RecommendedsObj> recommendeds;
    public String points;
    //准现金
    public String money_earnings;
    //现金
    public String money_balance;
}
