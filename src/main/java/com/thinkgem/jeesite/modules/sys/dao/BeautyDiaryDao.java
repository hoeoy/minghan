package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.BeautyDiary;

@MyBatisDao
public interface BeautyDiaryDao extends CrudDao<BeautyDiary>{

	void deleteById(@Param("id")String id);

	void deleteImg(@Param("id")String id);

}
