package com.ks.app.dao;

import javax.mail.MailSessionDefinition;

import com.ks.app.entity.AMRecord;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AMRecordDao extends CrudDao<AMRecord>{

}
