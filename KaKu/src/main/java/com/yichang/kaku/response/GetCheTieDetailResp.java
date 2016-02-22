package com.yichang.kaku.response;

import com.yichang.kaku.obj.Addr2Obj;
import com.yichang.kaku.obj.CheTieDetailObj;

import java.io.Serializable;

public class GetCheTieDetailResp extends BaseResp implements Serializable {
    public CheTieDetailObj advert;
    public Addr2Obj addr;
    public String flag_reco;
    public String flag_one;
    public String flag_advert;
    public String money_balance;
    public String customer_tel;
    public String flag_pay;
}
