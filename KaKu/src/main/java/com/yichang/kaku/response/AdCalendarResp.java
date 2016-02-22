package com.yichang.kaku.response;

import com.yichang.kaku.obj.CalendarObj;

import java.io.Serializable;
import java.util.List;

public class AdCalendarResp extends BaseResp implements Serializable {
	public List<CalendarObj> calendars;
	public String start_photo;
}
