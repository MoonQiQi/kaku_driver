package com.yichang.kaku.response;

import java.io.Serializable;
import java.util.List;

public class BigCalendarResp extends BaseResp implements Serializable {
    public List<WeekResp> calendars;
    public String index_y;
}
