package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MUserDao;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
@Service
@Transactional
public class MUserService extends CrudService<MUserDao, MUser>{

	public void parentsave(MUser muser) {
		dao.parentsave(muser);
	}
	
	/**
	 * 判断商品用户是否可以修改推荐人
	 * @param id
	 * @param parentId
	 * @param flag
	 * @return
	 */
	public boolean usubFlag(String id,String parentId,boolean flag){
		if(id.equals(parentId)){
			return false;
		}
		List<MUser> list = dao.getSubordinateById(id);
		if(list.size()!=0){
			for(int i =0;i<list.size();i++){
				if(parentId.equals(list.get(i).getId())){
					flag=false;
					break;
				}else{
					usubFlag(list.get(i).getId(), parentId, flag);
				}
			}
		}
		return flag;
	}

	public String getUserIdById(String userId) {
		// TODO Auto-generated method stub
		return dao.getUserIdById(userId);
	}


}
