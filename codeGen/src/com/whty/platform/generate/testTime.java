package com.whty.platform.generate;

import java.util.Calendar;
import java.util.Date;

public class testTime {

	
	public static void main(String[] args) {
		Calendar cal=Calendar.getInstance();
		Date now=cal.getTime();
		
		cal.set(Calendar.MINUTE, 0);
		Date start=now;
		Date end=now;
		int sh=8;
		cal.set(Calendar.HOUR_OF_DAY, sh);
		start=cal.getTime();
		
		
		int eh=12;
		cal.set(Calendar.HOUR_OF_DAY, eh);
		end=cal.getTime();
		System.out.println(now);
		System.out.println(start);
		System.out.println(end);
		
		int tag1=start.compareTo(now);
		int tag2=end.compareTo(now);
		if(tag1<1 && tag2>-1){
			System.out.println(true);
		}else{
			System.out.println(false);
		}
			
			
	}
}
