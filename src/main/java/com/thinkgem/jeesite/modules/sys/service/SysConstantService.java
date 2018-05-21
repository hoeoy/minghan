package com.thinkgem.jeesite.modules.sys.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.SysConstantDao;
import com.thinkgem.jeesite.modules.sys.entity.SysConstant;

/**
 * 系统常量Service
 */
@Service
@Transactional
public class SysConstantService extends CrudService<SysConstantDao, SysConstant> {
	
	public List<SysConstant> findListByType(String type){
		SysConstant dict = new SysConstant();
		dict.setType(type);
		return dao.findList(dict);
	}

	@Transactional(readOnly = false)
	public void save(SysConstant dict) {
		super.save(dict);
	}

	@Transactional(readOnly = false)
	public void delete(SysConstant dict) {
		super.delete(dict);
	}
	public SysConstant  getCustomPhoneDict(){
		List<SysConstant> phones = this.findListByType(Constant.KEFU_PHONE_COLUMN);
		if(phones != null && phones.size()>0){
			return phones.get(0);
		}
		return null;
	}
	public String getCustomPhone(){
		SysConstant phone = getCustomPhoneDict();
		if(phone != null){
			String value = phone.getValue();
			return StringUtils.isNotBlank(value) ? value:"";
		}
		return "";
	}
	
	//一级形象大使项目一级分销
	public SysConstant getAmbassadorOneItemLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.AMBASSADOR_ONE_ITEM_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getAmbassadorOneItemLevelOne(){
		SysConstant aoilo = getAmbassadorOneItemLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//二级形象大使项目一级分销
	public SysConstant getAmbassadorTwoItemLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.AMBASSADOR_TWO_ITEM_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getAmbassadorTwoItemLevelOne(){
		SysConstant aoilo = getAmbassadorTwoItemLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//三级形象大使项目一级分销
	public SysConstant getAmbassadorThreeItemLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.AMBASSADOR_THREE_ITEM_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getAmbassadorThreeItemLevelOne(){
		SysConstant aoilo = getAmbassadorThreeItemLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//四级形象大使项目一级分销
	public SysConstant getAmbassadorFourItemLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.AMBASSADOR_FOUR_ITEM_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getAmbassadorFourItemLevelOne(){
		SysConstant aoilo = getAmbassadorFourItemLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//项目二级分销
	public SysConstant getItemLevelTwoList(){
		List<SysConstant> aoilo = this.findListByType(Constant.ITEM_LEVEL_TWO);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getItemLevelTwo(){
		SysConstant aoilo = getItemLevelTwoList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//套餐一级分销
	public SysConstant getComboLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.COMBO_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getComboLevelOne(){
		SysConstant aoilo = getComboLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//项目星级玩法每级分销比例
	public SysConstant getItemStarList(){
		List<SysConstant> aoilo = this.findListByType(Constant.ITEM_STAR);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getItemStar(){
		SysConstant aoilo = getItemStarList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	//手续费
	public SysConstant  getCustomShouXuFeiDict(){
		List<SysConstant> phones = this.findListByType(Constant.SHOU_XU_FEI);
		if(phones != null && phones.size()>0){
			return phones.get(0);
		}
		return null;
	}
	public String getCustomShouXuFei(){
		SysConstant shouxufei = getCustomShouXuFeiDict();
		if(shouxufei != null){
			String value = shouxufei.getValue();
			return StringUtils.isNotBlank(value) ? value:"";
		}
		return "";
	}
	
	//商品手续费
		public SysConstant  getCustomGoodsShouXuFeiDict(){
			List<SysConstant> phones = this.findListByType(Constant.GOODS_SHOU_XU);
			if(phones != null && phones.size()>0){
				return phones.get(0);
			}
			return null;
		}
		public String getCustomGoodsShouXuFei(){
			SysConstant shouxufei = getCustomGoodsShouXuFeiDict();
			if(shouxufei != null){
				String value = shouxufei.getValue();
				return StringUtils.isNotBlank(value) ? value:"";
			}
			return "";
		}
		
		//修改电话
		public void updatePhone(String phone){
			dao.updatePhone(phone,Constant.KEFU_PHONE_COLUMN);
		}
		
		//面膜商品分销
		public SysConstant getCustomGoodsSubDict(){
			List<SysConstant> phones = this.findListByType(Constant.M_GOODS_SUB);
			if(phones != null && phones.size()>0){
				return phones.get(0);
			}
			return null;
		}
		public String getCustomGoodsSub(){
			SysConstant shouxufei = getCustomGoodsSubDict();
			if(shouxufei != null){
				String value = shouxufei.getValue();
				return StringUtils.isNotBlank(value) ? value:"";
			}
			return "";
		}
		//商品资格一级分销
		public SysConstant  getCustomZGLevelOneDict(){
			List<SysConstant> phones = this.findListByType(Constant.M_ZG_LEVEL_ONE);
			if(phones != null && phones.size()>0){
				return phones.get(0);
			}
			return null;
		}
		public String getCustomZGLevelOne(){
			SysConstant shouxufei = getCustomZGLevelOneDict();
			if(shouxufei != null){
				String value = shouxufei.getValue();
				return StringUtils.isNotBlank(value) ? value:"";
			}
			return "";
		}
		//商品资格二级分销
		public SysConstant  getCustomZGLevelTwoDict(){
			List<SysConstant> phones = this.findListByType(Constant.M_ZG_LEVEL_TWO);
			if(phones != null && phones.size()>0){
				return phones.get(0);
			}
			return null;
		}
		public String getCustomZGLevelTwo(){
			SysConstant shouxufei = getCustomZGLevelTwoDict();
			if(shouxufei != null){
				String value = shouxufei.getValue();
				return StringUtils.isNotBlank(value) ? value:"";
			}
			return "";
		}
		//商品资格星级分销
		public SysConstant  getCustomMGoodsStarDict(){
			List<SysConstant> phones = this.findListByType(Constant.M_GOODS_STAR);
			if(phones != null && phones.size()>0){
				return phones.get(0);
			}
			return null;
		}
		public String getCustomMGoodsStar(){
			SysConstant shouxufei = getCustomMGoodsStarDict();
			if(shouxufei != null){
				String value = shouxufei.getValue();
				return StringUtils.isNotBlank(value) ? value:"";
			}
			return "";
		}
		//面膜优惠数量
				public SysConstant  getDISCOUNTS_NUMDict(){
					List<SysConstant> phones = this.findListByType(Constant.DISCOUNTS_NUM);
					if(phones != null && phones.size()>0){
						return phones.get(0);
					}
					return null;
				}
				public String getDISCOUNTS_NUM(){
					SysConstant shouxufei = getDISCOUNTS_NUMDict();
					if(shouxufei != null){
						String value = shouxufei.getValue();
						return StringUtils.isNotBlank(value) ? value:"";
					}
					return "";
				}

	//店家对店家（上下级均为4级形象大使）
	public SysConstant getShopToShopList(){
		List<SysConstant> aoilo = this.findListByType(Constant.SHOP_TO_SHOP);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getShopToShop(){
		SysConstant aoilo = getShopToShopList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//上上级为总监，一级分销
	public SysConstant getDirectorLevelOneList(){
		List<SysConstant> aoilo = this.findListByType(Constant.DIRECTOR_LEVEL_ONE);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getDirectorLevelOne(){
		SysConstant aoilo = getDirectorLevelOneList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//上上级为总监，二级分销
	public SysConstant getDirectorLevelTwoList(){
		List<SysConstant> aoilo = this.findListByType(Constant.DIRECTOR_LEVEL_TWO);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getDirectorLevelTwo(){
		SysConstant aoilo = getDirectorLevelTwoList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//上上级为咨询师，二级分销
	public SysConstant getConsultantLevelTwoList(){
		List<SysConstant> aoilo = this.findListByType(Constant.CONSULTANT_LEVEL_TWO);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getConsultantLevelTwo(){
		SysConstant aoilo = getConsultantLevelTwoList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	//上上级为店家，上级为店家，买家非店家
	public SysConstant getNoShopLevelTwoList(){
		List<SysConstant> aoilo = this.findListByType(Constant.NO_SHOP_LEVEL_TWO);
		if(aoilo != null && aoilo.size()>0){
			return aoilo.get(0);
		}
		return null;
	}
	public BigDecimal getNoShopLevelTwo(){
		SysConstant aoilo = getNoShopLevelTwoList();
		if(aoilo != null){
			String value = aoilo.getValue();
			return StringUtils.isNotBlank(value) ? new BigDecimal(value):new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
}
