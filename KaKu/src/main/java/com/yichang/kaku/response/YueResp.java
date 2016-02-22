package com.yichang.kaku.response;

import com.yichang.kaku.obj.YueObj;

import java.io.Serializable;
import java.util.List;

public class YueResp extends BaseResp implements Serializable {
	public List<YueObj> moneys;
    public String money_income;
    public String money_deposit;
    public String money_balance;
    public String pass_exist;

}
