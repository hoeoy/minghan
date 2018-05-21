
mui(".mui-scroll-wrapper").scroll({
	  deceleration: 0.001,
      //bounce: false,//滚动条是否有弹力默认是true
     // indicators: false, //是否显示滚动条,默认是true
});        
var slider = mui("#slider");
slider.slider({
	interval: 5000
});

/*mui('.mui-scroll-wrapper').scroll({
	deceleration: 0.0005,
	indicators: true,
});*/ //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
mui('body').on('tap', 'a', function() {
	document.location.href = this.href;
});
