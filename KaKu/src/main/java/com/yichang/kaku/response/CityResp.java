package com.yichang.kaku.response;

import com.yichang.kaku.tools.PinyinUtil;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/24.
 */
public class CityResp extends BaseResp {

    /**
     * id_area : 11
     * name_area : 北京市
     * list : [{"id_area":"1101","name_area":"北京市辖区"}]
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String id_area;
        public String name_area;
        /**
         * id_area : 1101
         * name_area : 北京市辖区
         */

        public List<InnerListEntity> list;

        public static class InnerListEntity implements Comparable<InnerListEntity> {
            public String id_area;
            public String name_area;
            public int type = 0;

            @Override
            public int compareTo(InnerListEntity another) {
                return this.getPinyin().compareToIgnoreCase(another.getPinyin());
            }

            public String getPinyin() {
                return PinyinUtil.getPinyin(name_area);
            }
        }
    }
}
