package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysConstant;

/**
 * 系统常量DAO接口
 */
@MyBatisDao
public interface SysConstantDao extends CrudDao<SysConstant> {

	void updatePhone(@Param("phone")String phone,@Param("type") String kefuPhoneColumn);
}
