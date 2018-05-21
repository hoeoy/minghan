package com.thinkgem.jeesite.modules.cmd.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 提现手续费比例  分销比例 VO
 * @author pc-20170905
 *
 */
public class Rate extends DataEntity<Rate>{

	private static final long serialVersionUID = 1L;

	/**值*/
	private String value;
	/**key*/
	private String type;
	/**描述*/
	private String description;
	
	public Rate() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
