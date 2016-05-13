package com.yichang.kaku.request;

import java.io.Serializable;

public class WhiteMoneyReq extends BaseReq implements Serializable {
    public String flag_type;
    public String flag_way;
    public String date_record;
    public String remark_account;
    public String money_account;
}
