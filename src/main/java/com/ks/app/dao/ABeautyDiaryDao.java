package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.ABeautyDiary;
import com.ks.app.entity.DiaryImg;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface ABeautyDiaryDao extends CrudDao<ABeautyDiary> {

	void addDiary(ABeautyDiary bd);

	void addDiaryImg(DiaryImg di);

	List<ABeautyDiary> getAll(@Param("n")int n,@Param("s")int s,@Param("type")String type);
	
	String getBeforeOneById(@Param("id")String id);
	
	String getAfterOneById(@Param("id")String id);
	
	List<DiaryImg> getBeforeByDiaryId(@Param("id")String id);
	
	List<DiaryImg> getAfterByDiaryId(@Param("id")String id);

	List<ABeautyDiary> getAllOfUser(@Param("id")String id); 
	
	int getBDpage();

	/**
	 * 通过订单id查看是否有日记
	 * @param orderId 订单id
	 */
	Integer hasDiaryByOrderId(String orderId);

	ABeautyDiary getByOrderId(String orderId);

	String getByOUId(@Param("userId")String userId, @Param("orderId")String orderId);

}
