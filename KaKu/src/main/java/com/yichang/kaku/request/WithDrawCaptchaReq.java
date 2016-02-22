package com.yichang.kaku.request;

import java.io.Serializable;

public class WithDrawCaptchaReq extends BaseReq implements Serializable {
    public String id_driver;
    public String sign;//卡号
}
