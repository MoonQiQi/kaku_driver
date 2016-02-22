package com.yichang.kaku.tools;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Glaivelee on 14-4-29.
 */
public class TextUtil {

    public final static boolean isCHN(char c) {
        boolean isOK = false;
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            isOK = true;
        }
        return isOK;
    }

    /**
     * errorCode=00&custTranId=null&orderStatus=1&ibknum=****&returnActFlag=1&payAmount=0.01&phoneNum=&holderName=****&cardTyp=01&orderIp=118.187.65.124&acctNo=****&errorMessage=00&transactionId=1994295064&orderRefer=&merchantNo=104440154111428&orderSeq=1326280086&payTime=20140508151426&signData=MIIEZQYJKoZIhvcNAQcCoIIEVjCCBFICAQMxCTAHBgUrDgMCGjALBgkqhkiG9w0B
     * BwGgggMcMIIDGDCCAgCgAwIBAgIQYJiGMHRzcwcr8DtN4OzzBzANBgkqhkiG9w0B
     * AQUFADBdMQswCQYDVQQGEwJjbjEWMBQGA1UEChMNYmFuayBvZiBjaGluYTEQMA4G
     * A1UECBMHYmVpamluZzEQMA4GA1UEBxMHYmVpamluZzESMBAGA1UEAxMJYm9jbmV0
     * IGNhMB4XDTA4MTAxODA3NTQyM1oXDTE4MTAwNjA3NTQyM1owNzELMAkGA1UEBhMC
     * Q04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExEDAOBgNVBAMTB0JPQ1NpZ24wgZ8w
     * DQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKyIAbb9FOLikUSHpi6VJum4PnCJ/CFB
     * C9q7rucP/7V3iTaM4LM84oxcPB+7N/t6XXD6XSxl4Y9I963+j9Rgb0krsjf9Y/ai
     * pr1uuBueEiuZymIYhUJGBran5V04a7jyTDSqSYAKoZKIJ9Ti7u8UoGd5CED76uyN
     * zlHequDmYTXtAgMBAAGjfjB8MB8GA1UdIwQYMBaAFCZqvzWrI5lrG/BQN416c2Da
     * AVJQMC0GA1UdHwQmMCQwIqAgoB6GHGh0dHA6Ly8yNi4xMjAuMzYuMzIvY3JsMS5j
     * cmwwCwYDVR0PBAQDAgbAMB0GA1UdDgQWBBSzPKFT/y0od/001puh31jj7w4y4TAN
     * BgkqhkiG9w0BAQUFAAOCAQEAXM3aK8LqRyhXBbMMhHOBW/abpEK3YQEtWss0wsOL
     * /nFawPwDUX7u0IPHaRrLYqZLzmyLnu3cmSeGENnFQbkqJJA9APJ1XzZL94vO6876
     * xA2/p0feLuplftvDTSkahqctTVNOXybv0uD7xLOgL/E6i2BIUKyPZ6vYN5EqmUuz
     * D7kPsGCQbZIg1breiuTaUSRbhbcNuCJTJG2AOxleMX8DKP8UGR6ek1MnzsrU+bH2
     * ahlEeaWmc1Y2p0pUAuSHEGfU541gf88TsPBNA4mwco5m1WDysOEwu6CR0KM7JzJM
     * u7SpnVWZuXvWUmTKKPbEYFu6sor99MPHa4NUYUtbzehnBTGCARMwggEPAgEDMHEw
     * XTELMAkGA1UEBhMCY24xFjAUBgNVBAoTDWJhbmsgb2YgY2hpbmExEDAOBgNVBAgT
     * B2JlaWppbmcxEDAOBgNVBAcTB2JlaWppbmcxEjAQBgNVBAMTCWJvY25ldCBjYQIQ
     * YJiGMHRzcwcr8DtN4OzzBzAHBgUrDgMCGjALBgkqhkiG9w0BAQEEgYAje1XPkvpo
     * ubhzdX2dV7WXXBsrizFnkUbnxE9TJuphTonfDPsGuMy3dm7L3vn2FHKDrESi9I5u
     * Vkq6Xp+yNWqVRXjlydzF4JlSgqSh7W4k9OFlyX/2xlHbEOAD35NbAWXxiWvDZjgM
     * eGkaUBYX3AAUUMgLzEP13xCrEpyDuMMLdw==&bankTranSeq=2014050844003742001132&orderNo=720873
     */
    public final static Map<String, String> analysis(String url) {
        Map<String, String> paramMap = new Hashtable<String, String>();
        if (!"".equals(url)) {
            String[] paramaters = url.split("&");
            for (String param : paramaters) {
                if (param.contains("orderNo")) {
                    String[] values = param.split("=");
                    if (values != null && values.length >= 2) {
                        paramMap.put(values[0], values[1]);
                    }
                }
            }
        }
        return paramMap;
    }

    public static void onFocusChange(View v, boolean hasFocus) {
        EditText _v = (EditText) v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());
        } else {
            String hint = _v.getHint().toString();
            _v.setTag(hint);
            _v.setHint("");
        }
    }

    public static void limitTextLength(int length, TextView textView) {

        String s = textView.getText().toString();

        if (TextUtils.isEmpty(s) || s.length() <= length) {
            return;
        }

        String temp = s.substring(0, length);

        textView.setText(temp + "...");
    }

}
