package com.yichang.kaku.response;

import com.yichang.kaku.obj.BigOilObj;
import com.yichang.kaku.obj.BrandBigOilObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangChangeListResp extends BaseResp implements Serializable {
    public List<BigOilObj> items;
    public List<BrandBigOilObj> brands;
}
