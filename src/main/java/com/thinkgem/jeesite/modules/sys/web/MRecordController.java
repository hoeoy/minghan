package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.DetailValue;
import com.thinkgem.jeesite.modules.sys.entity.MRecord;
import com.thinkgem.jeesite.modules.sys.service.MRecordService;
@Controller
@RequestMapping(value = "${adminPath}/sys/mrecord")
public class MRecordController extends BaseController{
@Autowired
private MRecordService mRecordService;
private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

@RequiresPermissions("sys:morder:edit")
@RequestMapping(value="list")
public String list (MRecord record,HttpServletRequest request, HttpServletResponse response,Model model) throws ParseException{
	String start = request.getParameter("startTime");
	String end =request.getParameter("endTime");
	Map<String,DetailValue> map = new HashMap<String,DetailValue>();
	BigDecimal allMoney = new BigDecimal(0);
	if(start!=null&&!("".equals(start))){
		Date startTime =sdf1.parse(start);
		record.setStartTime(startTime);
	}
	if(end!=null&&!("".equals(end))){
		Date endTime = sdf1.parse(end);
		record.setEndTime(endTime);
	}
	Page<MRecord> page = mRecordService.findPage(new Page<MRecord>(request,response), record);
	List<MRecord> list = page.getList();
	for(int i = 0; i<list.size();i++){
		String time = sdf1.format(list.get(i).getCreateDate());
		list.get(i).setTime(time);
		allMoney = allMoney.add(list.get(i).getGoodsPrice());
		String goodsName = list.get(i).getGoodsName();
		int num = list.get(i).getGoodsNum();
		if(map.containsKey(goodsName)){
			DetailValue dv = map.get(goodsName);
			dv.setNub(dv.getNub()+num);
			dv.setMoney(dv.getMoney().add(list.get(i).getGoodsPrice()));
			map.put(goodsName,dv);
		}else{
			DetailValue dv = new DetailValue();
			dv.setNub(num);
			dv.setMoney(list.get(i).getGoodsPrice());
			map.put(goodsName,dv);
		}
		
	}
	page.setList(list);
	model.addAttribute("record", record);
	model.addAttribute("page", page);
	model.addAttribute("allMoney", allMoney);
	model.addAttribute("map", map);
	return "modules/sys/mRecordList";
}
}
