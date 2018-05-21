package com.ks.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ks.utils.EnumConstant.AdvertType;
import com.ks.utils.EnumConstant.TopState;
import com.ks.utils.EnumConstant.UserState;
import com.ks.utils.EnumConstant.VersionType;

public class ToolUtil {
	public static int getRandomNum(int maxNum,int minNum){
		 Random ne=new Random();//实例化一个random的对象ne
	     return ne.nextInt(maxNum-minNum+1)+minNum;//为变量赋随机值1000-9999
	}
	public static String getEnumLabel(String type,String value,String defaultValue){
		String label = defaultValue;
		switch (type) {
			case "6"://用户状态
				UserState[]  userStates= UserState.values();
				for (UserState userState : userStates) {
					if(userState.getKey().equals(value)){
						label = userState.getValue();
						break;
					}
				}
				break;
			case "7"://版本类型
				VersionType[] versionTypes = VersionType.values();
				for (VersionType versionType : versionTypes) {
					if(versionType.getKey().equals(value)){
						label = versionType.getValue();
						break;
					}
				}
				break;
			case "8"://广告类型
				AdvertType[] advertTypes = AdvertType.values();
				for (AdvertType advertType : advertTypes) {
					if(advertType.getKey().equals(value)){
						label = advertType.getValue();
						break;
					}
				}
				break;
			case "9"://置顶状态	
				TopState[] topStates =TopState.values();
				for (TopState topState : topStates) {
					if(topState.getKey().equals(value)){
						label = topState.getValue();
						break;
					}
				}	
				break;
			default:
			break;
		}
		return label;
	}
	public static List<EnumObject> getEnumList(String type){
		List<EnumObject> list = new ArrayList<EnumObject>();
		switch (type) {
			case "6"://用户状态
				UserState[] states = UserState.values();
				for (UserState state : states) {
					EnumObject obj = new EnumObject(state.getValue(),state.getKey());
					list.add(obj);
				}
				break;
			case "7"://版本类型
				VersionType[] versionTypes = VersionType.values();
				for (VersionType versionType : versionTypes) {
					EnumObject obj = new EnumObject(versionType.getValue(),versionType.getKey());
					list.add(obj);
				}
				break;
			case "8"://广告类型
				AdvertType[] advertTypes = AdvertType.values();
				for (AdvertType advertType : advertTypes) {
					EnumObject obj = new EnumObject(advertType.getValue(),advertType.getKey());
					list.add(obj);
				}
				break;
			case "9"://置顶状态
				TopState[] topStates =TopState.values();
				for (TopState topState : topStates) {
					EnumObject obj = new EnumObject(topState.getValue(),topState.getKey());
					list.add(obj);
				}
				break;
			default:
				break;
		}
		return list;
	}
	
	//过滤掉一些特殊字符
	public static String filter(String value){
		if(value == null){
			return "";
		}
		return value.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+","");
	}
}
