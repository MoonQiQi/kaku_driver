package com.yichang.kaku.obj;

import java.io.Serializable;

public class CallMoreObj implements Serializable {

    private String call_duration;
    private String time_create;
    private String remark_duration;

    public String getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(String call_duration) {
        this.call_duration = call_duration;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getRemark_duration() {
        return remark_duration;
    }

    public void setRemark_duration(String remark_duration) {
        this.remark_duration = remark_duration;
    }

    @Override
    public String toString() {
        return "CallMoreObj{" +
                "call_duration='" + call_duration + '\'' +
                ", time_create='" + time_create + '\'' +
                ", remark_duration='" + remark_duration + '\'' +
                '}';
    }
}
