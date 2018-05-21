package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户Entity
 */
public class AUser extends DataEntity<AUser> {
	private static final long serialVersionUID = 1L;

	private String id;//id
	private String openId;//微信id
	private String name;	// 姓名
	private String mobile;	// 手机
	private String birthday;//生日
	private String card;//身份证
	private String mail;//邮箱
	private String province;//省
	private String city;//市
	private String zone;//区
	private String addr;//详细地址
	private String bankType;//银行类型
	private String bankCard;//银行卡号
	private String photo;	// 头像
	private String minPhoto;//缩略图
	private String userType;// 用户类型	
	private String userTypeEntity;//用户类型实体
	private String userLevel;// 用户级别
	private String userLevelEntity;//用户级别实体
	private long point;//积分	
	private BigDecimal accountMoney;//余额
	private BigDecimal rewardMoney;//佣金
	private BigDecimal backMoney;//返现
	private String updateFlagName;//是否改过名字
	private String updateFlagCard;//是否修改过信息 是-否
	private String parentId;//推荐人
	private BigDecimal performance=new BigDecimal(0);//业绩
	private String qrCode;//二维码
	private String recommendDay;//推荐日期
	private int subFlag;//下级是否有人
	private String startFlag;//是否启用
	private String userAmbassador;//形象大使 0普通 1-4 1-4
	
	
	
	




	public String getUserAmbassador() {
		return userAmbassador;
	}





	public void setUserAmbassador(String userAmbassador) {
		this.userAmbassador = userAmbassador;
	}





	public String getStartFlag() {
		return startFlag;
	}





	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}





	public int getSubFlag() {
		return subFlag;
	}





	public void setSubFlag(int subFlag) {
		this.subFlag = subFlag;
	}





	public AUser() {
		super();
	}
	
	



	public String getQrCode() {
		return qrCode;
	}



	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getRecommendDay() {
		return recommendDay;
	}
	
	
	
	public void setRecommendDay(String recommendDay) {
		this.recommendDay = recommendDay;
	}



	public String getUserLevelEntity() {
		return userLevelEntity;
	}

	public void setUserLevelEntity(String userLevelEntity) {
		this.userLevelEntity = userLevelEntity;
	}

	public String getUserTypeEntity() {
		return userTypeEntity;
	}
	public void setUserTypeEntity(String userTypeEntity) {
		this.userTypeEntity = userTypeEntity;
	}
	public BigDecimal getPerformance() {
		return performance;
	}
	public void setPerformance(BigDecimal performance) {
		this.performance = performance;
	}
	public AUser(String loginName,String password,String name,String mobile,String photo,String sign,String openId){
		this.name = name;
		this.photo = photo;
		this.openId = openId;
		this.mobile = mobile;
	}
	
	public AUser(String id){
		super(id);
	}

	public AUser(String id, String loginName){
		super(id);
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@JsonIgnore
	public String getRemarks() {
		return remarks;
	}
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}
	@JsonIgnore
	public Date getUpdateDate() {
		return updateDate;
	}
	@JsonIgnore
	public boolean getIsNewRecord() {
		return isNewRecord;
	}

	@Override
	public String toString() {
		return id;
	}


	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMinPhoto() {
		return minPhoto;
	}
	public void setMinPhoto(String minPhoto) {
		this.minPhoto = minPhoto;
	}
	public long getPoint() {
		return point;
	}
	public void setPoint(long point) {
		this.point = point;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public BigDecimal getAccountMoney() {
		return accountMoney;
	}
	public void setAccountMoney(BigDecimal accountMoney) {
		this.accountMoney = accountMoney;
	}
	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public BigDecimal getBackMoney() {
		return backMoney;
	}
	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}
	public String getUpdateFlagName() {
		return updateFlagName;
	}
	public void setUpdateFlagName(String updateFlagName) {
		this.updateFlagName = updateFlagName;
	}
	public String getUpdateFlagCard() {
		return updateFlagCard;
	}
	public void setUpdateFlagCard(String updateFlagCard) {
		this.updateFlagCard = updateFlagCard;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}