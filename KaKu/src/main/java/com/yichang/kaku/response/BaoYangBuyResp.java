package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.obj.BaoYangObj;

import java.io.Serializable;

public class BaoYangBuyResp extends BaseResp implements Serializable {
	public AddrObj addr;
    public BaoYangObj upkeep;
    public String price_bill;
    public String money_balance;
    public String flag_pay;
}
