package com.yichang.kaku.tools;

import com.yichang.kaku.webService.UrlCtnt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InterfaceTest {
	
	//10-17 12:51:37.420: E/LungPoon(30373): Unknown column 'help' in 'field list'

	public static void sendEms() throws Exception {

        URL url = new URL(UrlCtnt.BASEIP+"bill/goods/goods_information");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数

        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        params.append("id_driver").append("=").append("11");
        params.append("id_goods").append("=").append("145");
        params.append("sid").append("=").append("ff971ff22139bc9222563e0b66eef50d");
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        LogUtil.E("KaKu :"+new String(readInputStream(inStream), "gbk"));

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


}
