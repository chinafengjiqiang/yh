package com.yh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	public static Long dateString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2Long(String date,String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2LongNotss(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTime2Long(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	public static Long dateTime2LongNotS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	
	public static Long dateTime2Long(Date date,String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate,formatString);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	public static Date stringTime2Date(String date){
		long longDate = dateTimeString2Long(date);
		return new Date(longDate);
	}
	
	public static Date stringTime2DateNotss(String date){
		long longDate = dateTimeString2LongNotss(date);
		return new Date(longDate);
	}
	
	public static Date stringDay2Date(String date){
		long longDate = dateString2Long(date);
		return new Date(longDate);
	}
	
	public static String dateTime2String(Date date){
		long longdate = dateTime2Long(date);
		return long2TimeString(longdate);
	}
	
	
	public static String dateTime2StringNotS(Date date){
		long longdate = dateTime2LongNotS(date);
		return long2TimeStringNotS(longdate);
	}
	
	/**
	 * 日期转换为去除秒后的字符串
	 * @param date 要转换的日期
	 * @return 转换后的
	 */
	
	public static String dateTime2String(Date date,String formatString) {
		long time = dateTime2Long(date, formatString);
		return long2TimeString(time, formatString);
	}
	
	
	public static String dateTimeNotS2String(Date date) {
		String stringdate = dateTime2String(date);
		long stringDate = dateTimeNotSString2Long(stringdate);
		return long2TimeStringNotS(stringDate);
	}
	
	public static Date stringTimeNotS2Date(String date){
		long longDate = dateTimeNotSString2Long(date);
		return new Date(longDate);
	}
	
	public static Long dateTimeNotSString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	public static String long2DateString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	public static String long2MonthString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	public static String long2DateTimeString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}

	public static String long2TimeString(Long time,String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	public static String long2TimeString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	public static String long2TimeStringNotS(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}

	/**
	 * 月份左右移动某几个月
	 * @param time
	 * @param step
	 * @return
	 */
		public static long getMoveMonthDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			//end.roll(Calendar.MONTH,step);
			end.add(Calendar.MONTH,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
	    public static Date getOffsetMonthDate(Date protoDate,int monthOffset){   
		        Calendar cal = Calendar.getInstance();   
		        cal.setTime(protoDate);   
		        cal.add(Calendar.MONTH, monthOffset); //正确写法   
		        //System.out.println(cal.get(Calendar.MONTH));   
		        return cal.getTime();   
		}


	/**
		 * 时间左右移动几年
		 */
		public static long getMoveYearDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.add(Calendar.YEAR,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		/**
		 * 时间左右移动几年
		 */
		public static long getMoveHourDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.add(Calendar.HOUR,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		
		
		/**
		 * 时间左右移动X分钟
		 */
		public static long getMoveMinuteDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.add(Calendar.MINUTE,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		/**
		 * 某时间左右移动某几天
		 * @param time
		 * @param step
		 * @return
		 */
		public static long getEndValidDate4Day(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.add(Calendar.DATE,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		
		public static Date getEndValidDate4Day(Date date,int step){
			Calendar end = Calendar.getInstance();
			end.setTime(date);
			end.add(Calendar.DATE,step);
			return end.getTime();
		}
		
		public static Long getDayLong2ValidLong(Long day){
			day = day==null?new Long(new Date().getTime()):day;
			String str = long2DateString(day);
			Long result = dateString2Long(str);
			return result;
		}
		
		/**
		 * 得到本周周一的时间值
		 * @param time
		 * @return
		 */
		public static long getMondayDayOfWeek(long time){
			time = getDayLong2ValidLong(new Long(time)).longValue();
	        Calendar  day = new GregorianCalendar();
			day.setTimeInMillis(time);
			day.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.MONDAY);
			long validTime = day.getTimeInMillis();
			return validTime;
		}
		
	    public static String dateTime2StringNotSNEW(Date date){
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String thisDate = "";
	        try {
	            thisDate = format.format(date);
	        } catch (Exception e) {
	            return "";
	        }
	        return thisDate;
	    }
		
		/**
		 * 格式化手机时间
		 * @param str 要格式的时间
		 * @return 格式后的时间
		 */
		public static String phoneDateFormat(String str) {
			if(StringUtils.isBlank(str)) {
				return str;
			} else {
				str = str.replaceAll("[^0-9:-]", " ");
			}
			return str;
		}
		
		
	    public static String dateTime2StringNotS1(Date date){
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String thisDate = "";
	        try {
	            thisDate = format.format(date);
	        } catch (Exception e) {
	            return "";
	        }
	        return thisDate;
	    }
	    
	    public static String getStringNowTime(){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    	String thisDate = "";
	        try {
	            thisDate = format.format(new Date());
	        } catch (Exception e) {
	            return "";
	        }
		   return thisDate;
	    }
	    
	    private static String getLastDay() {
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar calendar = Calendar.getInstance();
	        Date theDate = calendar.getTime();
	        String s = df.format(theDate);
	        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
	        return str.toString();

	    }

	    public   static   String   getLastDayOfMonth(Date   sDate1)   {  
	        Calendar   cDay1   =   Calendar.getInstance();  
	        cDay1.setTime(sDate1);  
	        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        Date   lastDate   =   cDay1.getTime();  
	        lastDate.setDate(lastDay);  
	        String resDate = DateUtils.dateTime2String(lastDate,"yyyy-MM-dd")+" 23:59:59";
	        return   resDate;  
	}
	    
	    
	    private static Map<String, String> getFirstday_Lastday_Month(Date date) {
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH, -1);
	        Date theDate = calendar.getTime();
	        
	        //上个月第一天
	        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	        gcLast.setTime(theDate);
	        gcLast.set(Calendar.DAY_OF_MONTH, 1);
	        String day_first = df.format(gcLast.getTime());
	        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
	        day_first = str.toString();

	        //上个月最后一天
	        calendar.add(Calendar.MONTH, 1);    //加一个月
	        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
	        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
	        String day_last = df.format(calendar.getTime());
	        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
	        day_last = endStr.toString();

	        Map<String, String> map = new HashMap<String, String>();
	        map.put("first", day_first);
	        map.put("last", day_last);
	        return map;
	    }

		
		public static void main(String[] args) {
			/*long l1 = 1381393933000l;
			long l2=1381393933219l;
			String s1 = long2TimeString(l1);
			String s2 = long2TimeString(l2);
			System.out.println(s1+" : "+s2);*/
			//String res = dateTime2String(new Date(),"MM月dd日HH:mm");
			//System.out.println(res);
			//String res = dateTime2StringNotS1(new Date());
			//String res = getStringNowTime();
			//System.out.println(res);
			//Date date = getOffsetMonthDate(new Date(),8);
			//System.out.println(dateTimeNotS2String(date));
			/*String str = new java.text.DecimalFormat("#.00").format(3.1455926);
			System.out.println(str);
			long longTime = dateString2Long("2014-05-16 14:20:30");
			Date startDate = new Date(longTime);
			Date endDate = new Date();
			int days = getDaysBetween(startDate, endDate);
			System.out.println(days);*/
		  //  Date dte = getOffsetMonthDate(new Date(),-1);
		   // String res = dateTime2StringNotS1(dte);
//		  long t = 1419523200000l;
//		  String res = long2TimeString(t);
//		  System.out.println(res);
		  
//		  String start = "2015-04-17";
//		  String end = "2016-08-19";
//		  
//		  long sl = dateString2Long(start);
//		  long el = dateString2Long(end);
//		  long term = (el - sl) / 1000 / 3600 / 24 / 30;
		  //long l = getNowUnixTimestamp();
		 //Map<String,String> map = getFirstday_Lastday_Month(new Date());
		  //String l = map.get("last");
		  //String l = getLastDayOfMonth(new Date());
			String data = "2016-07-19 12:20";
			long lret = dateTimeNotSString2Long(data);
			String strData = long2TimeString(lret);
		  System.out.println(lret+"  "+strData);
		}
		
		   //得到两个日期之间的天数
		   public static int getDaysBetween(Date startDate, Date endDate){
				int days = 0;
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				long time1 = dateTime2Long(startDate);
			    calendar1.setTimeInMillis(time1);		
			    long time2 = dateTime2Long(endDate);
			    calendar2.setTimeInMillis(time2);
			    days = getDaysBetween(calendar1,calendar2);
			    return days;
			}
			
			//得到两个日期之间的天数
		    public static int getDaysBetween (Calendar d1, Calendar d2){ 
		        if (d1.after(d2)){ 
		            Calendar swap = d1;
		            d1 = d2; 
		            d2 = swap; 
		        } 
		        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR); 
		        int y2 = d2.get(Calendar.YEAR); 
		        if (d1.get(Calendar.YEAR) != y2){ 
		            d1 = (Calendar) d1.clone(); 
		            do{ 
		                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数 
		                d1.add(Calendar.YEAR, 1); 
		            } while (d1.get(Calendar.YEAR) != y2); 
		        } 
		        return days; 
		    }
		    
		    public static float getFormatFloat(float value){
		    	try {
		    		String strValue = new java.text.DecimalFormat("#.00").format(value);
		    		if(strValue != null && !"".equals(strValue))
		    			return Float.parseFloat(strValue);
		    		return value;
				} catch (Exception e) {
					return value;
				}
		    	
		    }
		    
		    public static Long dateTimeNoYString2Long(String date) {
		        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
		        Date thisDate = null;
		        try {
		            thisDate = format.parse(date);
		        } catch (ParseException e) {
		            return null;
		        }
		        return new Long(thisDate.getTime());
		    }
		    
		    public static String unixTimestamp2String(long timestamp){
		      try {
		        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		        .format(new Date(timestamp * 1000));
		        return date;
              } catch (Exception e) {
                return "";
              }
		    }
		    
		    public static long date2UnixTimestamp(String time){
		      try {
		        long ldate = 0L;
		        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		        if(date != null){
		          ldate = date.getTime()/1000;
		        }
		        return ldate;
              } catch (Exception e) {
                // TODO: handle exception
                return 0L;
              }
		    }
		    
		    public static long getNowUnixTimestamp(){
		      try {
		        long ldate = 0L;
		        Date date = new Date();
		        if(date != null){
	               ldate = date.getTime()/1000;
	            }
		        return ldate;
              } catch (Exception e) {
                return 0L;
              }
		    }


	public static Date stringsToDate(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return  sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
		    
}
