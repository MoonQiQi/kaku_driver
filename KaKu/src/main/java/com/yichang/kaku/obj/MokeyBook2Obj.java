package com.yichang.kaku.obj;

import java.io.Serializable;

public class MokeyBook2Obj implements Serializable {

    private String flag_type;
    private String flag_way;
    private String date_record;
    private String date_remark;
    private String money_account;
    private String remark_account;

    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
    }

    public String getFlag_way() {
        return flag_way;
    }

    public void setFlag_way(String flag_way) {
        this.flag_way = flag_way;
    }

    public String getDate_record() {
        return date_record;
    }

    public void setDate_record(String date_record) {
        this.date_record = date_record;
    }

    public String getDate_remark() {
        return date_remark;
    }

    public void setDate_remark(String date_remark) {
        this.date_remark = date_remark;
    }

    public String getMoney_account() {
        return money_account;
    }

    public void setMoney_account(String money_account) {
        this.money_account = money_account;
    }

    public String getRemark_account() {
        return remark_account;
    }

    public void setRemark_account(String remark_account) {
        this.remark_account = remark_account;
    }

    @Override
    public String toString() {
        return "MokeyBook2Obj{" +
                "flag_type='" + flag_type + '\'' +
                ", flag_way='" + flag_way + '\'' +
                ", date_record='" + date_record + '\'' +
                ", date_remark='" + date_remark + '\'' +
                ", money_account='" + money_account + '\'' +
                ", remark_account='" + remark_account + '\'' +
                '}';
    }
}
