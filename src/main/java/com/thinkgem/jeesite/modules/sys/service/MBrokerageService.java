package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MBrokerageDao;
import com.thinkgem.jeesite.modules.sys.entity.MBrokerage;

@Service
@Transactional
public class MBrokerageService extends CrudService<MBrokerageDao, MBrokerage>{

}
