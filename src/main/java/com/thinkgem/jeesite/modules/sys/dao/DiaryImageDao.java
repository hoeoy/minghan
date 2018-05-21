package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.DiaryImage;

@MyBatisDao
public interface DiaryImageDao extends CrudDao<DiaryImage>{

	/**
	 * 根据日记id获取其全部图片
	 * @param diaryId
	 * @return
	 */
	List<DiaryImage> getImagesByDiaryId(String diaryId);

}
