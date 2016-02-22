
package com.yichang.kaku.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */

public  class BankNameUtil {

    private static List<String> bankNameList = new ArrayList<>();

    public static List<String> getBankNameList() {
        for (String name : bankName) {
            bankNameList.add(name);
        }
        if (bankNameList != null) {
            return bankNameList;
        }
        return null;
    }

        //"发卡行.卡种名称",
        private static final String[] bankName = {

                "中国银行",
                "中国农业银行",
                "中国建设银行",
                "中国工商银行",
                "中国邮政储蓄银行",
                "中国民生银行",
                "中国光大银行",
                "中信银行",
                "招商银行",
                "兴业银行",
                "上海浦东发展银行",
                "平安银行",
                "交通银行",
                "华夏银行",
                "广发银行"
        };

   /* //"发卡行.卡种名称",
    private static final String[] bankName = {
            "中国工商银行",
            "中国银行",
            "中国建设银行",
            "上海浦东发展银行",
            "招商银行",
            "中信银行",
            "中国光大银行",
            "中国民生银行",
            "广发银行",
            "交通银行",
            "平安银行",
            "兴业银行",
            "上海银行",
            "中国农业银行",
            "中国邮政储蓄银行",
            "华夏银行",
            "北京银行",
            "江苏银行",
            "龙江银行",
            "宁波银行",
            "长沙银行",
            "徽商银行",
            "浙商银行",
            "汉口银行",
            "北京农商银行",
            "福建省农村信用社联合社",
            "哈尔滨银行",
            "宁夏银行",
            "重庆农村商业银行",
            "大连银行",
            "江苏省农村信用社联合社",
            "南京银行",
            "包商银行",
            "常熟农商银行",
            "广州银行",
            "河北银行",
            "吉林银行",
            "青海银行",
            "台州银行",
            "乌鲁木齐市商业银行",
            "重庆银行",
            "广州农商银行",
            "杭州银行",
            "兰州银行",
            "柳州银行",
            "宁波鄞州农村合作银行",
            "青岛银行",
            "厦门银行",
            "上海农商银行",
            "绍兴银行",
            "顺德农商银行",
            "天津银行",
            "浙江民泰商业银行",
            "沧州银行",
            "东莞农村商业银行",
            "东莞银行",
            "福建海峡银行",
            "贵阳银行",
            "湖北省农村信用社联合社",
            "南昌银行",
            "天津农商银行",
            "温州银行",
            "浙江泰隆商业银行",
            "安徽省农村信用社联合社",
            "渤海银行",
            "德阳银行",
            "赣州银行",
            "海南省农村信用社联合社",
            "锦州银行",
            "晋商银行",
            "商丘银行",
            "上饶银行",
            "深圳农村商业银行",
            "苏州银行",
            "潍坊银行",
            "吴江农村商业银行",
            "郑州银行",
            "鞍山银行",
            "承德银行",
            "德州银行",
            "东营银行",
            "鄂尔多斯银行",
            "广西北部湾银行",
            "恒丰银行",
            "葫芦岛银行",
            "湖州银行",
            "黄河农村商业银行",
            "昆仑银行",
            "昆山农村商业银行",
            "辽阳银行",
            "洛阳银行",
            "漯河银行",
            "内蒙古银行",
            "齐商银行",
            "日照银行",
            "泰安市商业银行",
            "威海市商业银行",
            "邢台银行",
            "云南省农村信用社联合社",
            "张家港农村商业银行",
            "张家口市商业银行",
            "浙江稠州商业银行",
            "阜新银行",
            "富滇银行",
            "广西农村信用社联合社",
            "邯郸银行",
            "韩亚银行(中国)",
            "济宁银行",
            "晋城银行",
            "莱商银行",
            "临商银行",
            "绵阳市商业银行",
            "南阳银行",
            "攀枝花市商业银行",
            "企业银行（中国）",
            "新韩银行",
            "烟台银行",
            "营口银行",
            "高要农村信用社",
            "佛山市禅城区农村信用合作联社",
            "珠海农商银行",
            "新会农村商业银行",
            "中山市农村信用合作联社",
            ","
    };*/

        /*public static String getNameOfBank(char[] charBin, int offset) {
                long longBin = 0;

                for (int i = 0; i < 6; i++) {
                        longBin = (longBin * 10) + (charBin[i + offset] - 48);
                }

                int index = binarySearch(bankBin, longBin);

                if (index == -1) {
                        return "磁条卡卡号:\n";
                }
                return bankName[index] + ":\n";

        }


        //二分查找方法
        public static int binarySearch(long[] srcArray, long des) {
                int low = 0;
                int high = srcArray.length - 1;
                while (low <= high) {
                        int middle = (low + high) / 2;
                        if (des == srcArray[middle]) {
                                return middle;
                        } else if (des < srcArray[middle]) {
                                high = middle - 1;
                        } else {
                                low = middle + 1;
                        }
                }
                return -1;
        }*/

}

