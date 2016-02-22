package com.yichang.kaku.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckLegal {
	 
	public static boolean isPassword(String password) {
		try {
	        //密码由字母、数字、下划线、特殊字符组成 
			
//			Pattern regexpwd = Pattern.compile("^[a-zA-Z0-9_!@#$%^&*()]{6,30}$");
			Pattern regexpwd = Pattern.compile("^[a-zA-Z0-9]{6,12}$");
			Matcher m = regexpwd.matcher(password);
			return m.matches();
	    } catch (Exception e) {
			return false;
		}
	}

	public static boolean isEmail(String email) {
		try {
	        // 电子邮件   
	        //String checkemail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";   
	        String checkemail = "^([a-zA-Z0-9_])+\\@(([a-zA-Z0-9])+\\.)+([a-zA-Z0-9]{2,4})+$";
	        Pattern regexemail = Pattern.compile(checkemail);   
	        Matcher matcheremail = regexemail.matcher(email);
	        return matcheremail.matches();   
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isphone(String telphone) {
		try {
	        //手机号码  
			Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		//	Pattern p = Pattern.compile("^([1-9])\\d{10}$");
			Matcher m = p.matcher(telphone);
			return m.matches(); 
		} catch (Exception e) {
			return false;
		}
	}
	

	public static boolean isNumber(String number) {
		try {
	        //手机号码  
			Pattern p = Pattern.compile("^[0-9]d*$"); 
		//	Pattern p = Pattern.compile("^([1-9])\\d{10}$");
			Matcher m = p.matcher(number);
			return m.matches(); 
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isTencode(String twocode) {
		try {
	        //二维码的验证码
			Pattern p = Pattern.compile("^\\d{10}$"); // 验证二维码验证码
		//	Pattern p = Pattern.compile("^([1-9])\\d{10}$");
			Matcher m = p.matcher(twocode);
			return m.matches(); 
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isIdcard(String idcard) {
		try {
			
			//15位和18位兼容
	    	//Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9xX])|(\\d{17}[0-9xX])");  
			//只支持18位
			Pattern idNumPattern = Pattern.compile("(\\d{17}[0-9xX])");  
			Matcher m = idNumPattern.matcher(idcard);
			return m.matches();
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isCode(String code) {
		try {
	        //邮编号码 
			Pattern p = Pattern.compile("^[1-9][0-9]{5}$"); 
		//	Pattern p = Pattern.compile("^([1-9])\\d{10}$");
			Matcher m = p.matcher(code);
			return m.matches(); 
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isTelephone(String telephone) {
		try {
	        //电话
			Pattern p = Pattern.compile("\\d{8,12}"); 
		//	Pattern p = Pattern.compile("^([1-9])\\d{10}$");
			Matcher m = p.matcher(telephone);
			return m.matches(); 
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 身份证号校验
	 * @param idCard
	 * @return
	 */
	 public static boolean isIdCard(String idCard) { 
	        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}"; 
	        return Pattern.matches(regex,idCard); 
	    } 
	 
	 /**
	  * 邮政编码校验
	  * @param post
	  * @return
	  */
	 public static boolean isPost(String post){
		 String regex = "^[1-9]\\d{5}$";
		 return Pattern.matches(regex,post);
	 }
}
