package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.BalanceExDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
@Service
@Transactional
public class RechargeExService extends CrudService<BalanceExDao,Balance>{

}
