package com.yichang.kaku.obj;

import java.io.Serializable;

public class CallListObj implements Serializable {

    private String call_duration;
    private String time_create;
    private String phone_people;

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

    public String getPhone_people() {
        return phone_people;
    }

    public void setPhone_people(String phone_people) {
        this.phone_people = phone_people;
    }

    @Override
    public String toString() {
        return "CallListObj{" +
                "call_duration='" + call_duration + '\'' +
                ", time_create='" + time_create + '\'' +
                ", phone_people='" + phone_people + '\'' +
                '}';
    }
}
