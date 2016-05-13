package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class ChePinAdapter1Obj implements Serializable {

    private String name_type;
    private String flag_root;
    private String id_goods_type;
    private String color_type;
    private List<ChePinAdapter2Obj> list;

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getFlag_root() {
        return flag_root;
    }

    public void setFlag_root(String flag_root) {
        this.flag_root = flag_root;
    }

    public List<ChePinAdapter2Obj> getList() {
        return list;
    }

    public void setList(List<ChePinAdapter2Obj> list) {
        this.list = list;
    }

    public String getId_goods_type() {
        return id_goods_type;
    }

    public void setId_goods_type(String id_goods_type) {
        this.id_goods_type = id_goods_type;
    }

    public String getColor_type() {
        return color_type;
    }

    public void setColor_type(String color_type) {
        this.color_type = color_type;
    }

    @Override
    public String toString() {
        return "ChePinAdapter1Obj{" +
                "name_type='" + name_type + '\'' +
                ", flag_root='" + flag_root + '\'' +
                ", id_goods_type='" + id_goods_type + '\'' +
                ", color_type='" + color_type + '\'' +
                ", list=" + list +
                '}';
    }
}
