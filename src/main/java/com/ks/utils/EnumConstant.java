package com.ks.utils;


public class EnumConstant {
	//用户状态
	public static enum UserState{
		FORBIDDEN("0","禁用"), OPEN("1","启用");
		private String key;
		private String value;
		
		private UserState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String userState(String key){
			for(UserState us :UserState.values()){
				if(us.key.equals(key)){
					return us.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	//用户类型
	public static enum UserType {
		DIRECTOR("3","总监"), SALE("2","业务员"), MANAGER("1","管理员"), WEIXIN("0","微信用户");
		private String key;
		private String value;

		private UserType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		public static String userType(String key){
			for(UserType ut :UserType.values()){
				if(ut.key.equals(key)){
					return ut.value;
				}
			}
			return null;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	//用户级别
	public static enum UserLevel {
		DEFAULT("0","普通会员"), ONE("1","一星会员"), TWO("2","二星会员"), THREE("3","三星会员"), FOUR("4","四星会员"), 
		FIVE("5","五星会员"), SIX("6","六星会员"),SEVEN("7","七星会员"),EIGHT("8","八星会员"),NINE("9","九星会员"),
		TEN("10","十星会员"),ELEVEN("11","十一星会员"),TWELVE("12","十二星会员"),THIRTEEN("13","十三星会员"),FOURTEEN("14","十四星会员")
		,FIFTEEN("15","十五星会员"),SIXTEEN("16","十六星会员"),SEVENTEEN("17","十七星会员"),EIGHTEEN("18","十八星会员"),NINETEEN("19","十九星会员")
		,TWENTY("20","二十星会员");
		private String key;
		private String value;

		private UserLevel(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String userLevel(String key){
			for(UserLevel ul :UserLevel.values()){
				if(ul.key.equals(key)){
					return ul.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	//专家是否认证
	public static enum DocAttestationFlag{
		NO("0"," "),YES("1","认证");
		private String key;
		private String value;
		
		private DocAttestationFlag(String key,String value){
			this.key = key;
			this.value = value;
		}
		public static String docAttestationFlag(String key){
			for(DocAttestationFlag daf :DocAttestationFlag.values()){
				if(daf.key.equals(key)){
					return daf.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	//广告类型
	public static enum AdvertType {
		PACKAGE("0","套餐"), PROJECT("1","项目"),GOODS("2","商品"),INNER_URL("3","内部链接"),OUT_URL("4","外部链接");
		private String key;
		private String value;
		
		private AdvertType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	//广告位置类型
	public static enum AdvertLocationType {
		PORTAL("portal_banner","首页"), MALL("mall_banner","商城"), PORTAL_MENU("portal_menu","首页菜单"), SALON("salon_step","美容攻略");
		private String key;
		private String value;
		
		private AdvertLocationType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	//置顶状态
	public static enum TopState {
		NO("0","未置顶"), YES("1","已置顶");
		private String key;
		private String value;
		
		private TopState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	//版本类型
	public static enum VersionType {
		ANDROID("0","安卓"), IOS("1","苹果");
		private String key;
		private String value;

		private VersionType(String key,String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	//发货状态
	public static enum SendState {
		NO("0","待发货"), YES("1","已发货");
		private String key;
		private String value;
		
		private SendState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}

	public static enum Integral{
		NEWUSER(20L,"新用户送的积分"),
		FORBUDDHA(10L,"供佛送的积分"),
		BACKTHE(5L,"回向送的积分"),
		POSTING(10L,"发帖送的积分"),
		RETURNCARD(5L,"回复帖子送的积分");
		private Long key;
		private String value;
		
		private Integral(Long key,String value){
			this.key = key;
			this.value = value;
		}

		public Long getKey() {
			return key;
		}

		public void setKey(Long key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	//是否选中
	public static enum SelectState{
		NO("0","未选中"), YES("1","选中");
		private String key;
		private String value;
		
		private SelectState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	//微信消息类型
	public static enum WeixinMsgType{
		TEXT("text","文本"), IMAGE("image","图片"), VOICE("voice","语音"), VIDEO("video","视频")
		, SHORT_VIDEO("shortvideo","微视频"), LOCATION("location","地理位置"), LINK("link","链接")
		, EVENT("event","事件");
		private String key;
		private String value;
		
		private WeixinMsgType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	//微信事件类型
	public static enum WeixinEventType{
		SCAN("SCAN","重新绑定"),SUBSCRIBE("subscribe","关注"), UNSUBSCRIBE("unsubscribe","取消关注"), CLICK("CLICK","点击");
		private String key;
		private String value;
		
		private WeixinEventType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	public static enum MonthBackState{
		ZERO("0","点击领取"),ONE("1","已领取");
		private String key;
		private String value;
		
		private MonthBackState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		public static String monthBackState(String key){
			for(MonthBackState mbs :MonthBackState.values()){
				if(mbs.key.equals(key)){
					return mbs.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum MonthBackAuditFlag{
		ZERO("0","未开始"),ONE("1","待审核"),TWO("2","已通过");
		private String key;
		private String value;
		
		private MonthBackAuditFlag(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String monthBackAuditFlag(String key){
			for(MonthBackAuditFlag mbaf :MonthBackAuditFlag.values()){
				if(mbaf.key.equals(key)){
					return mbaf.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	public static enum BrokerageStatus{
		ZERO("0","提现"),ONE("1","奖励");
		private String key;
		private String value;
		
		private BrokerageStatus(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String brokerageStatus(String key){
			for(BrokerageStatus bs :BrokerageStatus.values()){
				if(bs.key.equals(key)){
					return bs.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static enum RewardTypeF{
		ZERO("0","一级分销"),ONE("1","二级分销"),THREE("3","购买套餐");
		private String key;
		private String value;
		
		private RewardTypeF(String key,String value) {
			this.key = key;
			this.value = value;
		}
		public static String rewardTypeF(String key){
			for(RewardTypeF rtf :RewardTypeF.values()){
				if(rtf.key.equals(key)){
					return rtf.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum RewardTypeX{
		ONE("1","一星奖励"), TWO("2","二星奖励"), THREE("3","三星奖励"), FOUR("4","四星奖励"), FIVE("5","五星奖励"), SIX("6","六星奖励"),SEVEN("7","七星奖励"),EIGHT("8","八星奖励"),NINE("9","九星奖励"),TEN("10","十星奖励");
		private String key;
		private String value;
		
		private RewardTypeX(String key,String value) {
			this.key = key;
			this.value = value;
		}
		public static String rewardTypeX(String key){
			for(RewardTypeX rtx :RewardTypeX.values()){
				if(rtx.key.equals(key)){
					return rtx.value;
				}
			}
			return null;
		}
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	public static enum UserAmbassador{
		ZERO_ANGEL("0","普通用户"),ONE_ANGEL("1","一级形象大使"), TWO_ANGEL("2","二级形象大使"), THREE_ANGEL("3","三级形象大使"), FOUR_ANGEL("4","四级形象大使");
		private String key;
		private String value;
		
		private UserAmbassador(String key,String value){
			this.key = key;
			this.value = value;
		}
		
		public static String userAmbassador(String key){
			for(UserAmbassador ua :UserAmbassador.values()){
				if(ua.key.equals(key)){
					return ua.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	
	public static enum OrderState{
		ZERO("0","待付款"),ONE("1","待发货"),TWO("2","待收货"),THREE("3","交易完成"),FOUR("4","交易关闭");
		private String key;
		private String value;
		
		private OrderState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String orderState(String key){
			for(OrderState os :OrderState.values()){
				if(key.equals(os.key)){
					return os.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum MBrokerageType{
		ZERO("0","团队奖励"),ONE("1","一级分销"),TWO("2","二级分销"),THREE("3","复销提成");
		private String key;
		private String value;
		
		private MBrokerageType(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String mBrokerageType(String key){
			for(MBrokerageType os :MBrokerageType.values()){
				if(key.equals(os.key)){
					return os.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum PayState{
		ZERO("0","成功"),ONE("1","失败");
		private String key;
		private String value;
		
		private PayState(String key,String value) {
			this.key = key;
			this.value = value;
		}
		
		public static String payState(String key){
			for(PayState os :PayState.values()){
				if(key.equals(os.key)){
					return os.value;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
}
