package com.yichang.kaku.response;

import com.yichang.kaku.obj.Shops_wxzObj;

import java.io.Serializable;
import java.util.List;

public class ChooseShopResp extends BaseResp implements Serializable {
    public List<Shops_wxzObj> shops;
    public String total_num;
}
