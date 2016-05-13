package com.yichang.kaku.response;

import com.yichang.kaku.obj.Worker2Obj;

import java.io.Serializable;

public class YellowOilOrderResp extends BaseResp implements Serializable {

    public String id_upkeep_bill;
    public String price_goods;
    public String money_balance;
    public String flag_pay;
    public String no_bill;
    public Worker2Obj worker;
}
