package com.shabro.comm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;

public class BaseUtil {
	public static double EARTH_RADIUS = 6378137;	//赤道半径(单位m)

	public static boolean objectNotNull(Object object)
	{
		if(null == object)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否不为空
	 * 空格字符串返回false
	 * @param string
	 * @return
	 */
	public static boolean stringNotNull(String string)
	{
		if((null == string)||string.trim().equals("") || (string.trim().equals("null")))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 把空串转换为null
	 * @param str
	 * @return
	 */
	public static String stringToNull(String str){
		if(!"".equals(str)){
			return str;
		}
		return null;
	}
	
	/**
	 * 把Null字符串转换为Null
	 * @param str
	 * @return
	 */
	public static String nullstringToNull(String str){
		if(!"".equals(str) && !"null".equals(str)){
			return str;
		}
		return null;
	}

	public static String stringToNotNullString(String string){
		if (null == string)
		{
			return "";
		}
		return string;
	}
	
    public static String timeToFormatString(Date date ,String format) {
        
        if(BaseUtil.objectNotNull(date)){
            
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            
            return sdf.format(date);
        }
        
        return null;
    }
    
	/**
	 * 检查List是否为空 
	 * 2006-2-18
	 * loading
	 * @param list
	 * @return
	 */
	public static boolean listNotNull(List list)
	{
		if(list != null && list.size() != 0)
		{
			return true;
		}
		return false;
	}

	public static List pageList(List list,int iPage,int iRows)
	{
		List lstRet = null;
		if(!BaseUtil.listNotNull(list))
		{
			return lstRet;
		}

		int startIndex = (iPage - 1) * iRows;
		int endIndex  = iPage * iRows;

		if(startIndex >= list.size()) {

			return lstRet;
		}
		else {
			if (endIndex >= list.size()) {
				lstRet = list.subList(startIndex, list.size());
			} else {
				lstRet = list.subList(startIndex, endIndex);
			}
		}

		return lstRet;
	}

    
    /**
     * 判读集合对象是否为空
     * @param con
     * @return
     */
    public static boolean collectionNotNull(Collection con)
    {
        if(con != null &&con.size() != 0)
        {
            return true;
        }
        return false;
    }

	/**
	 * 射1�7 Character 转换戄1�7 String
	 * @param character
	 * @return
	 * @throws Exception
	 */
	public static String characterToString(Character character) throws Exception{
		String newString ="";
		if(null!=character){
			newString = character.toString();
		}
		return newString;
	}

	public static int toDays(Date date) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		date=sdf.parse(sdf.format(date));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long time = cal.getTimeInMillis();
		int days = (int)time/(1000*3600*24);

		return days;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param little 较小的时间
	 * @param big  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Integer daysBetween(Date big, Date little) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		little=sdf.parse(sdf.format(little));
		big=sdf.parse(sdf.format(big));
		Calendar cal = Calendar.getInstance();
		cal.setTime(little);
		long time1 = cal.getTimeInMillis();
		cal.setTime(big);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return new Integer(String.valueOf(between_days));
	}

	/**
	 *字符串的日期格式的计算
	 */
	public static Integer daysBetween(String big, String little) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(little));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(big));
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return new Integer(String.valueOf(between_days));
	}

	/**
	 * 获取和今天附近的某天
	 * @param diff
	 * @return
	 */
	public static Date getNearDay(int diff) {
		Calendar now = Calendar.getInstance();
		int beforeMon = now.get(Calendar.MONTH);
		now.roll(Calendar.DAY_OF_YEAR, diff);

		int nowMon = now.get(Calendar.MONTH);
		//必须进行日期处理，否则2009-01-04日前七天是2009-12-28
		if (diff > 0) {
			if (nowMon < beforeMon) {
				now.roll(Calendar.YEAR, 1);
			} else if (nowMon == beforeMon && diff > 31) {
				now.roll(Calendar.YEAR, 1);
			}
		} else if (diff < 0) {
			if (nowMon > beforeMon) {
				now.roll(Calendar.YEAR, -1);
			} else if (nowMon == beforeMon && diff < -31) {
				now.roll(Calendar.YEAR, -1);
			}
		}

		return now.getTime();
	}

	public static Date getNearDayYMD(Date base, int diff)  throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		base=sdf.parse(sdf.format(base));
		Calendar cal = Calendar.getInstance();
		cal.setTime(base);

		int beforeMon = cal.get(Calendar.MONTH);
		cal.roll(Calendar.DAY_OF_YEAR, diff);

		int nowMon = cal.get(Calendar.MONTH);
		//必须进行日期处理，否则2009-01-04日前七天是2009-12-28
		if (diff > 0) {
			if (nowMon < beforeMon) {
				cal.roll(Calendar.YEAR, 1);
			}
		} else if (diff < 0) {
			if (nowMon > beforeMon) {
				cal.roll(Calendar.YEAR, -1);
			}
		}
		return cal.getTime();
	}

	public static Date getNearDayTime(Date base, int diff)  throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		base=sdf.parse(sdf.format(base));
		Calendar cal = Calendar.getInstance();
		cal.setTime(base);

		int beforeMon = cal.get(Calendar.MONTH);
		cal.roll(Calendar.DAY_OF_YEAR, diff);

		int nowMon = cal.get(Calendar.MONTH);
		//必须进行日期处理，否则2009-01-04日前七天是2009-12-28
		if (diff > 0) {
			if (nowMon < beforeMon) {
				cal.roll(Calendar.YEAR, 1);
			}
		} else if (diff < 0) {
			if (nowMon > beforeMon) {
				cal.roll(Calendar.YEAR, -1);
			}
		}
		return cal.getTime();
	}
    
	/**
     * 获取系统当前时间 
     * @return
	 */
    public static Date getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date currentDate = new Date();
        try {
            currentDate = df.parse(df.format(new Date()));
        } catch (ParseException e) {
        }
        return currentDate;
    }
    
    public static Date getCurrentDateNoTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date currentDate = new Date();
        try {
            currentDate = df.parse(df.format(new Date()));
        } catch (ParseException e) {
        }
        return currentDate;
    }

	public static String timeToString(Date date){

		if(BaseUtil.objectNotNull(date)){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			return sdf.format(date);
		}

		return null;
	}

	public static Date stringToTime(String string) throws Exception {

		if (!stringNotNull(string)){

			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date datetime = null;

		try {

			datetime = sdf.parse(string);

		} catch (ParseException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}

		if (!BaseUtil.timeToString(datetime).equals(string)){

			throw new Exception();
		}

		return datetime;

	}
    
    public static String dateToString(Date date){
		
		if(BaseUtil.objectNotNull(date)){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			return sdf.format(date);
		}
		
		return null;
	}
	
	public static Date stringToDate(String string) throws Exception {
		
		if (!stringNotNull(string)){
			
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		
		try {
			
			date = sdf.parse(string);
			
		} catch (ParseException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
		
		if (!BaseUtil.dateToString(date).equals(string)){
			
			throw new Exception();						
		}
		
		return date;

	}
	
	public static Date stringToDate(String string,String format){
		
		if (!stringNotNull(string)){
			
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Date date = null;
		
		try {
			
			date = sdf.parse(string);
			
		} catch (ParseException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			//throw new Exception();
		}
		
		return date;

	}
	
	public static Integer stringToInteger(String string) throws Exception 
	{
		
		if (!stringNotNull(string)){
			
			return null;
		}
		
		try {
			
			return new Integer(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	}
	
	public static Short stringToShort(String string) throws Exception 
	{
		
		if (!stringNotNull(string)){
			
			return null;
		}
		
		try {
			
			return new Short(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	}
	public static Long stringToLong(String string) throws Exception{
		
	if (!stringNotNull(string)){
				
				return null;
			}
		
		try {
			
			return new Long(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	}
	public static BigDecimal stringToBigDecimal(String string) throws Exception 
	{
		
		if (!stringNotNull(string)){
			
			return null;
		}
		
		try {
			
			return new BigDecimal(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	}
	public static String longToString(Long integer)
	{
		
		if (!objectNotNull(integer)){
			
			return null;
		}
			
		return integer.toString();
			
	}
	
	public static String integerToString(Integer integer)
	{
		
		if (!objectNotNull(integer)){
			
			return null;
		}
			
		return integer.toString();
			
	}

	public static String integerToNotNullString(Integer integer){
		if (!objectNotNull(integer)){
			return "";
		}
		return integer.toString();
	}
	
    public static String integerToZeroString(Integer integer)
    {
        
        if (!objectNotNull(integer)){
            
            return "0";
        }
            
        return integer.toString();
            
    }    
    
	public static String shortToString(Short _short)
	{
		
		if (!objectNotNull(_short)){
			
			return null;
		}
			
		return _short.toString();
			
	}
	
	public static String bigDecimalToString(BigDecimal bigDecimal){
		
		if (!objectNotNull(bigDecimal)){
			
			return null;
		}
			
		return bigDecimal.toString();
	}
    
    public static String bigDecimalToZeroString(BigDecimal bigDecimal){
        
        if (!objectNotNull(bigDecimal)){
            
            return "0";
        }
            
        return bigDecimal.toString();
    }    
	
	public static String isNull(String string1,String string2){
		
		if (string1 == null){
			
			return string2;
		}
		else{
			
			return string1;
		}
	}
	
	//将Java对象转为XML字符丄1�7
	/***
	public static String toXml(Object object) throws Exception{
		 
        StringWriter writer = new StringWriter();
       
        try {
        	
	        Marshaller marshaller = new Marshaller(writer);
	        
	        marshaller.setEncoding("GB2312");
        	
			marshaller.marshal(object);
	        
		} catch (MarshalException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
			
		} catch (ValidationException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
			
		} catch (IOException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}

        return writer.toString();
	}
	***/
	
	public static String floatToString(Float float1){
		
	    if(BaseUtil.objectNotNull(float1)){
	    	
	        return float1.toString();
	    }
	    
	    return null;
	} 
	
	public static Float stringToFloat(String string) throws Exception {
		
	    if(!BaseUtil.objectNotNull(string)){
	    	
	        return null;
	    }
	    
		try {
			
			return new Float(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	} 
	
	/**
	 * 棄1�7查Set是否为空 
	 * 2006-8-30
	 * haoman
	 * @param set
	 * @return
	 */
	public static boolean setNotNull(Set set){
		
		if(set != null && set.size() > 0){
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 该方法用于将传入的到期日期查询条件加丄1�7天，这样才能查出到期日期当天的记彄1�7
	 * 
	 * @param s_dateto
	 * @return
	 * @throws Exception
	 */
	public static String incDateto(String s_dateto){

		if (s_dateto != null) {

			Date dateto = null;
			
			try {
				
				dateto = BaseUtil.stringToDate(s_dateto);
				
			} catch (Exception e) { //出现格式错误返回原串，由校验程序控制
				
				return s_dateto;
			}

			if (dateto != null) {

				Calendar calendar = new GregorianCalendar();

				calendar.setTime(dateto);

				calendar.add(Calendar.DAY_OF_YEAR, 1);

				return dateToString(calendar.getTime());
			}

			else {

				return s_dateto;
			}

		}

		else {

			return null;
		}
	}
	
	/**
	 * 该方法用于将传入的到期日期查询条件加若干处1�7
	 * 
	 * @param s_dateto
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public static Date incDaysto(String s_dateto,int days){

		Date date = null;
		try {
			
			date = BaseUtil.stringToDate(s_dateto);
			
		} catch (Exception e) { //出现格式错误返回原串，由校验程序控制
			
			return null;
		}
		
		if (date != null) {

				Calendar calendar = new GregorianCalendar();

				calendar.setTime(date);

				calendar.add(Calendar.DATE, days);

				return calendar.getTime();
			
		}

		else {

			return null;
		}
	}
	
	/**
	 * 该方法用于将传入的日期加几个朄1�7
	 * 
	 * @param date
	 * @param months
	 * @return
	 * @throws Exception
	 */
	public static Date incMonthsto(Date date,int months){

		if (date != null) {

				Calendar calendar = new GregorianCalendar();

				calendar.setTime(date);

				calendar.add(Calendar.MONTH, months);

				return calendar.getTime();
			
		}

		else {

			return null;
		}
	}
	
	/**
	 * 将字符串变为大写 
	 * 2006-11-17
	 * haoman
	 * @param string
	 * @return
	 */
	public static String stringToUpperCase(String string){
		
		if((string != null) && (!string.trim().equals(""))){
			
			return string.toUpperCase();
		}
		else{
			
			return string;
		}
	}
	
	public static Date stringToOnlyTime(String string) throws Exception {
		
		if (!stringNotNull(string)){
			
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		try {
			
			return sdf.parse(string);
			
		} catch (ParseException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}

	}
	
	public static String onlyTimeToString(Date date) throws Exception {
		
		if(BaseUtil.objectNotNull(date)){
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			
			return sdf.format(date);
		}
		
		return null;

	}
	public static Double stringToDouble(String num) throws Exception {
		
		if (!stringNotNull(num)){
			
			return null;
		}
		
		try {
			
			Double dx = new Double(num);
			return dx;
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}

	}
	public static String doubleToString(Double num) throws Exception {
		
		if (num==null){
			
			return null;
		}
		
		try {
			DecimalFormat dFormat = new DecimalFormat();
			dFormat.applyPattern("####0.00");  
			
			return dFormat.format(num);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
	}

	public static String doubleToNotNullString(Double num) throws Exception {
		if (num==null){
			return "";
		}
		try {
			DecimalFormat dFormat = new DecimalFormat();
			dFormat.applyPattern("####0.00");
			return dFormat.format(num);
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			throw new Exception();
		}
	}
	
	public static int stringToInt(String string) throws Exception {
		try {
			
			return Integer.parseInt(string);
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
		
	}
	public static String intToString(int it) throws Exception {
		try {
			
			return ""+it;
			
		} catch (NumberFormatException e) {
			// TODO 自动生成 catch 坄1�7
			//e.printStackTrace();
			throw new Exception();
		}
		
	}
    
    /**
     * trim to zero
     * @param string
     * @return
     * @throws Exception
     */
    public static String trimToZero(String string) throws Exception{
        String ret = "0";
        if(null != string ){
            ret = string.trim();
            if("".equals(ret)){
                ret = "0";
            }
        }
        return ret;
    }
    
    /**
     * trim to other
     * @param string
     * @return
     * @throws Exception
     */
    public static String trimToOther(String string,String other) throws Exception{
        String ret = other;
        if(null != string ){
            ret = string.trim();
            if("".equals(ret)){
                ret = other;
            }
        }
        return ret;
    }    
    
    
    /**
     * 获得下一个数孄1�7
     * 
     * @param str
     * @return
     */
    public static String GetNextNumber(String str)
	{
		int i = new Integer(str).intValue() + 1;
		if (i / 100 >= 1)
		{
			str = i + "";
			return str;
		}
		if (i / 10 >= 1)
		{
			str = "0" + i + "";
			return str;
		}
		else
		{
			return "00" + i;
		}
	}
    
    /**
     * 将null字符串变成空丄1�7
     * @param str
     * @return
     */
    public static String nullStringToEmpty(final String str){
        String newStr = "";
        if(null == str){
            newStr = "";
        } else {
            newStr = str;
        }
        return newStr;
    }

    /**
     * 将null字符串变戄1�7"^^"
     * @param str
     * @return
     */
    public static String nullStringToUpCorner(final String str){
        String newStr = "^^";
        if(stringNotNull(str)){
        	newStr = str;
        }
        return newStr;
    }
    
    public static int objectToInt(Object obj){
    	int rtnInt=0;
    	if(obj==null || "".equals(obj)){
    		rtnInt=1;
    	}
    	else{
    		rtnInt=Integer.parseInt(obj+"");
    	}
    	return rtnInt;
    }
    
    /**
  
   
    /**
     * 获取编码的公用方法
     * 编码规则：当前时间+两位随机数
     * @return
     */
    public static String getNumber(){
    	//当前时间
    	String timeStr = timeToFormatString(new Date(), "yyyyMMddHHmmss");
    	//两位随机数
    	Random random = new Random((new Date()).getTime());
    	String randomStr = String.valueOf(Math.abs(random.nextInt())%100);
    	
    	return timeStr + randomStr;
    }
    

 

    /**
	 * 替换字符串
	 * @param s  	源字符串
	 * @param org	需要替换的字符串
	 * @param ob	替换的字符串
	 * @return
	 */
	public static String replaceStr(String s, String org, String ob) {
		String newString = "";
		int first = 0;
		while (s.indexOf(org) != -1) {
			first = s.indexOf(org);
			if (first != s.length()) {
				newString = newString + s.substring(0, first) + ob;
				s = s.substring(first + org.length(), s.length());
			}

		}

		newString = newString + s;
		return newString;
	}
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	
	/**
	 * 获取当前有效的年级列表
	 */
	public static List<String> getGrade(){
		List<String> gradeList = new ArrayList<String>();
		int currentGrade ;
		Date currentDate = new Date();
		int currentMonth = currentDate.getMonth();
		if(currentMonth>7){
			//当前月份大于7说明是选课时间是今年的第二学期，有新生进来
			currentGrade = currentDate.getYear()+1900;
		}else{
			currentGrade = currentDate.getYear()+1900-1;
		}
		for(int i=0;i<5;i++){
			gradeList.add(currentGrade-i+"");
		}
		
		return gradeList;
		
	}
	
	public static String getLikeString(String str) {
		StringBuffer result = new StringBuffer("%");
		result.append(str);
		result.append("%");
		return result.toString();
	}
	
	/**
	 * 此函数不做非空判断
	 * @param idList
	 * @return
	 */
	public static String idListToString(List<String> idList){
		Iterator<String> iter = idList.iterator();
    	StringBuffer buffer = new StringBuffer();

    	buffer.append("( ");
    	while(iter.hasNext()){
    		String key = iter.next().toString();
    		buffer.append(" '");
    		buffer.append(key);
    		buffer.append("'");
    		
    		if(iter.hasNext()){
    			buffer.append(", ");
    		}
    	}
    	
    	buffer.append(" )");
    	
    	return buffer.toString();
	}
	
	/** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
      
    /** 
     * 基于余弦定理求两经纬度距离 
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的经度 
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位km 
     * */  
    public static double GPS2Dist(double lon1, double lat1,double lon2, double lat2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
  
        double radLon1 = rad(lon1);  
        double radLon2 = rad(lon2);  
  
        if (radLat1 < 0)  
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south  
        if (radLat1 > 0)  
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north  
        if (radLon1 < 0)  
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west  
        if (radLat2 < 0)  
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south  
        if (radLat2 > 0)  
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north  
        if (radLon2 < 0)  
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west  
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);
  
        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);
  
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
        //余弦定理求夹角  
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dist = theta * EARTH_RADIUS/1000;
        return dist;  
    }
    
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	
	public static long getDistance(String startAddress, String arriveAddress){
		return Math.round(getDoubbleDistance(startAddress,arriveAddress));
	}

	public static double getDoubbleDistance(String startAddress, String arriveAddress){
		int idx = startAddress.indexOf(',');
		double sLon = Double.parseDouble(startAddress.substring(1, idx));
		double sLat = Double.parseDouble(startAddress.substring(idx + 1, startAddress.length() - 1));

		idx = arriveAddress.indexOf(',');
		double aLon = Double.parseDouble(arriveAddress.substring(1, idx));
		double aLat = Double.parseDouble(arriveAddress.substring(idx + 1, arriveAddress.length() - 1));

		return BaseUtil.GPS2Dist(sLon, sLat, aLon, aLat);
	}

	public static double getDoubbleDis(Double startLon, Double startLat, Double arriveLon, Double arriveLat){
		if (startLon == null || startLat == null || arriveLon == null || arriveLat == null)
			return 0;

		double sLon = startLon.doubleValue();
		double sLat = startLat.doubleValue();
		double aLon = arriveLon.doubleValue();
		double aLat = arriveLat.doubleValue();

		return BaseUtil.GPS2Dist(sLon, sLat, aLon, aLat);
	}

	public static long getLongDis(Double startLon, Double startLat, Double arriveLon, Double arriveLat){
		if (startLon == null || startLat == null || arriveLon == null || arriveLat == null)
			return 0;

		double sLon = startLon.doubleValue();
		double sLat = startLat.doubleValue();
		double aLon = arriveLon.doubleValue();
		double aLat = arriveLat.doubleValue();

		return Math.round(BaseUtil.GPS2Dist(sLon, sLat, aLon, aLat));
	}

	public static double roundWithDigits(double value,int digits)
	{
		BigDecimal decimalValue  = new BigDecimal(value);
		double doubleValue = decimalValue.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
		return doubleValue;
	}

	//生成随机的字符串不超过32位
	public static String getUUID(Integer lenght){
		if(lenght == null || lenght < 0 || lenght > 32){
			lenght = 32;
		}
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return  uuid.substring(0,lenght);
	}
	/*public static List<Freight.FreightBean>  freightResolve(String freight){
		String freightBeans =  " {\"freightBeans\": "+freight+"}";;
		return new Gson().fromJson(freightBeans,Freight.class).getFreightBeans();
	}*/
	/**
	 * 封装一个double转BigDecimal并保留两位小数
	 * @param d
	 * @return
	 */
	public static BigDecimal doubleToBigdecimal(Double d){
		BigDecimal amountTemp = new BigDecimal(d.toString());//扣损金额  这里有个bug当double为0.03时，转BigDecimal出现问题
		return amountTemp.setScale(2, RoundingMode.DOWN);

	}
}
