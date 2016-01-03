package terminal.backend;

import java.util.Date;

public class FDate extends Date
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public String to_shortString()
	{
		String retval = "" + getDay() + "." + getMonth() + "." +getYear();
		return retval;
	}
	
//	int year;
//	int month;
//	int day;
//	
//	public FDate(int d, int m, int y)
//	{
//		day = d;
//		month = m;
//		year = y;
//		
//		if(year < 2000)
//		{
//			year = 2000;
//		}
//		else if(year > 3000)
//		{
//			year = 3000;
//		}
//		
//		if(month < 1)
//		{
//			month = 1;
//		}
//		else if(month > 12)
//		{
//			month = 12;
//		}
//		
//		if(day < 1)
//		{
//			day = 1;
//		}
//		else if(day > 28)
//		{
//			
//		}
//			
//	}
//	
//	private boolean is_leap_year()
//	{
//		boolean retval = false;
//		
//		if((year % 400 == 0) && !(year % 100 == 0) && )
//		{
//			
//		}
//		return (year % 400==0) - (year % 100==0) +
//				(year % 4==0);
//	}
}
