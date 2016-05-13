package com.yichang.kaku.response;

import com.yichang.kaku.obj.TuiJianObj;

import java.io.Serializable;
import java.util.List;

public class TuiJianResp extends BaseResp implements Serializable {
    public String content;
    public String url;
    public String url0;
    public String money;
    public String point;
    public String total_money;
    public String code_recommended;
    public List<TuiJianObj> recommendeds;
}
