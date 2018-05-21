package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.BeautyDiaryDao;
import com.thinkgem.jeesite.modules.sys.dao.DiaryImageDao;
import com.thinkgem.jeesite.modules.sys.entity.BeautyDiary;
import com.thinkgem.jeesite.modules.sys.entity.DiaryImage;
@Service
@Transactional
public class BeautyDiaryService extends  CrudService<BeautyDiaryDao,BeautyDiary>{

	@Autowired
	private BeautyDiaryDao beautyDiaryDao;
	@Autowired
	private DiaryImageDao diaryImageDao;

	public void deleteById(String id) {
		beautyDiaryDao.deleteById(id);
		beautyDiaryDao.deleteImg(id);
		
	}

	/**
	 * 根据日记id获取其全部图片
	 * @param diaryId
	 * @return
	 */
	public List<DiaryImage> getDiaryImagesByDiaryId(String diaryId) {
		List<DiaryImage> images = diaryImageDao.getImagesByDiaryId(diaryId);
		if(images == null || images.size() < 1){
			images = Lists.newArrayList();
		}
		return images;
	}

	/**
	 * 将日记的全部图片分成2个list，分别装术前，术后
	 * @param images
	 * @return
	 */
	public Map<String, List<DiaryImage>> separateImages(List<DiaryImage> images) {
		Map<String, List<DiaryImage>> map = new HashMap<String, List<DiaryImage>>();
		List<DiaryImage> before = new ArrayList<DiaryImage>();
		List<DiaryImage> after = new ArrayList<DiaryImage>();
		if(images != null && images.size() > 0){
			for(DiaryImage img : images){
				if("0".equals(img.getFlag())){//术前
					before.add(img);
				}else if("1".equals(img.getFlag())){//术后
					after.add(img);
				}
			}
			map.put("before", before);
			map.put("after", after);
		}else{
			images = Lists.newArrayList();
			map.put("before", images);
			map.put("after", images);
		}
		return map;
	}
}
