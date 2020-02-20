/*!
 * jQuery Mousewheel 3.1.13
 *
 * Copyright jQuery Foundation and other contributors
 * Released under the MIT license
 * http://jquery.org/license
 */
if(!window.top || !window.top.pictureViewer){	
	var pictureViewer=function(){
		this.proportion=10;	//每次缩放多少
		this.maxBig=1.6;//放大最大倍数
		this.maxYoung =0.5;//缩小最大倍数
	}
	
	pictureViewer.prototype={
		addElem:function(img){
			var mc=document.createElement("div"),
				demo=document.createElement("div"),
				box=document.createElement("div"),
				div=document.createElement("div"),
				ic=document.createElement("i"),
				ir=document.createElement("i"),
				Body=document.getElementsByTagName("body")[0];
			mc.className="dailog-checkImg-mode";
			box.className="dailog-checkImg-box";
			ic.className="close";
			ic.title="关闭";
			ic.innerHTML="&#xe642;";
			ir.title="重置";
			ir.innerHTML="&#xe61c;";
			ir.className="reset";
			div.className="dailog-checkImg-toolbar";
			demo.className="dailog-checkImg";
			
			div.appendChild(ic);
			div.appendChild(ir);
			demo.appendChild(div);
			demo.appendChild(box);
			Body.appendChild(mc);
			Body.appendChild(demo);
			this.mc=mc;
			this.box=box;
			this.demo=demo;
			this.ic=ic;
			this.ir=ir;
			//必须预加载完成
		},
		loadAddElem:function(img){
			var demo=this.demo;
			this.box.appendChild(img);
			$(demo).css("padding-top")
			var w=demo.offsetWidth,h=demo.offsetHeight,
				W=$(img).width(),H=$(img).height();	
			if(W/H>w/h){
				this.box.className="dailog-checkImg-box width";
				this.position="width";	
				H=$(img).height(),W=$(img).width();
				demo.style.height=H+'px'
				demo.style.marginTop=-Math.floor((H+($(demo).css("padding-top").replace(/\D/g,'')-0))/2)+'px';
				
			}else{
				demo.style.width=W+'px';	
				demo.style.marginLeft=-Math.floor((W)/2)+'px';
			}
			
			this.img=img;
			this.imgL=img.offsetLeft;
			this.imgT=img.offsetRight;
			this.changeW=Math.round((W-0)/this.proportion);
			this.changeH=Math.round((H-0)/this.proportion);
			demo=$(this.demo);
			this.checkT=(demo.css("top").slice(0,-2)-0)+(demo.css('margin-top').slice(0,-2)-0)+($(demo).css("padding-top").replace(/\D/g,'')-0);
			this.checkL=(demo.css("left").slice(0,-2)-0)+(demo.css('margin-left').slice(0,-2)-0);
			this.addEvent();
		},	
		getPosition:function(e,h,w,l,t){
			y=(e.pageY-(this.checkT+t)) * (this.changeH)/h;
			x=(e.pageX-(this.checkL+l)) * (this.changeW)/w;
			return {x:Math.round(x),y:Math.round(y)};
		},
		addShow:function(src){
			this.position="height";
			this.addElem();
			var that=this,img=new Image();
			img.onload=function(){
				that.loadAddElem(img);	
			}
			img.src=src;
			this.img=img;
		},
		addEvent:function(){
			var  that=this,top=0,left=0,isMouseover=false;page={x:0,y:0};
			this.mc.onclick=function(){
				that.remover();
			};
			
			this.ic.onclick=function(){
				that.remover()
			};
			this.ir.onclick=function(){
				that.reSet()
			};
			$("body").on("dragstart",function(){
				return false;	
			});
			$(this.box).on('mousewheel', function(event, delta, deltaX, deltaY){
				var img=$(that.img),
					 w=img.width(),
					 h=img.height(),
					 l=img.css("left").slice(0,-2)-0,
					 t=img.css("top").slice(0,-2)-0,
					 page=that.getPosition(event,h,w,l,t);
				if(delta>0){
					if(that.position=="height"){
						if(h+that.changeH<that.changeH*that.proportion*that.maxBig){
							img.css({
								height:h+that.changeH,
								left:l-page.x,
								top:t-page.y
							});
						}
					}else{
						if(w+that.changeW<that.changeW*that.proportion*that.maxBig){
							img.css({
								width:w+that.changeW,
								left:l-page.x,
								top:t-page.y
							})
						}
					};
				}else{
					if(that.position=="height"){
						if(h-that.changeH>that.changeH*that.proportion*that.maxYoung){
							img.css({
								height:h-that.changeH,
								left:l+page.x,
								top:t+page.y
							})
						}
					}else{
						if(w-that.changeW>that.changeW*that.proportion*that.maxYoung){
							img.css({
								width:w-that.changeW,
								left:l+page.x,
								top:t+page.y
							})
						}
					};
				}
				return false
			});
			
			$(this.img).mousedown(function(e){
				isMouseover=true;
				top=this.offsetTop;
				left=this.offsetLeft;
				page={
					x:e.pageX,	
					y:e.pageY
				};
			});
			$(this.box).mousemove(function(e){
				if(isMouseover){
					this.imgT=top+e.pageY-page.y;
					this.imgL=left+e.pageX-page.x;
					$(that.img).css({
						top:top+e.pageY-page.y,
						left:left+e.pageX-page.x
					});
				}
			});
			$('body').mouseup(function(e){
				isMouseover=false;
			});
		},
		remover:function(){
			document.body.removeChild(this.demo);
			document.body.removeChild(this.mc);
		},
		reSet:function(){
			$(this.img)	.attr('style',"");
		}
	}
}else{
	pictureViewer=window.top.pictureViewer;
}