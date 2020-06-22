package com.whty.platform.generate.codegen;

/**
 * 文件操作工具类
 */
public class GenFunctions {

	/**
	 * 将传入的字符串首字母大写
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		str=str.toLowerCase();
		int strLength = str.length();
		if (strLength > 1) {
			String post = str.substring(1, strLength);
			char first = str.charAt(0);
			return String.valueOf(first).toUpperCase() + post;
		} else {
			return str.toUpperCase();
		}
	}
	
	public static void main(String[] args){
		System.out.println(firstToUpperCase("USER"));
	}
}
