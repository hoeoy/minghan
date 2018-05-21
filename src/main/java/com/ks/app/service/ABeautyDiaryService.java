package com.ks.app.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.ks.app.dao.ABeautyDiaryDao;
import com.ks.app.entity.ABeautyDiary;
import com.ks.app.entity.DiaryImg;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Config;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
@Service
@Transactional
public class ABeautyDiaryService extends CrudService<ABeautyDiaryDao, ABeautyDiary>{
@Autowired
ABeautyDiaryDao beautyDiaryDao;
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	public void addDiary(ABeautyDiary bd) {
		beautyDiaryDao.addDiary(bd);
		
	}
	public void addDiaryImg(String photoA,String photoB,String diaryId,String userId){
		String[] pA = photoA.split(",");
		String[] pB = photoB.split(",");
		if(pA!=null&&pA.length>0){
			for(int i=0;i<pA.length;i++){
				DiaryImg di = new DiaryImg();
				String id = IdGen.uuid();
				di.setId(id);
				di.setDiaryId(diaryId);
				di.setImgPath(pA[i]);
				di.setFlag("0");
				di.setSort(i);
				di.setCurUserId(userId);
				di.preInsert();
				di.preUpdate();
				beautyDiaryDao.addDiaryImg(di);
			}
		}
		if(pB!=null&&pB.length>0){
			for(int i=0;i<pB.length;i++){
				DiaryImg di = new DiaryImg();
				String id = IdGen.uuid();
				di.setId(id);
				di.setDiaryId(diaryId);
				di.setImgPath(pB[i]);
				di.setFlag("1");
				di.setSort(i);
				di.setCurUserId(userId);
				di.preInsert();
				di.preUpdate();
				beautyDiaryDao.addDiaryImg(di);
			}
		}
	}
	public List<ABeautyDiary> getAll(int index,String type) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List<ABeautyDiary> list = beautyDiaryDao.getAll(n,s,type);
		if(list == null){
			list = Lists.newArrayList();
		}
		for(int i = 0;i<list.size();i++){
			list.get(i).setBefore(beautyDiaryDao.getBeforeOneById(list.get(i).getId()));
			list.get(i).setAfter(beautyDiaryDao.getAfterOneById(list.get(i).getId()));
			list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
		}
		return list;
	}
	
	public List<ABeautyDiary> getAllofUesr(String id) {
		List<ABeautyDiary> list = beautyDiaryDao.getAllOfUser(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i = 0;i<list.size();i++){
			list.get(i).setBefore(beautyDiaryDao.getBeforeOneById(list.get(i).getId()));
			list.get(i).setAfter(beautyDiaryDao.getAfterOneById(list.get(i).getId()));
			list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
		}
		return list;
	}
	public List<DiaryImg> getBeforeByDiaryId(String diaryId) {
		List<DiaryImg> list = beautyDiaryDao.getBeforeByDiaryId(diaryId);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	public List<DiaryImg> getAfterByDiaryId(String diaryId) {
		List<DiaryImg> list = beautyDiaryDao.getAfterByDiaryId(diaryId);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public int getBDPage(){
		int allPage;
		int index =beautyDiaryDao.getBDpage();
		allPage=index/WeixinUtil.BeautyDiaryCount;
		if(index%WeixinUtil.BeautyDiaryCount!=0){
			allPage+=1;
		}
		return allPage;
	}
	public ABeautyDiary getByOrderId(String orderId) {
		
		return beautyDiaryDao.getByOrderId(orderId);
	}
	public String getByOUId(String userId, String orderId) {
		// TODO Auto-generated method stub
		return beautyDiaryDao.getByOUId(userId,orderId);
	}
}
