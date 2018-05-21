package com.ks.utils;

public class Constant {

	public static int SUCCESS_CODE = 0;

	public static int FAILURE_CODE = 1;
	
	//未绑定账户，请先注册或者绑定已有账户。
	public static int FAILURE_CODE2 = 2;
	public static String LOGIN_ACCOUNT_NOOPEN = "账户未开通，不能登录系统！"; // 用户帐号未开通
	
	public static String LOGIN_PHONE_ERROR = "手机号或密码错误"; // 手机号密码错误
	public static int CODE_LOGIN_PHONE_ERROR = -1; // 手机号密码错误
	public static String NO_LOGINKEY = "未提供登录token"; // 未提供登录token
	public static int CODE_NO_LOGINKEY = -2; // 未提供登录token
	public static String LOGIN_USERSTATE_FORBIDDEN = "用户已禁用，不能登录系统！"; // 用户帐号被禁用
	public static int CODE_LOGIN_USERSTATE_FORBIDDEN = -3; // 用户帐号被禁用
	public static String LOGIN_EXPIRED = "token失效,请重新登录"; // token失效,请重新登录
	public static int CODE_LOGIN_EXPIRED = -4; // token失效,请重新登录
	public static String TOKEN_ERROR = "token错误,请重新登录"; // token错误
	public static int CODE_TOKEN_ERROE = -5; // token错误
	
	public static int VALID_TOKEN_DAYS=7;//token失效天数
	
	public static String MANAGER_ID="1";//管理员id
	
	public static String DEFAULT_ERROR_MSG = "获取数据失败";
	public static String DEFAULT_SUCCESS_MSG = "获取数据成功";
	public static String NO_USER = "用户不存在";
	

	public static final String DELETE_FLAG_0 = "0";
	public static final String DELETE_FLAG_1 = "1";
	
	public static final int DEFAULT_WIDTH=400;
	
	public static final int DEFAULT_HEIGHT=400;
	
	public static final String DEFAULT_SHARE_URL = "app/share/getShareDetail.do?id=%s&type=%s";
	
	public static final int PORT_TOP_MEDIA_COUNT = 999999;
	
	public static final int ORDER_AFTER_MAX_NUM = 99999;
	public static final int ORDER_AFTER_MIN_NUM = 10000;
	//客服电话
	public static final String KEFU_PHONE_COLUMN ="kefu_phone";
	//手续费
	public static final String SHOU_XU_FEI = "sys_shouxufei";
	
	/**一级形象大使项目一级分销*/
	public static final String AMBASSADOR_ONE_ITEM_LEVEL_ONE = "ambassador_one_item_level_one";
	/**二级形象大使项目一级分销*/
	public static final String AMBASSADOR_TWO_ITEM_LEVEL_ONE = "ambassador_two_item_level_one";
	/**三级形象大使项目一级分销*/
	public static final String AMBASSADOR_THREE_ITEM_LEVEL_ONE = "ambassador_three_item_level_one";
	/**四级形象大使项目一级分销*/
	public static final String AMBASSADOR_FOUR_ITEM_LEVEL_ONE = "ambassador_four_item_level_one";
	/**项目二级分销*/
	public static final String ITEM_LEVEL_TWO = "item_level_two";
	/**套餐一级分销*/
	public static final String COMBO_LEVEL_ONE = "combo_level_one";
	/**项目星级玩法每级分销比例*/
	public static final String ITEM_STAR = "item_star";
	/**商品手续费*/
	public static final String GOODS_SHOU_XU = "goods_shouxufei";
	/**面膜商品分销*/
	public static final String M_GOODS_SUB = "m_goods_sub";
	/**商品资格一级分销*/
	public static final String M_ZG_LEVEL_ONE = "m_zg_level_one";
	/**商品资格二级分销*/
	public static final String M_ZG_LEVEL_TWO = "m_zg_level_two";
	/**商品资格星级分销*/
	public static final String M_GOODS_STAR = "m_goods_star";
	/**面膜优惠数量*/
	public static final String DISCOUNTS_NUM = "discounts_num";
	
	/**店家对店家（上下级均为4级形象大使）*/
	public static final String SHOP_TO_SHOP = "shop_to_shop";
	/**上上级为总监，一级分销*/
	public static final String DIRECTOR_LEVEL_ONE = "director_level_one";
	/**上上级为总监，二级分销*/
	public static final String DIRECTOR_LEVEL_TWO = "director_level_two";
	/**上上级为咨询师，二级分销*/
	public static final String CONSULTANT_LEVEL_TWO = "consultant_level_two";
	/**上上级为店家，上级为店家，买家非店家*/
	public static final String NO_SHOP_LEVEL_TWO = "no_shop_level_two";
}
