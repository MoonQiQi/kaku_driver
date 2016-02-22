package com.yichang.kaku.response;

import com.yichang.kaku.obj.Addr2Obj;

import java.io.Serializable;

public class QiangImageResp extends BaseResp implements Serializable {
	public Addr2Obj addr;
    public String flag_one;
    public String money_balance;
    public String breaks_money;
    public String price_advert;

}
