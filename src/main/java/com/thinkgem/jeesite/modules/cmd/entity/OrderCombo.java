package com.thinkgem.jeesite.modules.cmd.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单实体类
 * @author pc-20170905
 *
 */
public class OrderCombo extends DataEntity<OrderCombo>{
	
	private static final long serialVersionUID = 1L;
	
	/**购买人*/
	private String buyId;
	/**商品id*/
	private String goodsId;
	/**商品类型*/
	private String goodsType;
	/**支付状态 0未支付 1已支付*/
	private String payState;
	/**成交价*/
	private BigDecimal dealPrice;
	/**改价人id*/
	private String changePriceId;
	/**改价时间*/
	private Date changePriceDate;
	/**扣除积分*/
	private Long chargeScore;
	/**扣除余额*/
	private BigDecimal chargeBalance;
	
	/**套餐名*/
	private String goodsName;
	private String buyName;
	
	public String getBuyName() {
		return buyName;
	}
	public void setBuyName(String buyName) {
		this.buyName = buyName;
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
	
	public OrderCombo() {
		super();
	}
	
}
