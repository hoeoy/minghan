package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 套餐合同
 * @author pc-20170905
 *
 */
public class WComboContract extends DataEntity<WComboContract>{
	
	private static final long serialVersionUID = 1L;
	
	/**套餐id*/
	private String comboId;
	/**图片排序*/
	private int sort;
	/**图片路径*/
	private String contract;
	
	private WCombo combo;

	public WComboContract() {
		super();
	}
	
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	public WCombo getCombo() {
		return combo;
	}
	public void setCombo(WCombo combo) {
		this.combo = combo;
	}
	
}
