package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.PosterDao;
import com.thinkgem.jeesite.modules.sys.entity.Poster;
@Service
@Transactional(rollbackFor=Exception.class)
public class PosterService extends CrudService<PosterDao, Poster>{

}
