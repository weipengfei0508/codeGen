/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author 舒海洋
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	

	
	/*
	 * 得到当前月的第一天
	 */
	public static Date firstMDay(){
		Calendar cday=Calendar.getInstance();
		cday.add(Calendar.MONTH, -1);
		cday.set(Calendar.DAY_OF_MONTH, 1);
		return cday.getTime();
	}
	
	/*
	 * 得到当前月的第一天
	 */
	public static Date firstMDay(int year,int month){
		Calendar cday=Calendar.getInstance();
		cday.set(Calendar.YEAR, year);
		cday.set(Calendar.MONTH, month);
		//cday.add(Calendar.MONTH, -1);
		cday.set(Calendar.DAY_OF_MONTH, 1);
		return cday.getTime();
	}
	
	
	public static String getYearMonth(){
		Calendar cday=Calendar.getInstance();
		cday.add(Calendar.MONTH, -1);
		
		return DateFormatUtils.format(cday.getTime(), "yyyy-MM");
		
	}
	
	public static String getDateformate(int year,int month,int day,String formate){
		Calendar cday=Calendar.getInstance();
		cday.set(year, month, day);
		cday.add(Calendar.MONTH, -1);
		return DateFormatUtils.format(cday.getTime(), "yyyy-MM");
	}
	
	/*
	 * 得到当前月的最后一天
	 */
	public static Date LastMDay(){
		Calendar cday=Calendar.getInstance();
		cday.add(Calendar.MONTH, -1);
		int MaxDay=cday.getActualMaximum(Calendar.DAY_OF_MONTH);
		cday.set(Calendar.DAY_OF_MONTH, MaxDay);
		return cday.getTime();
	}
	
	/*
	 * 得到当前月的最后一天
	 */
	public static Date LastMDay(int year,int month){
		Calendar cday=Calendar.getInstance();
		cday.set(Calendar.YEAR, year);
		cday.set(Calendar.MONTH, month);
		//cday.add(Calendar.MONTH, -1);
		int MaxDay=cday.getActualMaximum(Calendar.DAY_OF_MONTH);
		cday.set(Calendar.DAY_OF_MONTH, MaxDay);
		return cday.getTime();
	}
	
	public static  int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
        caled.setTime(late);   
         //设置时间为0时   
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天数   
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   } 
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		Date st=new Date(2013, 1, 1);
		Date et=new Date(2013, 1, 4); 
//        DateFormat df = DateFormat.getDateInstance();   
//        try {   
//            earlydate = df.parse("2009-09-21");   
//            latedate = df.parse("2009-10-16");   
//        } catch (ParseException e) {   
//              e.printStackTrace();   
//          }   
        int days = daysBetween(st,et);   
        //System.out.println(getDateformate(2013,9,1,"yyyy-MM-dd")); 
        
        System.out.println(firstMDay(2013, 8));   
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
		 //System.out.println(getYearMonth());
	}
	
}
