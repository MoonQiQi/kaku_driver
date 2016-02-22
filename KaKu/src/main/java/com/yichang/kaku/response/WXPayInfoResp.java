package com.yichang.kaku.response;

import java.io.Serializable;

public class WXPayInfoResp extends BaseResp implements Serializable {
    //public String no_order;
    public String appid;
    public String noncestr;
    public String package0;
    public String partnerid;
    public String prepay_id;
    public String timestamp;
    public String sign;
	
}
