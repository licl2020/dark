$(function(){
	  $.scrollPic = function (options){ //定义了一个scrollPic函数，有一个传参，用于调用；
	     /************************开始整个默认插件参数讲解*******************************/
	    //整个defaults以花括号包含的是默认的参数，调用此插件的只需要修改ele，Time，autoscroll即可；
	    var defaults={
	      ele: '.pic-slider-io',   //pic-slider-io是一个class，css中定义了其样式；
	      Time : '2000',      //Time是定义了滑动的时间；
	      autoscroll : true    //autoscroll设为true表示自动切换图片；
	    };
	    /************************结束整个默认插件参数讲解*******************************/
	     
	    //$.extend({},defaults, option)有{}主要是为了创建一个新对象，保留对象的默认值。
	    var opts = $.extend({}, defaults, options);
	     
	    //$(opts.ele)可以理解为取这个执行，与$('.class1').click();类似表示，然后理解为赋值给PicObject；
	    //或者这样理解,$(opts.ele)就是$('.pic-slider-io')，只不过（.pic-slider-io是个class作为参数，所以要(opts.ele)来表示）；
	    var PicObject = $(opts.ele);
	     
	    //PicObject.find('ul')，这个可以理解成$(opts.ele).find('ul'),因为opts.ele其实就是取得的一个class，
	    //相当于$('.pic-slider-io').find('ul');然后赋值给scrollList，所以整个就用scrollList来表示他；
	    var scrollList = PicObject.find('ul');
	     
	    //同理scrollList.find('li')可以表示为$('.pic-slider-io').find('ul').find('li');所以这个是一层一层来查找的如果你看到html就会更加清晰；
	    var listLi = scrollList.find('li');
	     
	    //图片的命名是1.jpg,2.jpg,这样的，index是用来表示图片的名字的初始化赋值为0；
	    var index = 0;
	     
	    //这是定义一个picTimer(自动切换函数)的函数；
	    var picTimer;
	     
	    //一个li中包含一个图片，所以这是查找有多少个图片，赋值给len；
	    var len = PicObject.find('li').length;
	     
	    /*****************开始自动切换函数************************/
	    function picTimer(){
	      showPic(index);//调用showPic(index)函数(在下面)
	      index++;
	      if(index == len){//如果index的值等于len，就表示从第一张图片到最后一张图片切换完了，然后是index赋值为0重新开始
	        index=0;
	      }
	    }
	    /*****************结束自动切换函数************************/
	     
	     
	    //setInterval() 方法可按照指定的周期（以毫秒计）来调用函数或计算表达式。
	    //setInterval() 方法会不停地调用函数，直到 clearInterval() 被调用或窗口被关闭
	    //此判断为如果autoscroll为true，则不停的调用picTimer函数，以Time的速度调用
	    if(opts.autoscroll){
	      var time = setInterval(picTimer,opts.Time);
	    }
	     
	     
	    /*****************开始动画函数************************/
	    function showPic(index){//定义动画函数
	      //listLi是找到第一个li，然后隐藏他，listLi在var listLi = scrollList.find('li');已经介绍了
	      listLi.hide();
	      //fadeIn() 方法使用淡入效果来显示被选元素，假如该元素是隐藏的。siblings()方法是遍历。
	      listLi.eq(index).fadeIn(500).siblings().hide();
	      //找到paging对应的css样式，如果与当前的index一致，则添加class为current的css样式，否则就移除。
	      PicObject.find(paging).eq(index).addClass('current').siblings().removeClass('current');
	    }
	    /*****************结束动画函数************************/
	     
	    //这是在class为pic-slider-io的div中添加一个class为pic-page-btn的子div。
	    //主要是设置右下角数字的承载
	    PicObject.append('<div class="pic-page-btn"></div>');
	     
	    //在这个class为pic-page-btn的子div中添加图片张数对应的数字个数，1,2,3，，，，，
	    //从这可以看出来，数字不是自己一个个添得，是根据li的个数，也就是图片的个数自动生成的
	    for( i=1;i<=len;i++){
	      PicObject.find('.pic-page-btn').append('<span>'+i+'</span>');
	    }
	     
	    //这个就是和上面讲listLi一样的
	    var paging = PicObject.find('.pic-page-btn span');
	     
	    //为相应的右下角的数字改变其背景颜色
	    paging.eq(index).addClass('current');
	     
		//鼠标经过1、2、3、4的效果
		PicObject.find(paging).mouseover(function(){
		  index = PicObject.find(paging).index($(this));
		  showPic(index); //调用showPic(index)函数。
		});
		//鼠标经过1、2、3、4的效果
		 
		//清除计时器
		//当鼠标悬浮在1,2,3,4上面的时候，就相当于要切换图片了
		//所以我们就要清除计时器，重新来过了
		PicObject.hover(function(){
		  clearInterval(time);//这个是相对于setInterva()的；
		},function(){
		  if(opts.autoscroll){
		    time = setInterval(picTimer,opts.Time);
		  }else{
		    clearInterval(time);
		  }
		});
	  }
	});

