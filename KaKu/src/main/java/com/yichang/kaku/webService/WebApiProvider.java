package com.yichang.kaku.webService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yichang.kaku.callback.Json2ObjHelper;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.LogUtil;

import android.content.Context;


public abstract class WebApiProvider {

    private static final AsyncHttpClient httpClient = new AsyncHttpClient();

    static {
        httpClient.setMaxRetriesAndTimeout(3, 30 * 1000);
    }

    private static AsyncHttpClient getHttpClient() {
        return httpClient;
    }

    private static RequestParams getParams(final boolean isValiToken) {
        final RequestParams params = new RequestParams();
        if (isValiToken) {
            params.put("id_user", KaKuApplication.id_user);
        }
        return params;
    }

    private final static RequestParams getParams() {
        return getParams(true);
    }
    

    private final static RequestParams getParamsWithoutToken() {
        return getParams(false);
    }

    //GET
    private final static void getRequest(final Context context, final String url,
                                         final boolean isValiToken, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient httpClient = getHttpClient();
        if (httpClient != null) {
            httpClient.get(url, params, responseHandler);
        }
    }

    private final static void getRequest(final String url, final RequestParams params,
                                         final AsyncHttpResponseHandler responseHandler) {
        getRequest(null, url, false, params, responseHandler);
    }

    private final static void getRequest(final String url, final boolean isValiToken,
                                         final RequestParams params,
                                         final AsyncHttpResponseHandler resp) {
        getRequest(null, url, isValiToken, params, resp);
    }

    protected static final void getRequest(final Object requestObj, final String url,
                                           final AsyncHttpResponseHandler resp) {
        RequestParams params = getParams();
        setParam(requestObj, params);
        LogUtil.D("getRequest-params:" + params);
        LogUtil.D("getRequest-url:" + url);
        LogUtil.D("getRequest-requestObj:" + requestObj);
        getRequest(url, false, params, resp);
    }
    protected static final void  getRequest(String url,final AsyncHttpResponseHandler resp) {
    	 AsyncHttpClient httpClient = getHttpClient();
         if (httpClient != null) {
        	 httpClient.get(url, resp);
         }
	}
    protected static final void getRequest(final Object requestObj, boolean isAudio, final String url,
                                           final AsyncHttpResponseHandler resp) {
        RequestParams params = getParams();
        if (isAudio) {
            params.add("Content-Type", "audio/mpeg");
        }
        setParam(requestObj, params);
        LogUtil.D("getRequest-url:" + url);
        LogUtil.D("getRequest-requestObj:" + requestObj);
        getRequest(url, false, params, resp);
    }

    private final static void postRequest(final Context context, final String url, final boolean isValiToken, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient httpClient = getHttpClient();
        if (httpClient != null) {
            httpClient.post(url, params, responseHandler);
        }
    }

    private final static void postRequest(final String url, final boolean isValiToken,
                                          final RequestParams params, final AsyncHttpResponseHandler resp) {
        postRequest(null, url, isValiToken, params, resp);
    }

    protected static final void postRequest(final Object requestObj, final String url,
                                            final AsyncHttpResponseHandler resp) {
        RequestParams params = getParams();
        setParam(requestObj, params);
        postRequest(url, false, params, resp);
    }

    protected static final void postRequest(final Object requestObj, final File file, final String url,
                                            final AsyncHttpResponseHandler resp) {
        RequestParams params = getParams();
        if (file != null && file.exists()) {
            try {
                byte[] buf = file2ByteSteam(file);
                params.put("filedata", new ByteArrayInputStream(buf));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setParam(requestObj, params);
        postRequest(url, false, params, resp);
    }

    protected static final byte[] file2ByteSteam(File file) {
        BufferedInputStream bis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int c = bis.read();//读取bis流中的下一个字节

            while (c != -1) {
                baos.write(c);
                c = bis.read();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    //IMAGE UPLOAD
    private final static <T> void postRequest(
            final Context context, final String url, final HttpEntity entity,
            final AsyncHttpResponseHandler resp) {
        AsyncHttpClient httpClient = getHttpClient();
        if (httpClient != null) {
            httpClient.post(context, url, entity, "application/data", resp);
        }
    }

    protected static final <T> void jsonPost(
            final Context context, final T t,
            final String url, final AsyncHttpResponseHandler resp) {
        if (context == null || t == null || url == null || resp == null) {
            LogUtil.E("jsonPost param is NULL");
            return;
        }

        try {
            HttpEntity httpEntity = new StringEntity(obj2Json(t),HTTP.UTF_8);
            postRequest(context, url, httpEntity, resp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static final <T> String obj2Json(T t) {
        final String s = Json2ObjHelper.gson.toJson(t, t.getClass());
        LogUtil.V("obj2Json:" + s);
        return s;
    }

//    public static final void jsonPost(final Object requestObj, final String url,
//                                      final AsyncHttpResponseHandler resp) {
//        RequestParams params = getParams();
////        setParam(requestObj, params);//TODO
//        postRequest(url, false, params, resp);
//    }

    private static void setParam(final Object requestObj, final RequestParams params) {
        if (requestObj != null) {
            final Class clazz = requestObj.getClass();
            if (clazz != null) {
                reflect(clazz, params, requestObj);
            }
        }
    }

    /*private static void plane2ParamsForCreditAccountAddReq(final Object requestObj, final RequestParams params) {
        AddReq planeOrderReq = (AddReq) requestObj;
        List<AddTmpReq> passgeners = planeOrderReq.list;
        if (passgeners != null) {
            for (int i = 0; i < passgeners.size(); i++) {
                AddTmpReq passgenerReq = passgeners.get(i);
                if (passgenerReq != null) {
                    reflect(AddTmpReq.class, params, passgenerReq);
                }
            }
        }
    }*/

    private static final void reflect(final Object obj, final RequestParams params) {
        if (obj == null || params == null) return;
        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            if (isList(fields[j])) {
                continue;
            }

            try {
                params.put(fields[j].getName(), fields[j].get(obj));
                LogUtil.V(fields[j].getName() + ":" + fields[j].get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final void reflect(final Class clazz, final RequestParams params, final Object requestObj) {
        if (clazz == null || params == null) return;
        Field[] fields = clazz.getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            if (isList(fields[j])) {
                continue;
            }

            try {
                params.put(fields[j].getName(), fields[j].get(requestObj));
                LogUtil.V(fields[j].getName() + ":" + fields[j].get(requestObj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            reflect(superclass, params, requestObj);
        }
    }

    //    Passgeners[0].Gender”:1
    private static final void reflect(final String pre, final Object obj, final int index, final RequestParams params) {
        if (pre == null || obj == null || params == null) return;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            try {
                String name = fields[j].getName();
                Object value = fields[j].get(obj);
                String key = pre + "[" + index + "]." + name;
                params.put(key, value);
                LogUtil.V(key + ":" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static String change(final String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }

    private static final boolean isList(final Field field) {
        if (field != null) {
            String name = field.getType().getName();
            String List = List.class.getName();
            String ArrayList = ArrayList.class.getName();
            return ArrayList.equals(name) || List.equals(name);
        }
        return false;
    }

}
