package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MRecordDao;
import com.thinkgem.jeesite.modules.sys.entity.MRecord;
@Service
@Transactional
public class MRecordService extends CrudService<MRecordDao, MRecord>{

}
