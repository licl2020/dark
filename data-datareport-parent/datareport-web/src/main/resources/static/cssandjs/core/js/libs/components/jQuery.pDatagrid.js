// JavaScript Document
(function($){
	if(!jqXhr) {var jqXhr={};};
	$.ajaxSingle = function (settings) {
		var options = $.extend({ className: 'DEFEARTNAME' }, $.ajaxSettings, settings);
	
		if (jqXhr[options.className]) {
			jqXhr[options.className].abort();
		};
		jqXhr[options.className] = $.ajax(options);
	};//对JS的AJAX进行拓展实现清除队列
	
	$.drawgrid = function(t, o){

		var a = $.extend({
			height: 500, // 最小高度
			width: "auto", // 宽度
			columns:"",// 显示列头
			setHFFixed:true,//列头和分页是否固定
			className:'datagrid',//ajax请求队列名用于清除队列
			striped: true, // 是否应用隔行换色效果
			multiSelect: false, // 是否启用多选
			addRowContent:null,//行自定义内容添加
			data: null, // 数据集合
			url: false, // 请求地址
			params: null, // 请求参数
			type: 'POST', // 请求方式
			dataType: 'json', // 数据类型
			loadingTips: '数据加载中', // 加载数据时的文本提示
			noDataTips: '未搜索到匹配项',
			total: 0, // 共多少条数据
			page: 1, // 当前页码
			pages: 1, // 共多少页
			usePage: true, // 是否使用分页
			useMean: true, // 是否使用每页显示多少条数据的条件选项
			sortField: null,//默认排序项
			sortType: 'asc',//默认排序方式asc升序 desc降序
			rpOptions: [15, 30, 50], // 每页显示多少条数据的条件选项
			defaultRp: 30, // 默认每页显示多少条数据
			preProcessData: null, // 预处理数据
			onSortChange: null, // 排序事件处理函数
			onError: null, // 请求出错时的处理函数
			onComplete: null, // 请求完成数据拼接后处理函数
			isShowRowContents:false,//设置每行下拉展示是否显示多个
            extendGridClass: function(g){ // 扩展g公共类
                return g;
            },
			// 
            extendCell: {
            	all: null
            },
            // 
            factory: {
                extendDataCol: function(div,o, field, cell, data){
                    var fn = o.extendCell[field];
                    if(fn && typeof fn === 'function'){
						
                        return fn(div,cell, data);
                    }
                   div.innerHTML=cell;
                }
            }
		}, o);
		
		// 创建g公共类
		var g = {
			setHtml:function(o){
				var head = '<div class="header-box"><div class="header"></div></div>',
				 	body='<ul class="body"></ul>',
					foot=[];
				//表格标签数据
				o.selector={H:{},B:{},F:{}};	
				o.head = $(head);
				$('.header',o.head).append(this.gethead(o));
				o.body = $(body);
				t.append(o.head);
				t.append(o.body);
				o.selectedRows=[];
				//创建页脚
				foot.push('<div class="footer-box">','<div class="footer">','<div class="ui-datagrid-foot-total">共<span>'+o.total+'</span>条</div>');
				if(o.usePage){
					foot.push('<div class="ui-datagrid-foot-buttons">');
					foot.push(' <div class="group"><div title="首页" class="ui-datagrid-foot-button first"></div>');
					foot.push('<div title="上一页" class="ui-datagrid-foot-button prev"></div></div>');
					foot.push('<div class="group"><div class="current"><label class="label">第</label><input type="text" value="'+o.page+'" class="page"/><label class="label">页</label></div>'); 
					foot.push('<div class="total"><label class="label">共</label><label class="pages">'+o.pages+'</label><label class="label">页</label></div></div>');
					foot.push('<div class="group"><div title="下一页" class="ui-datagrid-foot-button next"></div><div title="尾页" class="ui-datagrid-foot-button last"></div></div>');
				};
				if(o.setHeader && typeof o.setHeader =='function'){
					foot.push('<div class="group"><div title="编辑列头" class="ui-datagrid-foot-button refresh-head"></div></div>');
				};
				foot.push('<div class="group"><div title="刷新" class="ui-datagrid-foot-button refresh"></div></div><div class="group">');
				
				if(o.useMean && o.usePage){
					foot.push('<select class="mean" name="mean">');

					for(var i = 0; i< o.rpOptions.length ; i++){
						
						foot.push('<option  '+((o.rpOptions[i] == o.defaultRp)?"selected":"")+' value="'+o.rpOptions[i]+'">'+o.rpOptions[i]+'</option>');
						
					}
					
					foot.push('</select');
				}
				foot.push('</div></div></div></div>');
				
				//储存
				o.foot = $(foot.join(''));
				
				t.append(o.foot);
				//表格footer标签数据
				o.selector.F={
					footer      :   o.foot[0],
					total       :   $('.ui-datagrid-foot-total span',o.foot),
					page        :   $('.ui-datagrid-foot-buttons .page',o.foot),
					pages		:	$('.ui-datagrid-foot-buttons .pages',o.foot),
					refreshHead :	$('.ui-datagrid-foot-buttons .refresh-head',o.foot),
					refresh		:	$('.ui-datagrid-foot-buttons .refresh',o.foot),
					first		:	$('.ui-datagrid-foot-buttons .first',o.foot),
					prev		:	$('.ui-datagrid-foot-buttons .prev',o.foot),
					next		:	$('.ui-datagrid-foot-buttons .next',o.foot),
					last		:	$('.ui-datagrid-foot-buttons .last',o.foot),
					mean		:	$('.ui-datagrid-foot-buttons .mean',o.foot)
				};
					
				this.setData(o);
				t[0].dataGrid={
					o:o,
					g:g	
				};	
			},
			setData:function(o){
				$(o.selector.H.checkbox).prop('checked',false);
				o.body.html(o.loadingTips);
				if(!!o.url){
					this.ajax($.extend({},o.params||{},o.gridSearch||{},{'page.pageNum': o.page-0, 'page.pageSize': o.defaultRp-0, 'page.sortField': o.sortField, 'page.sortType': o.sortType }),o);
				}else{
					o.params=[];
					g.addHtml([],o);
				}	
			},
			ajax:function(params,o){
				var url = (o.url && typeof o.url == 'function') ? o.url() : o.url;
					$.ajaxSingle({
						type: o.type,
						url: url,
						data: params,
						className:o.className,
						dataType: o.dataType,
						success: function(data){
							if(typeof  o.preProcessData == "function"){
								o.preProcessData(data);
							}
							o.data=data;
							o.page=data.page;
							g.addHtml(data,o);
							
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							try{
								if(_mode.isFunction(o.onError)){
									o.onError(XMLHttpRequest, textStatus, errorThrown);
								}
							}catch(e){  }
						}
					});
			},
			addHtml:function(data,o){
				//window.console.profile("report1");//采样开始  
					//期间的操作，会被采样  
				this.setBody(o,data);
				if(typeof  o.onComplete == "function"){
					o.onComplete(data);
				}
				this.setFooter(o,data);
				this.setElemEvent(o,data);
				if(o.setHFFixed){
					this.setGridPosition(o);
				};
				//window.console.profileEnd();//采样结束  
			},
			gethead:function(o){
				var table=document.createElement('table'),colgroup=document.createElement('colgroup'),thead=document.createElement('thead'),tr=document.createElement('tr'),sortS=[];
				if(o.multiSelect){
					var col=document.createElement('col'),th=document.createElement('th'),input=document.createElement('input');
					col.width="32";
					input.type="checkbox";
					th.className="grid-checked";
					colgroup.appendChild(col);
					th.appendChild(input);
					tr.appendChild(th);
					o.selector.H.checkbox=input;
				}
				
				for(var i=0;i<o.columns.length;i++){
					var columns=o.columns[i],col=document.createElement('col'),th=document.createElement('th'),span=document.createElement('span');
					col.width=columns.width || "";
					if(!o.multiSelect && i==1){
						span.className="first-text";
					}

					span.innerHTML=columns.display || "";
					colgroup.appendChild(col);

					if(columns.sortable){
						var div=document.createElement('div'),iA=document.createElement('i'),iD=document.createElement('i');
						iA.className="i1";
						iD.className="i2";
						div.appendChild(iA);
						div.appendChild(iD);
						div.className="sort"+(o.sortField && (columns.field == o.sortField)?" "+o.sortType:'');
						div.dataGrid=columns;
						sortS.push(div);
						th.appendChild(div);
					};
					th.appendChild(span);
					tr.appendChild(th);
				}
				if(o.action || o.action.buttons){
					var col=document.createElement('col'),th=document.createElement('th'),span=document.createElement('span');
					col.width=o.action.width || "";
					
					th.align=o.action.align;
					span.innerHTML=o.action.display || "";
					colgroup.appendChild(col);
					th.appendChild(span);
					tr.appendChild(th);
				}
				o.selector.H.sortS=sortS;
			    o.selector.H.tr=tr;
				thead.appendChild(tr);
				table.appendChild(colgroup);
				table.appendChild(thead);
				return table;
			},
			setBody:function(o,data){
				var LI=[],BC=[],TR=[],DIV=[];
				 for(var i = 0; i<data.rows.length;i++){
					 var li=document.createElement('li'),table=document.createElement('table'),colgroup=document.createElement('colgroup'),tbody=document.createElement('tbody'),tr=document.createElement('tr'),div=document.createElement('div');
					if(o.multiSelect){
						var col=document.createElement('col'),td=document.createElement('td'),input=document.createElement('input');
						col.width="32";
						input.Index=i;
						input.type="checkbox";
						if(typeof o.multiSelect == 'function'){
							o.multiSelect(data.rows[i],input,tr,data,o);
						}
						td.className="grid-checked";
						$(td).click(function(e) {
						   e.stopPropagation(); 
						});
						input.dataGrid={};
						input.dataGrid.row=data.rows[i];
						colgroup.appendChild(col);
						td.appendChild(input);
						tr.appendChild(td);
						BC.push(input);
					}	
					this.setBodyRow(o,data.rows[i],tr,colgroup);	
					
					if(o.action || o.action.buttons){
						this.setBodyAction(o,data.rows[i],tr,colgroup);	
					}
					
					//判断单双实现隔行换色
					if( o.striped && i%2){
						li.className="odd";
					};
					
					tbody.appendChild(tr);
					table.appendChild(colgroup);
					table.appendChild(tbody);
					li.appendChild(table);
					li.appendChild(div);
					TR.push(tr);
					LI.push(li);
					DIV.push(div);
				 }
				 if(o.multiSelect){
				 	o.selector.B.checkbox=BC;
				 }
				 o.selector.B.li=LI;
				 o.selector.B.tr=TR;
				 o.selector.B.div=DIV;
				 o.body.html(LI);
			},
			setBodyRow:function(o,row,tr,colgroup){
				var TD=[]
				for(var i=0; i<o.columns.length; i++){
					var columns=o.columns[i],col=document.createElement('col'),td=document.createElement('td'),div=document.createElement('div');
					col.width=columns.width || "";
					o.factory.extendDataCol(div,o,columns.field,row.cols[columns.field],row);
					td.appendChild(div);
					colgroup.appendChild(col);
					tr.appendChild(td);	
				}
			},
			setBodyAction:function(o,row,tr,colgroup){
				var col=document.createElement('col'),td=document.createElement('td');
					col.width=o.action.width || "";
				for(var i=0;i<o.action.buttons.length;i++){
					var button=o.action.buttons[i],span=document.createElement('span');
					span.className="ui-datagrid-link mr5 ";
					span.innerHTML=button.display || "";					
					if(button.callback && typeof button.callback =="function") button.callback(span,button,row);
					if(button.isDisabled && typeof button.isDisabled == "function" && button.isDisabled(row)) span.className+='ui-datagrid-link-disabled';
					if(button.onClick && typeof button.onClick =="function") {
						span.onclick=(function(row,button){
							return function(e){
								e =  window.event || e;
								button.onClick(row,button);
								e.stopPropagation();
							};
						})(row,button);
					}
					if(button.onComplete && typeof button.onComplete =="function") button.onComplete(span,button,row);
					td.appendChild(span);
				}
				tr.appendChild(td);	
				colgroup.appendChild(col);
				
			},
			setFooter:function(o,data){
				o.selector.F.total.text(data.total);	
				o.selector.F.page.val(data.page);
				o.pages=Math.ceil(data.total/o.defaultRp);
				o.selector.F.pages.text(o.pages);
			},
			setElemEvent:function(o,data){
				var that=this;
				$(o.selector.B.li).off('click').on('click',function(e) {
                    var i=$(this).index(),div=o.selector.B.div[i];
					if(!div.open){
						if(!o.isShowRowContents){
							for(var a=0;a<o.selector.B.div.length;a++){
								o.selector.B.div[a].style.display="none";
								o.selector.B.div[a].innerHTML="";
								o.selector.B.div[a].open=false;	
							}
						}
						if(typeof o.addRowContent =='function'){
							o.addRowContent(div,data.rows[i],data,o);
							div.open=true;
						}
						div.style.display="block";
					}else{
						div.style.display="none";
						div.innerHTML="";
						div.open=false;	
					}
					$(window).scroll();
                });
				$('i',o.selector.H.sortS).off('click').on('click',function(){
					var elem=$(this);tr=$(this).parent();
					$(o.selector.H.sortS).attr('class','sort');

					if(elem[0].className == 'i1'){
						tr.addClass('asc');
						o.sortType='asc';
					}else{
						tr.addClass('desc');
						o.sortType='desc';
					};
					o.sortField=tr[0].dataGrid.sortField || tr[0].dataGrid.field;
					that.setData(o);
				});

				$(o.selector.H.checkbox).off('change').on('change',function(e) {
					$(o.selector.B.checkbox).not('[disabled]').prop("checked",$(this).prop("checked")).change();
                });
				$(o.selector.B.checkbox).off('change').on('change',function(e){
					var index=this.Index,bool=true;
					if(this.checked){
						$(o.selector.B.li[index]).addClass("active");
					}else{
						$(o.selector.B.li[index]).removeClass("active");
					};
					for(var x=0; x<o.selector.B.checkbox.length;x++){
						if (!o.selector.B.checkbox[x].checked && !o.selector.B.checkbox[x].disabled){
							bool=false;
						}
					};
					o.selector.H.checkbox.checked= bool;
				});
				
				//为写好。页脚事件未写
				o.selector.F.page.off('change').on('change',function(e){
					value=o.selector.F.page.val();
					o.page=value;
					value > Number(o.pages) && (o.page = Number(o.pages));
					value < 1 && (o.page = 1);
					that.setData(o);
				});
				o.selector.F.first.off('click').on('click',function(){
					o.page=1;
					that.setData(o);
				});
				o.selector.F.prev.off('click').on('click',function(){
					o.page--;
					if(o.page<1){
						o.page=1;
					};
					if(o.page>o.pages){
						o.page=o.pages;
					}
					that.setData(o);
				});
				o.selector.F.next.off('click').on('click',function(){
					o.page++;
					o.page>o.pages && (o.page=o.pages);
					that.setData(o);
				});
				o.selector.F.last.off('click').on('click',function(){
					o.page=o.pages;
					that.setData(o);
				});
				
				o.selector.F.refresh.off('click').on('click',function(){
					that.setData(o);
				});
				
				o.selector.F.mean.off('change').on('change',function(){
					o.page=1;
					o.defaultRp=$(this).val();
					that.setData(o);
				});
			},
			GetRect:function(element){
				var rect   = element.getBoundingClientRect();
				
				var top    = document.documentElement.clientTop;

				var left   = document.documentElement.clientLeft;

				var height = document.documentElement.clientHeight;

				var width  = document.documentElement.clientWidth;
				
				var sT     = document.documentElement.scrollTop;
				
				var sL     = document.documentElement.scrollLeft;
				return{
					wWidth  :   width,//返回浏览器宽度
					wHeight :   height,//返回浏览器高度
					top     :   rect.top - top + sT,//返回元素相对浏览器距离
					left    :   rect.left - left + sL,//返回元素相对浏览器距离
					x       :   rect.top,//返回元素相对窗体距离
					y       :   rect.left//返回元素相对窗体距离
				}
			},
			setGridPosition:function(o){
				var that=this,heightH=o.head.height(),heightF=o.foot.height(),header=$('.header',o.head)[0],footer=$('.footer',o.foot)[0];
				

				$(window).off('scroll').on('scroll',function(event){
					var headPosition=that.GetRect(o.head[0]),footPosition=that.GetRect(o.foot[0]);
					if(headPosition.x < 0 && footPosition.x > -(heightF - heightH)){
						header.className='header fixed';
					}else{
						header.className='header';
					};
					if(footPosition.x > (footPosition.wHeight-heightF) && (headPosition.wHeight-heightH) > headPosition.x){
						footer.className='footer fixed';
					}else{
						footer.className='footer';
					};
				}).off('resize').on('resize',function(){
					var widthH=o.head.width(),widthF=o.foot.width();
					header.style.width = (widthH-2) + 'px';
					footer.style.width = (widthF-12) + 'px';
					$(window).scroll();
				});
				$(window).resize();
			}
		}
		g.setHtml(a);
	}
	
	//渲染grid
	$.fn.pDatagrid = function(o){

		return this.each(function(){
			var _this =$(this);
			_this.addClass('new-grid');
			$.drawgrid(_this, o);
		});
	};	
	
	//查询内容
	$.fn.pGridSearch = function(a){
		return this.each(function(){
			if(this.dataGrid && this.dataGrid.g &&  this.dataGrid.o){
				var o=this.dataGrid.o,g=this.dataGrid.g;
				o.gridSearch=a;
				g.setData(o);				
			}
		});	
	}
	//刷新内容
	$.fn.pGridRefresh = function(){
		return this.each(function(){
			if(this.dataGrid && this.dataGrid.g &&  this.dataGrid.o){
				var o=this.dataGrid.o,g=this.dataGrid.g;
				g.setData(o);
			}
		});
	};
	
	//返回选择项
	$.fn.pGetSelectedRows = function(){
		var selectedRows=[];
		this.each(function(){
			if(this.dataGrid && this.dataGrid.g &&  this.dataGrid.o){
				var o=this.dataGrid.o,g=this.dataGrid.g;
				if(o.selector.H.checkbox.checked){
					selectedRows=o.data.rows;	
				}else{
					var checkbox=o.selector.B.checkbox;
					for( var i = 0 ;i< checkbox.length ;i++){
						if(checkbox[i].checked){
							selectedRows.push(checkbox[i].dataGrid.row);	
						}
					}	
				}
			}
		});
		return selectedRows;
	};
	
	$.fn.pGetParams =function(){
		var params;
		this.each(function(){
			if(this.dataGrid && this.dataGrid.g &&  this.dataGrid.o){
				params = this.dataGrid.o.params;
			}
		});
		return params;
	};
})(jQuery);