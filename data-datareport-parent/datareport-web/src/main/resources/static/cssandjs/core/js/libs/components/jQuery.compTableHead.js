// JavaScript Document

$.fn.noSelect = function(flag){
	flag = (flag === undefined) ? true : flag;
	
	if(flag){
		return this.each(function(){
			if(browser.msie || browser.safari || !!window.ActiveXObject || "ActiveXObject" in window){
				$(this).bind('selectstart', function(){
					return false;
				});
			}
			else if(browser.mozilla){
				$(this).css('MozUserSelect', 'none');
				$('body').trigger('focus');
			}
			else if(browser.opera){
				$(this).bind('mousedown', function(){
					return false;
				});
			}
			else{
				$(this).attr('unselectable', 'on');
			}
		});
	}else{
		return this.each(function(){
			if(browser.msie || browser.safari){
				$(this).unbind('selectstart');
			}
			else if(browser.mozilla){
				$(this).css('MozUserSelect', 'inherit');
			}
			else if(browser.opera){
				$(this).unbind('mousedown');
			}
			else{
				$(this).removeAttr('unselectable', 'on');
			}
		});
	}
};
	
function compTableHead(o){
	this.node=o.node;
	this.minX=10;
	this.minY=10;
	this.data=o.data;
	this.init();
}
compTableHead.prototype={
	getHtml:function(){
		var html=['<ul class="comp-head-list">'],i=0;
		for(;i<this.data.length;i++){
			html.push('<li><div class="box '+(this.data[i].isChoose?"hover":"")+'">'+this.data[i].html+'<i class="iconfont">'+(this.data[i].isChoose?"&#xe606;":"&#xe607;")+'</i><div class="shadow"></div></div></li>');	
		}
		
		html.push('</ul>')
		return html.join("");	
	},
	init:function(){
		this.tableHead=$(this.getHtml());
		$(this.node).append(this.tableHead);
		this.tableHeadEvent();
	},
	tableHeadEvent:function(){
		var that=this;
		this.tableHead.find("li div.box").off('mousedown').on('mousedown',function(e){
			that.isRunning=true;
			$(this).parent("li").addClass("running");
			var o=that.GetRect(this),div=document.createElement("div");
			div.className="absoluteBox";
			div.style.top=o.top+"px";
			div.style.left=o.left+"px";
			$(div).append($(this).clone());
			
			that.runningDiv=div;
			that.runningNode=$(this);
			that.runningMove=false;
			that.oldRect=o;
			that.oldPage={x:e.pageX,y:e.pageY};
			that.index=$(this).parent("li").index();
			
			that.divEvent();
			$('body').append(div).noSelect(true);
		})			
	},
	divEvent:function(){
		var that=this;
		
		this.runningDiv.onmouseup=function(){
			if(!that.runningMove){
				that.runningNode.toggleClass("hover");
				if(that.runningNode.hasClass("hover")){
					$("i",that.runningNode).html("&#xe606;");	
				}else{
					$("i",that.runningNode).html("&#xe607;");	
				}
				that.data[that.index].isChoose=!that.data[that.index].isChoose;	
			}
			$('body').noSelect(false);
		}
		
		
		$(document).mousemove(function(e) {
			if(that.isRunning && that.runningDiv){
				var x=e.pageX-that.oldPage.x;y=e.pageY-that.oldPage.y;
				if(y>that.minY || x>that.minX){
					that.runningMove=true;
					that.runningDiv.style.top=(y+that.oldRect.top)+"px";
					that.runningDiv.style.left=(x+that.oldRect.left)+"px";
				}
			}
		});
		$(document).mouseup(function(e) {
			if(that.isRunning){
				that.tableHead.children("li").removeClass("running");
				$(that.runningDiv).remove();
				if(that.runningMove){
					var obj=that.getRunningIdex(e);
					
					var html=that.runningNode.parent("li").clone();
					
					if(obj.before){
						that.tableHead.children("li:eq("+obj.num+")").before(html);
						
					}else{
						that.tableHead.children("li:eq("+obj.num+")").after(html);
						obj.num++;
					}
					
					if(obj.num<that.index){
						that.data.splice(obj.num,0,that.data[that.index]);
						that.data.splice(that.index+1,1);	
					}else{
						that.data.splice(obj.num,0,that.data[that.index]);
						that.data.splice(that.index,1);	
					}
					
					that.runningNode.parent("li").remove();
					
					that.tableHeadEvent();
				}
			}
			that.isRunning=false;
			that.runningMove=false;
		});
	},
	getRunningIdex:function(e){
		var obj={
			before : false,	
			num   : this.tableHead.children("li").length-1
		},that=this;
		if((e.pageX-that.oldPage.x) < 0){
			obj.before=true;
		}
		that.tableHead.children("li").each(function(i){
			var x=that.GetRect(this);
			if(e.pageX < x.left&& e.pageY<(x.top+44)){
				
				obj.num=i-1;
				return false;
			}
		});

		
		obj.num < 0 && (obj.num=0);
		return obj;	
	},
	getHeadJson:function(){
		return this.data;
	},
	GetRect:function(element){
		var rect = element.getBoundingClientRect();
		
		var top = document.documentElement.clientTop;
		
		var left= document.documentElement.clientLeft;
		
		return{
			top    :   rect.top - top,
			left   :   rect.left - left
		}
	}
}