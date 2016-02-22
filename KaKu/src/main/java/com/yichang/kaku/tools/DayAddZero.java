package com.yichang.kaku.tools;
/**
 * @author: 战琪
 * @类   说   明:	
 * @version 1.0
 * @创建时间：2014年11月3日 下午1:24:21
 * 
 */
public class DayAddZero {

	public static String mm;
	public static String dd;
	
	public static String MonthAdd(int month){
		switch (month) {
		case 0:
			mm="01";
			break;
		case 1:
			mm="02";
			break;
		case 2:
			mm="03";
			break;
		case 3:
			mm="04";
			break;
		case 4:
			mm="05";
			break;
		case 5:
			mm="06";
			break;
		case 6:
			mm="07";
			break;
		case 7:
			mm="08";
			break;
		case 8:
			mm="09";
			break;
		case 9:
			mm="10";
			break;
		case 10:
			mm="11";
			break;
		case 11:
			mm="12";
			break;
			default:
				break;
			}
		return mm;
	}
	
	public static String DayAdd(int day){
		
		switch (day) {
		case 1:
			dd="01";
			break;
		case 2:
			dd="02";
			break;
		case 3:
			dd="03";
			break;
		case 4:
			dd="04";
			break;
		case 5:
			dd="05";
			break;
		case 6:
			dd="06";
			break;
		case 7:
			dd="07";
			break;
		case 8:
			dd="08";
			break;
		case 9:
			dd="09";
			break;
		case 10:
			dd="10";
			break;
		case 11:
			dd="11";
			break;
		case 12:
			dd="12";
			break;
		case 13:
			dd="13";
			break;
		case 14:
			dd="14";
			break;
		case 15:
			dd="15";
			break;
		case 16:
			dd="16";
			break;
		case 17:
			dd="17";
			break;
		case 18:
			dd="18";
			break;
		case 19:
			dd="19";
			break;
		case 20:
			dd="20";
			break;
		case 21:
			dd="21";
			break;
		case 22:
			dd="22";
			break;
		case 23:
			dd="23";
			break;
		case 24:
			dd="24";
			break;
		case 25:
			dd="25";
			break;
		case 26:
			dd="26";
			break;
		case 27:
			dd="27";
			break;
		case 28:
			dd="28";
			break;
		case 29:
			dd="29";
			break;
		case 30:
			dd="30";
			break;
		case 31:
			dd="31";
			break;

		default:
			break;
		}
		return dd;	
	}
}
