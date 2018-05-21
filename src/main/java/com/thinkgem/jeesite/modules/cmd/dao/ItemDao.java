package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.ItemImage;

@MyBatisDao
public interface ItemDao extends CrudDao<Item>{

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
	List<Item> getItemByPage(
			@Param("page")Integer page, 
			@Param("size")Integer size);

	/**
	 * 查询项目详情
	 * @param id
	 * @return
	 */
	Item getItemById(String id);

	/**
	 * 删除商品的关联图片
	 * @param id
	 */
	void deleteItemImages(String itemId);

	void insertItemImage(List<ItemImage> images);

	/**
	 * 获取滚动图地址
	 * @param id 项目id
	 */
	List<ItemImage> getImagesById(String itemId);

}
