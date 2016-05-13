package com.yichang.kaku.response;

import com.yichang.kaku.obj.MokeyBook1Obj;

import java.io.Serializable;
import java.util.List;

public class MoneyBookResp extends BaseResp implements Serializable {
    public String year;
    public String month;
    public String get_total;
    public String pay_total;
    public List<MokeyBook1Obj> account;


}
