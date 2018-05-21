/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	public List<Balance> getBalanceById(String id);

	public void offUser(@Param("id")String id);

	public void startUser(@Param("id")String id);
	
	public Role getSix();

	public void updateParentId(@Param("id")String id, @Param("parentId")String parentId);

	/**
	 * 根据登录账户名获取用户
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);

	public List<User> getYWY();

	public List<User> findUserByName(@Param("name")String name);

	/**
	 * 根据user id修改余额
	 * @param id
	 * @param accountMoney
	 * @return
	 */
	int updateUserAccountMoney(
		@Param("id")String id, 
		@Param("accountMoney")BigDecimal accountMoney);

	public void updateForeach(@Param("map")Map<String, User> map);

	public void updateAccountMoney(User user);

	public void updateRewardMoney(User user);

	public String getNameById(String id);

}
