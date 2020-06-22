package com.whty.platform.generate;

import java.util.Calendar;

public class testClass {

	
	public static void main(String[] args) {
		Calendar calendar =Calendar.getInstance();
		System.out.println(calendar.getTime());
		calendar.add(Calendar.DATE, 30);
		System.out.println(calendar.getTime());
		//int hour=8;
		//System.out.println(hour%2);
		/*String tels="13971147,4444";
		if(tels!=null && !tels.isEmpty()){
			String []tel=tels.split(",");
			for(int i=0;i<tel.length;i++){
				System.out.println(tel[i]);
			}
			
		}*/
	}
}
