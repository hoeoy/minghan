package com.ks.utils;

import java.math.BigDecimal;

public class LocationUtils {
	public static String DEFAULT_UNIT_1="0";
    private static double EARTH_RADIUS = 6378.138;    
    
    private static double rad(double d) {    
        return d * Math.PI / 180.0;    
    }    
    
    /**   
     * 通过经纬度获取距离(单位：米)   
     * @param lat1   
     * @param lng1   
     * @param lat2   
     * @param lng2   
     * @return   
     */    
    public static double getDistance(double lat1, double lng1, double lat2,    
                                     double lng2) {    
        double radLat1 = rad(lat1);    
        double radLat2 = rad(lat2);    
        double a = radLat1 - radLat2;    
        double b = rad(lng1) - rad(lng2);    
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)    
                + Math.cos(radLat1) * Math.cos(radLat2)    
                * Math.pow(Math.sin(b / 2), 2)));    
        s = s * EARTH_RADIUS;    
        s = Math.round(s * 10000d) / 10000d;    
        s = s*1000;  
        return s;    
    }  
    
    public static String getRangeStr(double range){
    	String result = "";
    	if(range > 1000){
    		result = new BigDecimal(range/1000.0).setScale(2, BigDecimal.ROUND_HALF_UP)+"公里";
    	}else{
    		result = new BigDecimal(range).setScale(2, BigDecimal.ROUND_HALF_UP)+"米";
    	}
    	return result;
    }
    
    public static String SQL_DISTANCE="ROUND("+EARTH_RADIUS
    		+"* 2 * ASIN(" 
    		+"SQRT(" 
    		+"POW(SIN((A1 * PI() / 180 - A2 * PI() / 180) / 2),2) "
    		+"+ COS(A1 * PI() / 180) * COS(A2 * PI() / 180) * POW(SIN((B1 * PI() / 180 - B2 * PI() / 180) / 2),2)"
    		+")"
    		+ ") * 1000)";
    
    public static String getSQLDistance(double lat1,double langt1,String targetLatField,String targetLongtField){
    	String distance = SQL_DISTANCE.replaceAll("A1", String.valueOf(lat1))
    			.replaceAll("B1", String.valueOf(langt1))
    			.replaceAll("A2", targetLatField)
    			.replaceAll("B2", targetLongtField);
    	String resultSql = " (CASE WHEN "+targetLatField+" is null or "+targetLongtField+" is null THEN 0 ELSE "+distance+"  END)";
    	return resultSql;
    }
} 
