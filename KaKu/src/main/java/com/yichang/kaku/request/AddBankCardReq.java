package com.yichang.kaku.request;

import java.io.Serializable;

public class AddBankCardReq extends BaseReq implements Serializable {
    public String card_bank;//卡号
    public String name_bank;//银行名称
    public String area_bank;//开户行所属城市
    public String driver_bank;//  开户人姓名

}
