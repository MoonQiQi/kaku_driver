package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class WeiZhangResultObj implements Serializable {

    public String province_code;
    public String province;
    public List<WeiZhangCityObj> citys;

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<WeiZhangCityObj> getCitys() {
        return citys;
    }

    public void setCitys(List<WeiZhangCityObj> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return "WeiZhangResultObj{" +
                "province_code='" + province_code + '\'' +
                ", province='" + province + '\'' +
                ", citys=" + citys +
                '}';
    }
}
