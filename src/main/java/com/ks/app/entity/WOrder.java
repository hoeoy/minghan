package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单实体类
 * @author pc-20170905
 *
 */
public class WOrder extends DataEntity<WOrder>{
	
	private static final long serialVersionUID = 1L;
	
	/**购买人*/
	private String buyId;
	/**套餐/项目id*/
	private String goodsId;
	/**套餐/项目类型 商品类型：1套餐 2项目*/
	private String goodsType;
	/**套餐/项目名*/
	private String goodsName;
	/**支付状态 0未支付 1已支付*/
	private String payState;
	/**审核状态 0未审核 1已审核*/
	private String checkState;
	/**下单价*/
	private BigDecimal orderPrice;
	/**成交价*/
	private BigDecimal dealPrice;
	/**改价人id*/
	private String changePriceId;
	/**改价世间*/
	private Date changePriceDate;
	/**扣除积分*/
	private Long chargeScore;
	/**扣除余额*/
	private BigDecimal chargeBalance;
	
	//购买人姓名
	private String buyName;
	//字符串形式日期
	private String stringCreateDate;
	//审核人
	private String checkName;
	//门店名
	private String shopName;
	//是否写过日记 0无 1有
	private String hasDiary;
	
	public String getHasDiary() {
		return hasDiary;
	}
	public void setHasDiary(String hasDiary) {
		this.hasDiary = hasDiary;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getStringCreateDate() {
		return stringCreateDate;
	}
	public void setStringCreateDate(String stringCreateDate) {
		this.stringCreateDate = stringCreateDate;
	}
	public String getBuyName() {
		return buyName;
	}
	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}
	
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getBuyId() {
		return buyId;
	}
	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}
	public String getChangePriceId() {
		return changePriceId;
	}
	public void setChangePriceId(String changePriceId) {
		this.changePriceId = changePriceId;
	}
	public Date getChangePriceDate() {
		return changePriceDate;
	}
	public void setChangePriceDate(Date changePriceDate) {
		this.changePriceDate = changePriceDate;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public BigDecimal getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}
	public Long getChargeScore() {
		return chargeScore;
	}
	public void setChargeScore(Long chargeScore) {
		this.chargeScore = chargeScore;
	}
	public BigDecimal getChargeBalance() {
		return chargeBalance;
	}
	public void setChargeBalance(BigDecimal chargeBalance) {
		this.chargeBalance = chargeBalance;
	}
	
	public WOrder() {
		super();
	}
	
}
