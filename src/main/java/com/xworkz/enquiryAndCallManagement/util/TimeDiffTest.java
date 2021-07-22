package com.xworkz.enquiryAndCallManagement.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDiffTest {

	public static long calculateDaysDiff(String intrestedDate) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormatter.format(new Date());		
		long diffDays = 0;
	    try {
	    	Date givenDate = dateFormatter.parse(intrestedDate);
			Date today = dateFormatter.parse(currentDate);
			
			System.out.println("givenDate="+givenDate+" today="+today);
			diffDays = (givenDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
			System.out.println("diffDays="+diffDays);
			return diffDays;
			
		}    
	    catch (ParseException e) {
	    	System.out.println(e.getMessage()+ e);
		}
	  return diffDays;
	}	
	
	
	public static void main(String[] args) {
		TimeDiffTest.calculateDaysDiff("2021-07-02");
	}
	
}
