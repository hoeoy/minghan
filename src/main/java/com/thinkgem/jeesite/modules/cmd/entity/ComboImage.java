package com.thinkgem.jeesite.modules.cmd.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 套餐图片实体类
 * @author pc-20170905
 *
 */
public class ComboImage extends DataEntity<ComboImage>{

	private static final long serialVersionUID = 1L;
	
	/**套餐id*/
	private String comboId;
	/**图片排序*/
	private int sort;
	/**图片路径*/
	private String image;
	
	private Combo combo;
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Combo getCombo() {
		return combo;
	}
	public void setCombo(Combo combo) {
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
	
	public ComboImage() {
		super();
	}
	
	
}
