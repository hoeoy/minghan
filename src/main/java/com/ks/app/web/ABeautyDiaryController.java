package com.ks.app.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.ks.app.dao.ABeautyDiaryDao;
import com.ks.app.dao.WItemDao;
import com.ks.app.entity.ABeautyDiary;
import com.ks.app.entity.AUser;
import com.ks.app.entity.DiaryImg;
import com.ks.app.entity.WItem;
import com.ks.app.entity.WItemType;
import com.ks.app.entity.WOrder;
import com.ks.app.service.ABeautyDiaryService;
import com.ks.app.service.AUserService;
import com.ks.app.service.WItemService;
import com.ks.app.service.WItemTypeService;
import com.ks.app.service.WOrderService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.app.utils.WeixinUtil;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "/bd")
public class ABeautyDiaryController extends BaseController{
	@Autowired
	private WItemTypeService wItemTypeService;
	@Autowired
	private ABeautyDiaryService beautyDiaryService;
	@Autowired
	private WOrderService wOrderService;
	@Autowired
	private ABeautyDiaryDao beautyDiaryDao;
	@Autowired
	private WItemDao wItemDao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 跳转写日记
	 * @return
	 */
	@RequestMapping(value="/wBeautyDiary.do")
	public String wBeautyDiary(HttpServletRequest request,HttpSession session){
		session.removeAttribute("orderId");
		String id = request.getParameter("orderId");
		session.setAttribute("orderId",id);
		return  "mobile/wBeautyDiary";
	}
	/**
	 * 发布日记
	 * @param session
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/publish.do")
	public String publish(HttpSession session,HttpServletRequest req,MultipartHttpServletRequest request,Model  model){
//		String openId = (String)session.getAttribute("openId");
//		AUser user = aUserService.getUser(openId);
		
		AUser user = LoginUtil.weixinLoginUser(req);
		if(user != null){
			String orderId = (String)session.getAttribute("orderId");
			
			String Diary = req.getParameter("Diary");
			String photoA = req.getParameter("photoA");
			String photoB = req.getParameter("photoB");
			String userId = user.getId();
			String abd =  beautyDiaryService.getByOUId(userId,orderId);
			if(abd==null){
			ABeautyDiary bd = new ABeautyDiary();
			WOrder wo = wOrderService.getWOrderById(orderId);
			if(wo!=null){
				String goodsId = wo.getGoodsId();
				if(goodsId != null){
					WItem item = wItemDao.getItemById(goodsId);
					bd.setType(item.getType());
				}
			}
			bd.setOrderId(orderId);
			bd.setId(IdGen.uuid());
			bd.setAuthorMinPhoto(user.getMinPhoto());
			bd.setAuthorName(user.getName());
			bd.setDiary(Diary);
			bd.setApp(true);
			bd.setCurUserId(userId);
			bd.preInsert();
			bd.preUpdate();
			beautyDiaryService.addDiary(bd);
			beautyDiaryService.addDiaryImg(photoB,photoA,bd.getId(),userId);
			}
		}
		return seeBeautyDiary(req,model);
	}
	/**
	 * 查看所有美丽日记
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/seeBeautyDiary.do")
	public String seeBeautyDiary(HttpServletRequest req,Model model){
		List<ABeautyDiary> list = beautyDiaryService.getAll(1,"");
		if(list==null){
			list = Lists.newArrayList();
		}
			for(int i=0;i<list.size();i++){
				WOrder order = wOrderService.getWOrderById(list.get(i).getOrderId());
				if(order != null){
					list.get(i).setOrderId(order.getGoodsName());
				}else{
					list.get(i).setOrderId("");
				}
				/*AUser user = aUserService.getUserById(list.get(i).getCreateBy().getId());
				list.get(i).setAuthorMinPhoto(user.getMinPhoto());
				list.get(i).setAuthorName(user.getName());*/
				String url = list.get(i).getAuthorMinPhoto();
				if(StringUtils.isNotBlank(url)){
					list.get(i).setAuthorMinPhoto(SystemPath.getRequestPreUrl(req)+url);
				}
				String burl = list.get(i).getBefore();
				if(StringUtils.isNotBlank(burl)){
					list.get(i).setBefore(SystemPath.getRequestPreUrl(req)+burl);
				}
				String aurl = list.get(i).getAfter();
				if(StringUtils.isNotBlank(aurl)){
					list.get(i).setAfter(SystemPath.getRequestPreUrl(req)+aurl);
		}
		}
		List<WItemType> types = wItemTypeService.getAllItemType();
		model.addAttribute("types", types);
		req.setAttribute("allPage", beautyDiaryService.getBDPage());
		req.setAttribute("list", list== null ? Lists.newArrayList():list);
		return"mobile/seeBeautyDiary";
	}
	/**
	 * 查看术前相册
	 */
	@RequestMapping(value="seeBefore.do")
	public String seeBefore(HttpServletRequest req){
		String diaryId=req.getParameter("diaryId");
		List<DiaryImg> list = beautyDiaryService.getBeforeByDiaryId(diaryId);
		if(list == null){
			list = Lists.newArrayList();
		}
		for(int i=0;i<list.size();i++){
			String url = list.get(i).getImgPath();
			if(StringUtils.isNotBlank(url)){
				list.get(i).setImgPath(SystemPath.getRequestPreUrl(req)+url);
			}
		}
		req.setAttribute("list", list);
		return"mobile/Before";
	}
	/**
	 * 查看术后相册
	 * @param req
	 * @return
	 */
	@RequestMapping(value="seeAfter.do")
	public String seeAfter(HttpServletRequest req){
		String diaryId=req.getParameter("diaryId");
		List<DiaryImg> list = beautyDiaryService.getAfterByDiaryId(diaryId);
		if(list == null){
			list = Lists.newArrayList();
		}
		for(int i=0;i<list.size();i++){
			String url = list.get(i).getImgPath();
			if(StringUtils.isNotBlank(url)){
				list.get(i).setImgPath(SystemPath.getRequestPreUrl(req)+url);
			}
		}
		req.setAttribute("list", list);
		return"mobile/After";
	}
	
	@RequestMapping(value="bdLoading.do")
	public @ResponseBody ActionResponse<Object> bdLoading(HttpServletRequest req){
		String page = req.getParameter("page");
		String type = req.getParameter("type");
		System.out.println("page:"+page);
		ActionResponse<Object> returnData = new ActionResponse<>();
		List<ABeautyDiary> list = beautyDiaryService.getAll(Integer.parseInt(page),type);
		if(list == null){
			list = Lists.newArrayList();
		}
		for(int i=0;i<list.size();i++){
			WOrder order = wOrderService.getWOrderById(list.get(i).getOrderId());
			if(order != null){
				list.get(i).setOrderId(order.getGoodsName());
			}
			/*AUser user = aUserService.getUserById(list.get(i).getCreateBy().getId());
			list.get(i).setAuthorMinPhoto(user.getMinPhoto());
			list.get(i).setAuthorName(user.getName());*/
			String url = list.get(i).getAuthorMinPhoto();
			if(StringUtils.isNotBlank(url)){
				list.get(i).setAuthorMinPhoto(SystemPath.getRequestPreUrl(req)+url);
			}
			String burl = list.get(i).getBefore();
			if(StringUtils.isNotBlank(burl)){
				list.get(i).setBefore(SystemPath.getRequestPreUrl(req)+burl);
			}
			String aurl = list.get(i).getAfter();
			if(StringUtils.isNotBlank(aurl)){
				list.get(i).setAfter(SystemPath.getRequestPreUrl(req)+aurl);
			}
		}
		returnData.setData(list);
		return returnData;
	}
	@RequestMapping(value="seeOneBD.do")
	public String seeOneBD(HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		ABeautyDiary abd =  beautyDiaryService.getByOrderId(orderId);
		WOrder order = wOrderService.getWOrderById(abd.getOrderId());
		if(order != null){
			abd.setOrderId(order.getGoodsName());
		}else{
			abd.setOrderId("");
		}
		
		abd.setBefore(beautyDiaryDao.getBeforeOneById(abd.getId()));
		abd.setAfter(beautyDiaryDao.getAfterOneById(abd.getId()));
		abd.setTime(sdf.format(abd.getCreateDate()));
		
		String url =abd.getAuthorMinPhoto();
		if(StringUtils.isNotBlank(url)){
			abd.setAuthorMinPhoto(SystemPath.getRequestPreUrl(request)+url);
		}
		String burl = abd.getBefore();
		if(StringUtils.isNotBlank(burl)){
			abd.setBefore(SystemPath.getRequestPreUrl(request)+burl);
		}
		String aurl = abd.getAfter();
		if(StringUtils.isNotBlank(aurl)){
			abd.setAfter(SystemPath.getRequestPreUrl(request)+aurl);
		}
		request.setAttribute("abd",abd);
		return "mobile/bdOne";
	}
	
}
