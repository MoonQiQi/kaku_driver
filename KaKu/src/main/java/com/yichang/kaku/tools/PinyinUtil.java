package com.yichang.kaku.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	/**
	 * 将汉字转换成拼音
	 *
	 * @param str 汉字
	 * @return 对应的拼音
	 */
	public static String getPinyin(String str) {
		
		
		// 拼音格式化工具
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		// 设置不带音调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// 设置为大写字母
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		
		
		StringBuilder sb = new StringBuilder();
		// 获取字符数组
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];

			if(Character.isWhitespace(c)){
				// 如果是空格, 跳过循环
				continue;
			}
			
			if(c <= -127 || c > 128){
				// 可能是汉字
				try {
					// 获取拼音 , 黑 -> HEI, 单 -> DAN, SHAN
					String s = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
					sb.append(s);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}else {
				// 不可能是汉字
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
