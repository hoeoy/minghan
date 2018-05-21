       
    //启用双击监听
        mui.init({
            gestureConfig:{
                doubletap:true
            },
            subpages:[{
                url:'index.html',
                id:'MainViwe',
                styles:{
                    top: '0',
                    bottom: '51px'
                }
            }]
        });
    			var slider = mui("#slider");
			slider.slider({
				interval: 5000
			});
			mui('.mui-scroll-wrapper').scroll({
				deceleration: 0.0005,
				indicators: false,
			}); //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006

//底部选项卡切换跳转
(function jumpPage(){
        //跳转页面
        var subpages = ['index.html','share.html', 'telephone.html', 'my.html'];
        var subpage_style = {
            top: '44px',
            bottom: '51px'
        };
        var aniShow = {};//动画显示
        //首次启动切滑效果
             //当前激活选项
            var activeTab = subpages[0];         
             //选项卡点击事件
            mui('.mui-bar-tab').on('tap', 'a', function(e) {
                var targetTab = this.getAttribute('href');
                $('#MainViwe').attr('src',targetTab);
            });
             //自定义事件，模拟点击“首页选项卡”
            document.addEventListener('gohome', function() {
                var defaultTab = document.getElementById("defaultTab");
                //模拟首页点击
                mui.trigger(defaultTab, 'tap');
                //切换选项卡高亮
                var current = document.querySelector(".mui-bar-tab>.mui-tab-item.mui-active");
                if (defaultTab !== current) {
                    current.classList.remove('mui-active');
                    defaultTab.classList.add('mui-active');
                }

    });
   

        })();
