package com.ks.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.lang.StringUtil;
import com.google.common.collect.Lists;
import com.ks.app.dao.AMUserDao;
import com.ks.app.entity.AMBrokerage;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMTakeOut;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WBrokerage;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.MBrokerageType;
import com.ks.utils.EnumConstant.UserLevel;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Service
@Transactional
public class AMUserService extends CrudService<AMUserDao, AMUser>{
@Autowired
private AUserService aUserService;
@Autowired
private AMOrderService amOrderService;
@Autowired
private SysConstantService sysConstantService;
@Autowired
private AMBrokerageService amBrokerageService;
private SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
private DecimalFormat df = new DecimalFormat("#.00");

	public AMUser getMuserByUserId(String userId) {
		AMUser user = dao.getMuserByUserId(userId);
		if(user!=null){
			user.setmUserLevel(UserLevel.userLevel(user.getmUserLevel()));
			String parentId = user.getParentId();
			if(StringUtils.isNoneBlank(parentId)){
				AMUser u = dao.get(parentId);
				if(u!=null){
					if(StringUtils.isNoneBlank(u.getUserId())){
						AUser uuu = aUserService.getUserById(u.getUserId());
						if(uuu!=null){
							user.setParentName(uuu.getName());
						}
					}
				}
			}
		}else{
			user = new AMUser();
			user.setUserId(userId);
			save(user);
			user = dao.getMuserByUserId(userId);
		}
		return user;
	}

	public void allterRM(AMUser user) {
		dao.allterRM(user);
		
	}

	public List<AMUser> getByParentId(String id) {
		List<AMUser> list = dao.getByParentId(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	
	public BigDecimal getPerformance(String id,BigDecimal bd){
		List<AMUser> list = getByParentId(id);
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getId();
			List<AMOrder> amtoList = amOrderService.getZGOrder(userId);
			for(int j = 0;j<amtoList.size();j++){
				BigDecimal money = amtoList.get(j).getGoodsPrice();
				bd = bd.add(money);
			}
			bd=getPerformance(userId,bd);
		}
		return bd;
	}
	
	/**
	 * 获取团队
	 * @param userId
	 * @return
	 */
	public AMUser[] getGroup(String userId) {
		//定义长度为11的容器
		AMUser[] group = new AMUser[21];
		//获取用户的上级，根据其星级，将其放入容器
		AMUser user = dao.get(userId);
		//没有上级则返回null，其团队为空，奖励金公司保留
		if(StringUtils.isBlank(user.getParentId())){
			return group;
		}
		AMUser userStart = dao.get(user.getParentId());
		if(userStart == null){//若根据父id查不到用户则没有上级
			return group;
		}
		int start = Integer.parseInt(userStart.getmUserLevel());
		group[start] = userStart;
		//若其上级为10星，则其1个人获得全部奖励金
		if(start == 20){
			return group;
		}
		//如果user的上上级为空则只返回group，内只有1个元素
		if(StringUtils.isBlank(userStart.getParentId())){
			return group;
		}
		if(dao.get(userStart.getParentId()) == null){
			return group;
		}
		
		//星级缓存
		int LevelIndex = start;
		//上级id缓存
		String parentIdIndex = userStart.getParentId();
		
		//获取团队内其他成员
		do{
			AMUser u = dao.get(parentIdIndex);
			if(StringUtils.isBlank(u.getParentId()) || dao.get(u.getParentId())==null){
				//u没有上级，但其本身要加入group，再return
				if(Integer.parseInt(u.getmUserLevel()) > LevelIndex){
					LevelIndex = Integer.parseInt(u.getmUserLevel());
					group[LevelIndex] = u;
					return group;
				}else{
					return group;
				}
			}
			parentIdIndex = u.getParentId();
			if(StringUtils.isBlank(u.getmUserLevel())){
				continue;
			}
			//上级星级高于下级则放入容器
			if(Integer.parseInt(u.getmUserLevel()) > LevelIndex){
				LevelIndex = Integer.parseInt(u.getmUserLevel());
				group[LevelIndex] = u;
			} 
		}while(LevelIndex < 20);
		return group;
	}
	
	/**
	 * 处理团队
	 * @param user 消费用户
	 * @param ID 消费订单号
	 * @param group
	 * @param actualPay 参与奖励的金额
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void disposeGroup(AMUser user, String id, AMUser[] group, BigDecimal actualPay){
		//团队为空则无人获得奖励金
		if(group == null){
			return ;
		}
		AMOrder order = amOrderService.getByOrderNo(id);
		//未分配奖励金份数
		int n = 20;
		//已分配奖励金份数
		int m = 0;
		//每次循环中用户的星级
		int level;
		//获取团队玩法比例(每一级)
		BigDecimal goods_star = new BigDecimal(sysConstantService.getCustomMGoodsStar());
		for(int i=0; i<group.length; i++){
			if(group[i] == null){
				continue;
			}
			level = Integer.parseInt(group[i].getmUserLevel());
			String userId = group[i].getId();
			AMUser u = dao.get(userId);
				BigDecimal bd = new BigDecimal(level-m).multiply(actualPay).multiply(goods_star);
				bd = new BigDecimal(df.format(bd));
				//入表
				AMBrokerage amb = new AMBrokerage();
				amb.setUserId(u.getId());
				amb.setConsumerId(user.getId());
				amb.setGoodsId(order.getGoodsId());
				amb.setGoodsName(order.getGoodsName());
				amb.setGoodsPrice(order.getGoodsPrice());
				amb.setMoney(bd);
				amb.setType(MBrokerageType.ZERO.getKey());
				String awardLevel = u.getmUserLevel();
				amb.setAwardLevel(awardLevel);
				amBrokerageService.save(amb);
				//佣金累加
				bd = u.getmRewardMoney().add(bd);
				u.setmRewardMoney(bd);
				dao.allterRM(u);
				m = level;//已分配的=星级
				if(m == 0){//第一次分奖励
					n = n - level;
				}else{
					if(n >= level){
						n = n - level + m;//未分配的=未分配-星级
					}else{
						n = level - m;
					}
				}
		}
	}

	public void buyZiGeSucceed(String userId,String orderNo){
		dao.buyZiGeSucceed(userId);
		//十星团队和二级分销
		//二级分销
		AMUser user = dao.get(userId);
		if(user!=null){
			String parentId = user.getParentId();
			if(StringUtils.isNoneBlank(parentId)){
				AMOrder order = amOrderService.getByOrderNo(orderNo);
				if(order!=null){
				AMUser u = dao.get(parentId);
				if(u!=null){
				String oneF = sysConstantService.getCustomZGLevelOne();
				BigDecimal bd = u.getmRewardMoney().add(new BigDecimal(oneF));
				u.setmRewardMoney(bd);
				dao.allterRM(u);
				AMBrokerage amb = new AMBrokerage();
				amb.setUserId(u.getId());
				amb.setConsumerId(user.getId());
				amb.setGoodsId(order.getGoodsId());
				amb.setGoodsName(order.getGoodsName());
				amb.setGoodsPrice(order.getGoodsPrice());
				amb.setMoney(new BigDecimal(oneF));
				amb.setType(MBrokerageType.ONE.getKey());
				amBrokerageService.save(amb);
				parentId = u.getParentId();
				if(StringUtils.isNoneBlank(parentId)){
					u = dao.get(parentId);
					if(u!=null){
						String TwoF = sysConstantService.getCustomZGLevelTwo();
						bd = u.getmRewardMoney().add(new BigDecimal(TwoF));
						u.setmRewardMoney(bd);
						dao.allterRM(u);
						amb = new AMBrokerage();
						amb.setUserId(u.getId());
						amb.setConsumerId(user.getId());
						amb.setGoodsId(order.getGoodsId());
						amb.setGoodsName(order.getGoodsName());
						amb.setGoodsPrice(order.getGoodsPrice());
						amb.setMoney(new BigDecimal(TwoF));
						amb.setType(MBrokerageType.TWO.getKey());
						amBrokerageService.save(amb);
					}
				}
				}
				}
				//十星玩法
				//抓取团队
				AMUser[] group = getGroup(userId);
				String goodsStar = sysConstantService.getCustomMGoodsStar();
				double index = 20*Double.parseDouble(goodsStar);
				BigDecimal actualPay = order.getGoodsPrice();
				System.out.println("**********************************************奖励金额："+actualPay);
				System.out.println("**********************************************团队人数："+group.length);
				//处理团队
				disposeGroup(user, orderNo, group, actualPay);
			}
		}
		
		
		
	}

	public int getSubNum(String id) {
		int index = dao.getSubNum(id);
		return index;
	}


	public void updateParentId(AMUser user) {
		dao.updateParentId(user);
		
	}

	public boolean upDateParentIdFlag(String id, String parentId, boolean b) {
		AMUser user = dao.get(id);
		if(user!=null){
			if(StringUtils.isNoneBlank(user.getParentId())){
				return false;
			}
		}else{
			return false;
		}
		if(id.equals(parentId)){
			return false;
		}
		List<AMUser> list = dao.getByParentId(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getParentId().equals(id)){
				b = false;
				return b;
			}else{
				upDateParentIdFlag(list.get(i).getId(), parentId, b);
			}
		}
		return b;
	}

	public void addDiscountsNum(int discountsNum, String userId) {
		int index = dao.getDiscountsNum(userId);
		index = index+discountsNum;
		dao.updateDiscountsNum(index,userId);
	}


}
