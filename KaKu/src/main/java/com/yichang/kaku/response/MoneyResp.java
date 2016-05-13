package com.yichang.kaku.response;

import com.yichang.kaku.obj.MoneyObj;
import com.yichang.kaku.obj.MoneyRankObj;

import java.io.Serializable;
import java.util.List;

public class MoneyResp extends BaseResp implements Serializable {
    public String content;
    public String url;
    public String title;
    public MoneyObj driver;
    public List<MoneyRankObj> drivers;

}
