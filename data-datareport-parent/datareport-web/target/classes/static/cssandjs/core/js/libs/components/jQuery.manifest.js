/**
 * Author: HuBin 
 * time:2016-7-07
**/
(function($){
	if(!$.ajaxSingle){
		if(!jqXhr) {var jqXhr={};};
		$.ajaxSingle = function (settings) {
			var options = $.extend({ className: 'DEFEARTNAME' }, $.ajaxSettings, settings);
		
			if (jqXhr[options.className]) {
				jqXhr[options.className].abort();
			}
			jqXhr[options.className] = $.ajax(options);
		};//对JS的AJAX进行拓展实现清除队
	}
	function manifest(opts,that){
		g={
			initialize:function(opts,$that){
				this.selectData=opts.defaultNode || [];
				this.opts = opts;
				this.$input = $that;
				this.$div = $that;
				if($that.is('input')){
					this.$div =$that.parent();
				}else{
					this.$input = $that.children();
				}
				this.maxWidth = this.$div.innerWidth();
				this.fontSize = parseInt(this.$input.css('font-size'));
				this.inputPlaceholder = this.$input.attr('placeholder');
				this.setDefaultNode();
				this.initEvent();
			},
			initEvent:function(){				
				this.$div.on("click",$.proxy(this.divClick,this)); 
				this.$input.on("input propertychange",$.proxy(this.inputChange,this)); 
				this.reomveOptions();
	
			},
			setDefaultNode:function(){
				for(var a = 0 ,len = this.selectData.length;a < len;a++){
					this.setAddSpan(this.selectData[a]);
				}	
				if(len > 0){
					this.$input.width('1em');
				}
			},
			divClick:function(e){
				var $that = $(e.currentTarget);
				this.$input.focus();
				e.stopPropagation();
			},
			inputChange:function(e){
				var $that = $(e.currentTarget),
					val = $that.val(),
					that = this;
				
				this.manifestIsExisted = false;	
				clearInterval(this.clearSet);
				
				if(!$.trim(val) && !$('.selected-item',this.$div).length){
					$that.width('auto');
					this.$input.attr('placeholder',this.inputPlaceholder);
				}else{
					this.$input.attr('placeholder',"");
					var width = (val.length || 1)*this.fontSize;
					$that.width(width>this.maxWidth?this.maxWidth:width);	
				}
				$('.ui-manifest-options',this.$div).remove();
				this.clearSet = setTimeout(function(){
					that.setNode(val);
				},this.opts.MILLISEC);
			},
			setNode:function(keywords){
				var opts=this.opts,
					that=this,
					params = opts.params;
				if( typeof opts.params == 'function'){
					params = opts.params();
				}
				
				var params = $.extend({}, { keywords: keywords }, params);
					
				if(this.opts.url){
					$.ajaxSingle({
						type: opts.type,
						url: opts.url,
						data: params,
						className:opts.className,
						dataType: opts.dataType,
						type: opts.type,
						async: opts.async,
						cache: opts.cache,
						success: function(data){
							that.setInnerHTML(data);
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							html.innerHTML = '<div class="noData">'+this.opts.failedTips+'</div>';	
						}
					})	
				}else{
					var data=this.filterData(keywords);
					that.setInnerHTML(data);
				}	
			},
			filterData:function(keywords){
				var nData = $.extend(true,[],this.opts.node),
					data = [],
					Reg = new RegExp(keywords, 'i');
				
				for(var i = 0 ; i< nData.length ; i++){	
					if(Reg.test(nData[i].text)){
						data.push(nData[i])	;
					};
				}	
				
				return data;
			},
			setInnerHTML:function(data){
				if(typeof this.opts.pretreatment == 'function'){
						data = this.opts.pretreatment(data);
				}
				this.manifestIsExisted = true;	
				
				var $html = $(this.getInnerHTML(data)),
					that  = this,
					listsHeight,
					height;
					
					$('li',$html).click(function(e) {
						var $this = $(this),
							thisData = this.manifestData;
						if($this.is('.disabled')){
							return;	
						}
						
						if(typeof that.opts.onRowClick == 'function'){
							var bool = that.opts.onRowClick();
							if(typeof bool == 'boolean' &&　!bool){
								return ;
							};
						}
						
						that.$input.attr('placeholder',"");
						that.selectData.push(thisData);
						that.setAddSpan(thisData);
						that.$input.val("").width("1em");
						$html.remove();
						
						if(typeof that.opts.onRowBeforeClick == 'function'){
							that.opts.onRowBeforeClick();
						}
						
					});
				
				
				this.$div.append($html);
				height = $('li',$html).outerHeight();
				listsHeight = $html.height();
				$(this.$input).off('keydown.autoComplete').on('keydown.autoComplete',$.proxy(this.inputKeydown,this,$('li:not(.disabled)',$html),$html,listsHeight,height));
				
			},
			inputKeydown:function(list,lists,listsHeight,height,e){
				if(this.manifestIsExisted){
						var code = e.keyCode,
							li = list.filter('.hover'),
							index = list.index(li); 
						// 向上
						if(code == 38 && index > 0){
							list.removeClass('hover');
							list.eq(--index).addClass('hover');
							// 执行滚动
							li = list.filter('.hover');
							var top = li.position().top;
							if((top) < 0){
								lists.get(0).scrollTop += (top-1);	
							}else if(top >= lists.height()){
								lists.get(0).scrollTop += (top-listsHeight);	
							}
							
							e.preventDefault();
						}
						// 向下
						else if(code == 40 && index < list.length-1){
							list.removeClass('hover');
							list.eq(++index).addClass('hover');
							// 执行滚动
							li = list.filter('.hover');
							var top = li.position().top;
							
							if(top < 0){
								lists.get(0).scrollTop += (top-height);	
							}else if((top+height) >= listsHeight){
								lists.get(0).scrollTop += (top+height-listsHeight);	
							}
							
							e.preventDefault();
						}
						// 回车
						else if(code == 13){
							li.click();
							e.preventDefault();
						}
					}
			},
			setAddSpan:function(thisData){
				var span = document.createElement('span'),
					i = document.createElement('i'),
					that = this;
				span.className = 'selected-item';
				span.innerHTML = thisData.text;
				i.className = 'icon-close';
				i.innerHTML = 'x';
				span.appendChild(i);
				$(i).click(function(e) {
					for(var i  = 0;i < that.selectData.length; i++ ){
						if(that.selectData[i].id == thisData.id){
							that.selectData.splice(i,1);
							break;	
						}
					}
					
						
					if(typeof that.opts.onBeforedeleteSelected == 'function'){
						var bool = that.opts.onBeforedeleteSelected();
							if(typeof bool == 'boolean' &&　!bool){
								return ;
							};
					}
					
					$(span).remove();
					if(!$('.selected-item',that.$div).length){
						that.$input.width('auto');
						that.$input.attr('placeholder',that.inputPlaceholder);
					}
					
					if(typeof that.opts.ondeleteSelected == 'function'){
						that.opts.ondeleteSelected();
					}
					
					e.stopPropagation();
				});
				this.$input.before(span);
			},
			getInnerHTML:function(data){
				var html = document.createElement('div'),
					ul = document.createElement('ul'),
					selectData = $.extend(true,[],this.selectData),
					hover = 0;
					
				html.className = "ui-manifest-options";
				html.style.top =this.$div.innerHeight() +'px';
				html.style['max-height'] = this.opts.height + 'px';
				ul.className = "ui-manifest-options-ul";
				
				
				if( data.length == 0){
					html.innerHTML = '<div class="noData">'+this.opts.noDataTips+'</div>';	
				}
				
				for(var i = 0; i < data.length ;i++){
					var bool=false,li = document.createElement('li');
					if(selectData.length){
						for(var a = 0 ; a < selectData.length;a++){
							if(data[i].id == selectData[a].id){
								bool=true;
								selectData.splice(a,1);
								break;
							}
						}
					}
					
					if(bool){
						li.className='disabled';
					}else if(!hover){
						li.className='hover';
						hover++
					}
					var typ=document.createAttribute("title");
					typ.nodeValue=data[i].text;
					li.attributes.setNamedItem(typ);
					li.manifestData = data[i];
					li.innerHTML = '<label>'+data[i].text+'</label>';
					ul.appendChild(li);
					
				}	
				html.appendChild(ul);	
				return html;
			},
			reomveALLSpan:function(){
				$('.selected-item',this.$div).remove();
				this.$input.attr('placeholder',this.inputPlaceholder);
				this.selectData=[];	
			},
			reomveOptions:function(){
				$(document).on('click',function(){
					ui.components.collapse();	
				});
			}
		}
		g.initialize(opts,that);
		that[0].manifest = g;
	}
	
	$.fn.manifest = function(opts){
		opts = $.extend({}, {
			height: 200, //设置弹出框最大高度
			type: 'POST',//设置AJAX请求类型
			async: false,//设置AJAX请求同步异步
			cache: false,//设置AJAX请求是否缓存
			className:'manifest_1',//设置AJAX请求的队列标志
			url: null,//设置AJAX请求url
			node:null,//设置本地数据的内容若设置了URL则不起作用
			defaultNode:[],//设置默认选中项
			params: {},//设置AJAX请求参数
			dataType: 'json',//设置AJAX返回的数据类型
			MILLISEC: 500,//设置输入后多少毫秒后开始动作
			noDataTips: '无符合结果',//设置无数据的提示
			failedTips: '请求失败，请刷新后重试',//设置AJAX请求失败后的提示
			pretreatment:null,//数据预处理
			onRowBeforeClick: null,//设置下拉项单击前的callback,返回值为false会阻止默认操作
			onRowClick: null,//设置下拉项单击的callback
			onBeforedeleteSelected:null,//设置选中项删除前的callback,返回值为false会阻止默认操作		
			ondeleteSelected:null//设置选中项删除后的callback		
		}, opts);
		return this.each(function(){
			manifest(opts,$(this));
		})
	};
	$.fn.getManifestData=function(){
		var data=[];
		this.each(function(){
			if(this.manifest){
				data.push(this.manifest.selectData);	
			}
		})
		if(data.length<=1){
			data = data[0];
		}
		return data
	};
	$.fn.clearManifestData=function(){
		this.each(function(){
			if(this.manifest){
				this.manifest.reomveALLSpan();
			}
		})
	};
})(jQuery);
