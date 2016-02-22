package com.yichang.kaku.tools.okhttp;

import java.util.HashMap;

/**
 * Created by 小苏 on 2015/10/22.
 */
public class Params extends HashMap<String, String> {

    private Params() {
    }

    public static class builder {

        private Params p;

        public builder() {
            p = new Params();
        }

        public builder p(String key, String value) {
            return p(key, value, null);
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         * @param nullTip 空串的提醒，如果该参数不为空，提交请求的时候发现该参数为空。就中断请求并发出提示
         * @return
         */
        public builder p(String key, String value, String nullTip) {
            p.put((null != nullTip && "".equals(nullTip)) ? key + "[" + nullTip + "]" : key, value);
            return this;
        }

        public Params build() {
            return p;
        }
    }
}
