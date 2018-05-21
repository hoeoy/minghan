package com.ks.app.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WItem;
import com.ks.app.entity.WItemImage;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WItemDao extends CrudDao<WItem>{

	/**
	 * 项目销售次数+1
	 * @param id
	 * @return
	 */
	int addCount(String id);
	
	/**
	 * 分页查询项目信息
	 * @param page
	 * @param size 
	 * @return
	 */
	List<WItem> getItemByPage(
			@Param("page")Integer page, 
			@Param("size")Integer size);

	/**
	 * 查询项目详情
	 * @param id
	 * @return
	 */
	WItem getItemById(String id);

	/**
	 * 通过项目id获取项目滚动图
	 * @param id
	 * @return
	 */
	List<WItemImage> getItemImages(String id);

	BigDecimal getItemMaxCurrentPrice(String itemId);
	BigDecimal getItemMinCurrentPrice(String itemId);
	BigDecimal getItemMaxOldPrice(String itemId);
	BigDecimal getItemMinOldPrice(String itemId);

	/**
	 * 根据分类获取项目
	 * @param type
	 * @return
	 */
	List<WItem> getItemByType(String type);

	/**
	 * 根据分类获分页取项目
	 * @param type
	 * @param n
	 * @param s
	 * @return
	 */
	List<WItem> getItemByTypeByPage(
		@Param("type")String type, 
		@Param("n")int n, 
		@Param("s")int s);

	/**
	 * 获取项目有哪些类型
	 * @return
	 */
	List<String> getItemTypes();

}
