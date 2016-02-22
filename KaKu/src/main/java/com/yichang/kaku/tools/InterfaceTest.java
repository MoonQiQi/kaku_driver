package com.yichang.kaku.tools;

import com.yichang.kaku.webService.UrlCtnt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InterfaceTest {
	
	//10-17 12:51:37.420: E/LungPoon(30373): Unknown column 'help' in 'field list'

	public static void sendEms() throws Exception {
        String code = "10033";

        URL url = new URL(UrlCtnt.BASEIP);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数

        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        params.append("code").append("=").append(code);
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        LogUtil.E("sendEms :"+new String(readInputStream(inStream), "gbk"));

    }
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

	
	
	
/*	public static void testGehome(){
		HomeReq req = new HomeReq();
		req.code = "2001";
		LungPoonApiProvider.getHome(req, new BaseCallback<HomeResp>(HomeResp.class) {

			@Override
			public void onSuccessful(int statusCode, Header[] headers,
					HomeResp t) {
				// TODO Auto-generated method stub
				LogUtil.E(statusCode+" getHome "+t);
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg,
					Throwable error) {
				// TODO Auto-generated method stub
				LogUtil.E("msg "+ msg);
			}
		});
	}*/
	
/*	public  static void testGetZone() {
		// TODO Auto-generated method stub
		ZoneReq req = new ZoneReq();
		req.code = "2002";
		req.start = "0";
		req.len = "4";
		req.sort = "0";
		LungPoonApiProvider.getZone(req, new BaseCallback<ZoneResp>(
				ZoneResp.class) {

			@Override
			public void onSuccessful(int statusCode, Header[] headers,
					ZoneResp t) {
				// TODO Auto-generated method stub
				if (t != null && Constants.RES.equals(t.res)) {
					LogUtil.E(" getZone: " + t.prize_info);
				} else {
					LogUtil.E(" getZone: " + statusCode);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg,
					Throwable error) {
				// TODO Auto-generated method stub
			}
		});
	}*/
}
