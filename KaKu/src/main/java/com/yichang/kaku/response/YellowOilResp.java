package com.yichang.kaku.response;

import com.yichang.kaku.obj.Worker2Obj;
import com.yichang.kaku.obj.YellowOilObj;

import java.io.Serializable;
import java.util.List;

public class YellowOilResp extends BaseResp implements Serializable {

    public String flag_state;
    public String id_upkeep_bill;
    public Worker2Obj worker;
    public String price_goods;
    public String var_lat0;
    public String var_lon0;
    public String price_coupon;
    public List<YellowOilObj> services;

}
