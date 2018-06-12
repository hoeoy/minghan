package com.ks.app.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.AUser;
import com.ks.app.entity.TokenOfSql;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;

@MyBatisDao
public interface AUserDao extends CrudDao<AUser> {
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(AUser user);
	
	/**
	 * 根据user id修改余额
	 * @param id
	 * @param accountMoney
	 * @return
	 */
	int updateUserAccountMoney(
		@Param("id")String id, 
		@Param("accountMoney")BigDecimal accountMoney);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(AUser user);
	public int updatePassword(Map<String,Object> params);
	
	public AUser getUserByMobile(Map<String,Object> params);
	
	public int deleteUserByOpenId(String openId);
	/*******************************************************/
	
	/**
	 * 用户星级变更
	 * @param id
	 * @param level
	 * @return
	 */
	int updateUserLevel(
		@Param("id")String id, 
		@Param("level")String level);
	
	/**
	 * 用户形象大使变更
	 * @param id
	 * @param userAmbassador
	 * @return
	 */
	int updateUserAmbassador(
		@Param("id")String id, 
		@Param("userAmbassador")String userAmbassador);
	
	/**
	 * 修改用户余额
	 * @param id
	 * @param money
	 * @return
	 */
	int updateAccountMoney(
		@Param("id")String id,
		@Param("money")BigDecimal money);
	
	/**
	 * 修改用户佣金
	 * @param id
	 * @param subtract
	 * @return
	 */
	int updateRewardMoney(
		@Param("id")String id, 
		@Param("reward")BigDecimal subtract);
	
	/**
	 * 累加用户佣金
	 * @param userId
	 * @param reward
	 * @return
	 */
	int AddUpRewardMoney(
		@Param("id")String userId, 
		@Param("reward")BigDecimal reward);
	
	/**
	 * 修改用户积分
	 * @param userId
	 * @param score
	 * @return
	 */
	int updateScore(
		@Param("id")String userId, 
		@Param("score")Long score);
	
	
	/**
	 * 添加新的用户
	 * @param openId
	 * @param newId
	 */
	public void addUser(@Param("newId")String newId,@Param("openId")String openId,
			@Param("updateFlagName")String updateFlag,
			@Param("updateFlagCard")String updateFlagCard,
			@Param("name")String name,@Param("photo")String photo,
			@Param("minPhoto")String minPhoto,
			@Param("province")String province,@Param("city")String city);
	/**
	 * 添加新的用户
	 * @param openId
	 * @param newId
	 */
	public void addUser(@Param("newId")String newId,@Param("openId")String openId,
			@Param("updateFlagName")String updateFlag,
			@Param("updateFlagCard")String updateFlagCard,
			@Param("name")String name,@Param("photo")String photo,
			@Param("minPhoto")String minPhoto,
			@Param("province")String province,@Param("city")String city,@Param("parentId")String parentId,@Param("date")Date date);
	/**
	 * 从数据库中获取token
	 * @return
	 */
	public TokenOfSql getToken();
	
	/**
	 * 从数据库中获取token
	 * @return
	 */
	public TokenOfSql getTokenById(@Param("id") int id);

/**
 * 更新数据库中的token
 * @param token
 * @param time
 */
	public void updateToken(@Param("token")String token,@Param("time") long time);
	/**
	 * 更新数据库中的token
	 * @param token
	 * @param time
	 */
	public void updateTokenById(@Param("token")String token,@Param("time") long time,@Param("id") int id);
	/**
	 * 更新数据库中的ticket
	 * @param ticket
	 * @param time
	 */
	public void updateTicketById(@Param("ticket")String ticket,@Param("time") long time,@Param("id") int id);
	/**
	 * 依据openId获取用户实例
	 * @param openId
	 * @return
	 */
	public AUser getUser(String openId);

	/**
	 * 更新用户信息
	 * @param name
	 * @param mobile
	 * @param birthday
	 * @param card
	 * @param mail
	 * @param province
	 * @param addr
	 * @param bankType
	 * @param bankCard
	 * @param openId
	 */
	public void updateAUser(@Param("name")String name, @Param("mobile")String mobile,
			@Param("birthday")String birthday, @Param("card")String card, @Param("mail")String mail,
			@Param("province")String province,@Param("city")String city,@Param("zone")String zone,
			@Param("addr")String addr, @Param("bankType")String bankType, 
			@Param("bankCard")String bankCard,
			@Param("updateFlagName")String updateFlagName,@Param("updateFlagCard")String updateFlagCard,
			@Param("openId")String openId); 
	/**
	 * 根据微信id得到id
	 * @param openId
	 * @return
	 */
	public String getIdByOpenId(@Param("openId")String openId);


	public List<AUser> getSubordinateById(@Param("id")String id);


	public void unsubscribe(@Param("id")String id);


	public void subscribe(@Param("openId")String openId);
	
	public void subscribe(@Param("openId")String openId,@Param("parentId")String parentId);

	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	public AUser getUserById(@Param("userId")String userId);
	public int getSubordinateNub(@Param("id")String id);


	public int countSub(@Param("id")String id);


	public List<Balance> getBalanceById(@Param("user_id")String id);

	//add by andyzhao
	public BigDecimal getBalanceSumContainChildrenById(@Param("user_id")String id);


	public void insertToken(TokenOfSql tokenSql);


	public List<Balance> getBalanceOneMonthById(@Param("id")String id, @Param("now")Date now,@Param("oneMonth")Date oneMonth);


	public void changDelFlag(@Param("id")String thisId);


	public List<String> getYWYopenId();

	public String getNameById(String userId);

	public void updateParentId(AUser user);
}