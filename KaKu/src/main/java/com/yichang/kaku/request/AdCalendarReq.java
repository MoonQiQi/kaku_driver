package com.yichang.kaku.request;

import java.io.Serializable;

public class AdCalendarReq extends BaseReq implements Serializable {
    public String id_driver;
    public String id_advert;
    public String month;
    public String year;
}
