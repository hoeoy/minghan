package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 套餐图片实体类
 * @author pc-20170905
 *
 */
public class WComboImage extends DataEntity<WComboImage>{

	private static final long serialVersionUID = 1L;
	
	/**套餐id*/
	private String comboId;
	/**图片排序*/
	private int sort;
	/**图片路径*/
	private String image;
	
	private WCombo combo;
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public WCombo getCombo() {
		return combo;
	}
	public void setCombo(WCombo combo) {
		this.combo = combo;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	
	public WComboImage() {
		super();
	}
	
	
}
