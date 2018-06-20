package com.ks.app.web;

import com.google.common.collect.Lists;
import com.ks.app.dao.APosterDao;
import com.ks.app.entity.AAdvertInfo;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.service.AAdvertService;
import com.ks.app.service.AMGoodsService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.*;
import com.ks.utils.Config;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant;
import com.ks.utils.EnumConstant.AdvertLocationType;
import com.ks.utils.EnumConstant.UserAmbassador;
import com.ks.utils.EnumConstant.UserLevel;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员中心控制器
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/vip")
public class VipUserController extends BaseController {
    @Autowired
    private AUserService aUserService;
    @Autowired
    private AAdvertService aAdvertService;
    @Autowired
    private APosterDao aPosterDao;
    @Autowired
    private AMUserService amUserService;
    @Autowired
    private AMGoodsService amGoodsService;

    @RequestMapping("/getUserBySession.do")
    @ResponseBody
    public ActionResponse<Object> getUserBySession(HttpServletRequest request) {
        ActionResponse<Object> returnData = new ActionResponse<Object>();
//		String openId = (String) session.getAttribute("openId");
        AUser user = LoginUtil.weixinLoginUser(request);
        if (user != null) {
            returnData.setData(user);
            returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
        } else {
            returnData.setingError(Constant.DEFAULT_ERROR_MSG);
        }
        return returnData;
    }

    /**
     * 获取code重定向页面
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/redirect")
    public String WeixinRedirect(HttpServletRequest req, HttpServletResponse resp) {
        String type = req.getParameter("type");
//		return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeixinUtil.APPID+"&redirect_uri="+SystemPath.getRequestProjectUrl(req)+"/vip/inc.do?type="+type+"&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
        return "redirect:" + String.format(WeixinUtil.GRANT_URL, WeixinUtil.APPID, SystemPath.getRequestProjectUrl(req), type);
    }

    /**
     * 获取用户信息
     *
     * @param model
     * @param req
     * @param resp
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping("/inc.do")
    public String inc(HttpSession session, HttpServletRequest req, HttpServletResponse resp, Model model) throws ParseException, IOException {
        String type = req.getParameter("type");
        String CODE = req.getParameter("code");
        String APPID = WeixinUtil.APPID;
        String SECRET = WeixinUtil.APPSECRET;
        String URL = WeixinUtil.GRANT_TOKEN_URL.replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
        String jsonStr = URLConnectionHelper.sendGet(URL);
        JSONObject jsonObj = new JSONObject(jsonStr);

        if (!jsonObj.has("openid")) {//说明code失效
            return "redirect:/vip/redirect?type=" + type;
        }
        String openid = jsonObj.get("openid").toString();
        AUser user = aUserService.findUserByopenId(openid, "");
        if (user != null && !("0".equals(user.getStartFlag()))) {
            return "mobile/startFlagOff";
        }
        if (user != null) {
            AMUser u = amUserService.getMuserByUserId(user.getId());
            if (u == null) {
                u = new AMUser();
                u.setUserId(user.getId());
                u.setmUserLevel("0");
                amUserService.save(u);
            }
        }
        session.setAttribute("openId", openid);
        req.setAttribute("type", type);

        if ("2".equals(type)) {//点击个人中心
//	        	String accessToken = aUserService.getToken();
//	        	WeixinUserInfo wui = WeixinUtil.getWeixinUserInfo(accessToken,openid);
            int sbNub = 0;
            if (user != null) {
                String headImgUrl = user.getMinPhoto();
                if (StringUtils.isNotBlank(user.getMinPhoto())) {
                    user.setMinPhoto(SystemPath.getRequestPreUrl(req) + headImgUrl);
                }
                user.setUserLevelEntity(UserLevel.userLevel(user.getUserLevel()));
                String str = user.getUserLevel();
                if (!("0".equals(str))) {
                    user.setUserAmbassador(UserLevel.userLevel(user.getUserLevel()));
                } else {
                    user.setUserAmbassador(UserAmbassador.userAmbassador(user.getUserAmbassador()));
                }
                sbNub = aUserService.getSubordinateNub(user.getId());
            } else {//没有重新新建用户
                String accessToken = aUserService.getToken();
                WeixinUtil.getWeixinUserInfo(accessToken, openid);
                user = aUserService.findUserByopenId(openid, "");
            }
            user.setPerformance(aUserService.getAllPerformanceById(user.getId()));
            req.setAttribute("user", user);
            req.setAttribute("sbNub", sbNub);
            return "mobile/my";
        }
        if ("3".equals(type)) {//分享
            String accessToken = aUserService.getToken();
            if (StringUtils.isNotBlank(accessToken)) {
                if (user != null) {
                    String path = aPosterDao.getPath();
                    //创建二维码,并下载到本地,[外部地址，服务器地址]
                    path = FileUtils.getFileSavePath(path, Config.getUploadPoster());
                    String[] resultFile = WeixinERCodeUtil.getShareERCodePath(path, accessToken, user.getId(), user.getName(), req);
                    if (resultFile != null) {
                        String filePath = resultFile[0];
                        req.setAttribute("filePath", filePath);
                    }
                }
            }

            return "mobile/share";
        }
        //点击公司首页
        AAdvertInfo advertInfo = new AAdvertInfo();
        advertInfo.setAdvertState(EnumConstant.TopState.YES.getKey());
        advertInfo.setLocationModule(AdvertLocationType.PORTAL.getKey());
        // 取得列表
        List<AAdvertInfo> advertList = aAdvertService.findAdvertList(advertInfo, 702, 400, req);
        List<AMGoods> mList = amGoodsService.getTwoGoods();
        model.addAttribute("mList", mList);
        model.addAttribute("advertList", advertList == null ? Lists.newArrayList() : advertList);
        return "mobile/index";
    }

    /**
     * 修改用户资料的页面
     *
     * @param session
     * @param req
     * @return
     */
    @RequestMapping("/updateUser.do")
    public String updateUser(HttpServletRequest request, Model model) {
//		AUser user = aUserService.getUser((String)session.getAttribute("openId"));
        AUser user = LoginUtil.weixinLoginUser(request);
        model.addAttribute("user", user);
        return "mobile/edit-profile";
    }

    /**
     * 修改用户资料
     *
     * @param req
     * @param session
     * @return
     */
    @RequestMapping("/updateUserCommit")
    public String updateUserCommit(HttpServletRequest request) {
//		String openid = (String)session.getAttribute("openId");
//		AUser user = aUserService.getUser(openid);
        AUser user = LoginUtil.weixinLoginUser(request);
        String updateFlagName = "0";
        String updateFlagCard = "0";
        String name = user.getName();
        if ("0".equals(user.getUpdateFlagName())) {
            name = request.getParameter("name");
            if (name != null) {
                updateFlagName = "1";
            }
        }
        String mobile = request.getParameter("mobile");
        String birthday = request.getParameter("birthday");
        String card = user.getCard();
        if ("0".equals(user.getUpdateFlagCard())) {
            card = request.getParameter("card");
            if (card != null) {
                updateFlagCard = "1";
            }
        }
        String mail = request.getParameter("mail");
        String location[] = request.getParameter("city").split("-");

        String province = "";
        String city = "";
        String zone = "";
        if (location.length > 0) {
            province = location[0];
        }
        if (location.length > 1) {
            city = location[1];
        }
        if (location.length > 2) {
            zone = location[2];
        }
        String addr = request.getParameter("addr");
        String bankType = request.getParameter("bankType");
        String bankCard = request.getParameter("bankCard");
        String userBankType = user.getBankType();
        String userBankCard = user.getBankCard();
        if (userBankType != null && !("".equals(userBankType))) {
            bankType = userBankType;
        }
        if (userBankCard != null && !("".equals(userBankCard))) {
            bankCard = userBankCard;
        }
        String openid = user == null ? "" : user.getOpenId();
        aUserService.updateAUser(openid, name, mobile, birthday, card, mail, province, city, zone, addr, bankType, bankCard, updateFlagName, updateFlagCard);
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinUtil.APPID + "&redirect_uri=" + SystemPath.getRequestProjectUrl(request) + "/vip/inc.do?type=2&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
    }

    /**
     * 查看下级人
     *
     * @param session
     * @param req
     * @return
     */
    @RequestMapping("/Subordinate.do")
    public String seeSubordinate(HttpServletRequest request) {
//		String openId = (String)session.getAttribute("openId");
//		String id = aUserService.getIdByOpenId(openId);
        AUser user = LoginUtil.weixinLoginUser(request);
        if (user != null) {
            List<AUser> list = aUserService.getSubordinateById(user.getId());
            if (list == null) {
                list = Lists.newArrayList();
                ;
            }
            for (int i = 0; i < list.size(); i++) {
                BigDecimal bd = aUserService.getBalanceById(list.get(i).getId());
                list.get(i).setPerformance(bd);
                int nub = aUserService.countSub(list.get(i).getId());
                list.get(i).setSubFlag(nub);
                String url = list.get(i).getMinPhoto();
                if (StringUtils.isNotBlank(url)) {
                    String str = UserLevel.userLevel(list.get(i).getUserLevel());
                    if (StringUtils.isNoneBlank(str) && str.length() >= 2) {
                        list.get(i).setUserLevelEntity(str.substring(0, 2));
                    } else {
                        list.get(i).setUserLevelEntity(UserLevel.DEFAULT.getValue().substring(0, 2));
                    }
                    list.get(i).setMinPhoto(SystemPath.getRequestPreUrl(request) + url);
                }
            }
            request.setAttribute("sbNub", aUserService.getSubordinateNub(user.getId()));
            request.setAttribute("list", list);
        }

        return "mobile/subordinate";
    }

    @RequestMapping("/seeSubordinate.do")
    public @ResponseBody
    ActionResponse<Object> seeSubordinateDo(HttpServletRequest req, HttpServletResponse response) {
        ActionResponse<Object> returnData = new ActionResponse<Object>();
        String id = req.getParameter("id");
        List<AUser> list = aUserService.getSubordinateById(id);
        if (list == null) {
            list = Lists.newArrayList();
            ;
        }
        for (int i = 0; i < list.size(); i++) {
//change by andyzhao
//BigDecimal bd = aUserService.getBalanceById(list.get(i).getId());
            BigDecimal bd = aUserService.getBalanceSumContainChildrenById(list.get(i).getId());
//end change

            list.get(i).setPerformance(bd);
            list.get(i).setSubFlag(aUserService.countSub(list.get(i).getId()));
            String str = UserLevel.userLevel(list.get(i).getUserLevel());
            if (StringUtils.isNoneBlank(str) && str.length() >= 2) {
                list.get(i).setUserLevelEntity(str.substring(0, 2));
            } else {
                list.get(i).setUserLevelEntity(UserLevel.DEFAULT.getValue().substring(0, 2));
            }
            String url = list.get(i).getMinPhoto();
            if (StringUtils.isNotBlank(url)) {
                list.get(i).setMinPhoto(SystemPath.getRequestPreUrl(req) + url);
            }
        }
        returnData.setData(list);
        return returnData;

    }

    /**
     * 获取code重定向页面
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/grantRedirect")
    public String grantRedirect(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        return "redirect:" + String.format(WeixinUtil.GRANT_URL, WeixinUtil.APPID, SystemPath.getRequestProjectUrl(req), method);
    }

}
