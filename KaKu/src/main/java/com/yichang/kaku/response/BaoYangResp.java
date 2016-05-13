package com.yichang.kaku.response;

import com.yichang.kaku.obj.BaoYangCarObj;
import com.yichang.kaku.obj.BaoYangItemObj;
import com.yichang.kaku.obj.BaoYangShopObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangResp extends BaseResp implements Serializable {
    public BaoYangCarObj car;
    public BaoYangItemObj item;
    public List<BaoYangShopObj> shops;
}
