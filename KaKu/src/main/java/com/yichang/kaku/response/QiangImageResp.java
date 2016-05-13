package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddrObj;

import java.io.Serializable;

public class QiangImageResp extends BaseResp implements Serializable {
	public AddrObj addr;
    public String flag_one;
    public String money_balance;
    public String breaks_money;
    public String price_advert;

}
