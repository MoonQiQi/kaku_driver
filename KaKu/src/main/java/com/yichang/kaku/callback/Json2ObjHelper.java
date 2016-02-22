package com.yichang.kaku.callback;

import com.google.gson.Gson;
public class Json2ObjHelper {
    public static Gson gson = new Gson();

    public static final <T> T newInstance(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}