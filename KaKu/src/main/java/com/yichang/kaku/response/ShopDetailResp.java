package com.yichang.kaku.response;

import com.yichang.kaku.obj.EvalObj;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.obj.ShopDetailObj;

import java.io.Serializable;
import java.util.List;

public class ShopDetailResp extends BaseResp implements Serializable {
	public ShopDetailObj shop;
    public EvalObj eval;
    public List<RollsObj> rolls;
}
