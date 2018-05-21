package com.ks.utils;

import java.util.UUID;

public class OrderCodeUtil extends Thread{
	public static String getNo(String userId){
		int hashCodeV = UUID.randomUUID().toString().hashCode();
	    if (hashCodeV < 0) {//有可能是负数
	        hashCodeV = -hashCodeV;
	    }
	    String machId = "1";
	    // 0 代表前面补充0
	    // 15代表长度为15
	    // d 代表参数为正数型
	    String orderId=machId+String.format("%015d", hashCodeV);
	    return orderId;
	}
}

	    			