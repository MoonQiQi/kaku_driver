package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class MokeyBook1Obj implements Serializable {
    private String date_remark;
    private List<MokeyBook2Obj> data_list;

    public String getDate_remark() {
        return date_remark;
    }

    public void setDate_remark(String date_remark) {
        this.date_remark = date_remark;
    }

    public List<MokeyBook2Obj> getData_list() {
        return data_list;
    }

    public void setData_list(List<MokeyBook2Obj> data_list) {
        this.data_list = data_list;
    }
}
