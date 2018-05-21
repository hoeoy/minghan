package com.thinkgem.jeesite.modules.cmd.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 套餐合同
 * @author pc-20170905
 *
 */
public class ComboContract extends DataEntity<ComboContract>{
	
	private static final long serialVersionUID = 1L;
	
	/**套餐id*/
	private String comboId;
	/**图片排序*/
	private int sort;
	/**图片路径*/
	private String contract;
	
	private Combo combo;

	public ComboContract() {
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
	public Combo getCombo() {
		return combo;
	}
	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	
}
