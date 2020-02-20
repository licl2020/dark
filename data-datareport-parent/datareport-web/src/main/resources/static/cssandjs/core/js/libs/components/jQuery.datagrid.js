// JavaScript Document
(function($){
	if(!jqXhr) {var jqXhr={};};
	$.ajaxSingle = function (settings) {
		var options = $.extend({ className: 'DEFEARTNAME' }, $.ajaxSettings, settings);
	
		if (jqXhr[options.className]) {
			jqXhr[options.className].abort();
		}
		jqXhr[options.className] = $.ajax(options);
	};//对JS的AJAX进行拓展实现清除队列
	
	$.drawgrid = function(t, o){
		o.height=o.minHeight || o.height;//最小高度
		if(t.datagrid){
			return false;
		}
		
		var tid = t.id;
		
		if(!tid){
			return t;
		}		
		var a = $.extend({
			height: '500', // 最小高度
			width: "auto", // 宽度
			allColumns:"",// 所有列头
			columns:"",// 显示列头
			loadType:'window.load',//默认加载方式'window.load'加载，若非'window.load'即默认不加载
			className:'datagrid',//ajax请求队列名用于清除队列
			striped: true, // 是否应用隔行换色效果
			multiSelect: false, // 是否启用多选
            colResizable: false, // 是否允许调整列宽
			colMinWidth: 30, // 最小列宽
            addTitleToCell: true, //添加title
			data: null, // 数据集合
			url: false, // 请求地址
			params: null, // 请求参数
			type: 'GET', // 请求方式
			dataType: 'json', // 数据类型
			loadingTips: '数据加载中', // 加载数据时的文本提示
			noDataTips: '未搜索到匹配项',
			total: 0, // 共多少条数据
			page: 1, // 当前页码
			pages: 1, // 共多少页
			pageCross:false,//跨页选取
			usePage: true, // 是否使用分页
			useMean: true, // 是否使用每页显示多少条数据的条件选项
			sortField: null,//默认排序项
			showDataTotal:true,
			sortType: 'asc',//默认排序方式asc升序 desc降序
			rpOptions: [10,20, 50, 100], // 每页显示多少条数据的条件选项
			defaultRp: 10, // 默认每页显示多少条数据
			preProcessData: null, // 预处理数据
			onSortChange: null, // 排序事件处理函数
			onError: null, // 请求出错时的处理函数
			onComplete: null, // 请求完成数据拼接后处理函数
			onRowDblclick: null, // 行双击事件
			onRowSingleclick:null,//单击事件
			setHeader:null,//设置列头function
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
			getCompatible:function(){
				var bb = /msie ([\d.]+)/.test(navigator.userAgent.toLowerCase()) && navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[0].match(/\d+(?=.)/);
				return bb?bb:100;
			},
			GetRect:function(element){
				var rect = element.getBoundingClientRect();
				
				var top = document.documentElement.clientTop;
				
				var left= document.documentElement.clientLeft;
				
				var sT=document.documentElement.scrollTop;
				
				var sL=document.documentElement.scrollLeft;
				return{
					top    :   rect.top - top + sT,//返回元素相对浏览器距离
					left   :   rect.left - left + sL,//返回元素相对浏览器距离
					x      :   rect.top,//返回元素相对窗体距离
					y      :   rect.left//返回元素相对窗体距离
				}
			},
			bodyHtml:function(o){
				return '<div class="ui-datagrid-body-inner no-data" style="height:'+o.height+'px;line-height:'+o.height+'px;text-align:center;"><span class="loading">'+(o.loadType === "window.load"?o.loadingTips:o.noDataTips)+'</span></div>';
			},
			setHtml:function(o){
				var head = '<div class="ui-datagrid-head clearfix"><div class="ui-datagrid-head-inner"><table></table></div></div>',
				 	body='<div id="'+tid+'" class="ui-datagrid-body">'+this.bodyHtml(o)+'</div>',
					drag='<div class="ui-datagrid-drag" style="top: 0px;"></div>',
					foot=['<div class="ui-datagrid-foot">','<div class="ui-datagrid-foot-total">共<span>'+o.total+'</span>条</div>'];
					foot.push('<div class="ui-datagrid-foot-buttons">')
					//返回setHeader<label class="label">页</label>
					// <label class="label">第</label>
					foot.push(' <div class="group"><div class="ui-datagrid-foot-button first">首页</div>');
            		foot.push('<div  class="ui-datagrid-foot-button prev">上一页</div></div>');
           			foot.push('<div class="group"><div class="current"><input type="text" value="'+o.page+'" class="page"/></div>'); 
                    foot.push('<div class="total"><label class="label">共</label><label class="pages">'+o.pages+'</label><label class="label">页</label></div></div>');
					foot.push('<div class="group"><div  class="ui-datagrid-foot-button next">下一页</div><div class="ui-datagrid-foot-button last">末页</div></div>');
					if(o.setHeader && typeof o.setHeader =='function'){
						foot.push('<div class="group"><div title="编辑列头" class="ui-datagrid-foot-button refresh-head"></div></div>');
					};
					foot.push('<div class="group"><div title="刷新" class="ui-datagrid-foot-button refresh"></div></div><div class="group">');
                	if(o.useMean){
						foot.push('<select class="mean" name="mean">');
	
						for(var i = 0; i< o.rpOptions.length ; i++){
							
							foot.push('<option  '+((o.rpOptions[i] == o.defaultRp)?"selected":"")+' value="'+o.rpOptions[i]+'">'+o.rpOptions[i]+'条/页</option>');
							
						}
						
						foot.push('</select');
					}
                	foot.push('</div></div></div>'),
					div= $('<div class="ui-datagrid" style=" width:'+(typeof o.width == 'number' ?o.width+'px':o.width)+'"></div>');
					
					foot=foot.join('');
				this.datagrid = div;
				windowHeight=$(window).height();
				o.head = $(head);
				
				
				o.body = $(body);
				o.drag = $(drag);
				o.foot = $(foot);
				$('table',o.head).append(this.gethead(o));
				//替换标签
				this.div=div;
				$(t).replaceWith(div);
				o.defaultPage =o.page;
				//添加dom元素
				div.append(o.head);
				//添加dom元素
				div.append(o.body);
				
				var windowHe=windowHeight-this.GetRect(o.body[0]).top-160,tHAt=this; //控制标签的高度
				
				if(windowHe >= o.height){
					
					o.nowHeight = windowHe;
					
				}else{
					o.nowHeight = o.height;
				}
				o.body.children("div").height(o.nowHeight);
				o.body.children("div.no-data").css("line-height",o.nowHeight+"px");
				
				$("div",o.drag).height(o.nowHeight+50);
				
				$(window).resize(function(){
					windowHeight=document.documentElement.clientHeight-$(window).scrollTop();
					var windowHe=windowHeight-tHAt.GetRect(o.body[0]).top-160;
					
					if(windowHe > o.height){
						
						o.nowHeight = windowHe;
					}
					
					o.body.children("div").height(o.nowHeight);
					o.body.children("div.no-data").css("line-height",o.nowHeight+"px");
					$("div",o.drag).height(o.nowHeight+50);
				})
				//添加dom元素
				div.append(o.drag);
				//添加dom元素
				o.usePage && div.append(o.foot);
				
				this.deleteCacheRows(o);
				
				if(!!o.url && o.loadType === "window.load"){
					// 'page.sortField': o.sortField, 'page.sortType': o.sortType
					this.ajax($.extend(o.params||{},{'page': o.page, 'pageSize': o.defaultRp ,'sortField': o.sortField, 'sortType': o.sortType}),o);
					
				}else{
					o.loadType = "window.load";
					o.params=[];
					g.addData([],o);
				}		
			},
			ajax:function(params,o){
				var url = (o.url && typeof o.url == 'function') ? o.url() : o.url;				
				//console.log(params);
					$.ajaxSingle({
						type: o.type,
						url: url,
						data: params,
						className:o.className,
						dataType: o.dataType,
						success: function(data){
							o.params=params;
							g.addData(data,o);
							
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							try{
								if(_mode.isFunction(o.onError)){
									o.onError(XMLHttpRequest, textStatus, errorThrown);
								}
							}catch(e){  }
						}
					})
				},
			addData:function(data,o){
				
				if( !data || !data.rows  || !data.rows.length){
                    if(o.onComplete && typeof o.onComplete == "function"){
                    	o.onComplete(data);
                    }
					
					$('.ui-datagrid-body-inner span',o.body).html(o.noDataTips).addClass("nothing");
					
					data || (data={});
				
					data.o=o;
					
					data.g=g;
					
					o.body[0].gridData=data;
					$('.ui-datagrid-foot-total span',o.foot).text('0');
					$('.current input.page',o.foot).val(data.page || o.defaultPage);
					$('.total .pages',o.foot).text(o.pages);
					//注册事件
				
					this.regEvent(data,o);
					
					return false;
				}else{
					$('.ui-datagrid-body-inner',o.body).removeClass('no-data');
				}
				

				if(o.preProcessData && typeof o.preProcessData  == "function"){//数据预处理
					data = o.preProcessData(data);
				}
				
				
				var that=this;
				
				t = $('<table  width='+this.allWidth+'></table>');
				
				o.t = t;
				
				if(o.pageCross){
					this.setListChecked(data,o);
				}
				
				t.append(that.getbody(data,o));
				
				if(o.onComplete && typeof o.onComplete == "function"){
					o.onComplete(data);
				}
				
				$('.ui-datagrid-body-inner',o.body).css("line-height",'').html(t);
				
				//t.width(this.allWidth);
				//填写页码
				
				$('.ui-datagrid-foot-total span',o.foot).html(data.total);
				//填写当前页
				
				o.page = Number(data.page);
				
				//console.log(data)
				$('.current input.page',o.foot).val(data.page);
				
				//计算填写总页数
				$('.group .total label.pages',o.foot).html(Math.ceil(data.total/Number(o.defaultRp)));
				
				//注册事件
				
				this.regEvent(data,o);
				
				data.o=o;
					
				data.g=g;
				
				o.body[0].gridData=data;
			},
			setListChecked:function(data,o){
				var selectedRows=o.selectedRows,rows=data.rows,allBool=true;
				for(var i = 0;i < rows.length;i++){
					var bool=false;
					for(var a = 0;a < selectedRows.length;a++){
						if(selectedRows[a].row.id == rows[i].id){
							bool=true;
						}
					}
					rows[i].checked=bool;
					bool || (allBool=false);
				}
				$('input:checkbox',o.head).prop("checked",allBool);
			},
			gethead:function(o){
				if(!o.columns) return ;
				
				var thead = document.createElement('thead'),tr=document.createElement('tr'),allWidth=0,lineDiv=[],lineLeft=0,colgroup=document.createElement("colgroup");
				
				if(o.multiSelect ||  typeof o.multiSelect  == 'function'){
					
					var thSelect=$('<th align="center" name="select" column="select"><label class="ui checkBox"><input type="checkbox" class="ui-datagrid-checkbox" button="select" /><span></span></label></th>')
					
					tr.appendChild(thSelect[0]);
					var col=document.createElement("col");
					
					if(this.getCompatible() <= 7){
						col.width=23;
					}else{
						col.width=34;	
					}
					colgroup.appendChild(col);
					
					//添加th标签的同时添加动态添加滑动线
					//lineDiv.push('<div style="width: 5px; height:'+(o.height+32)+'px; left: 31px; display: none;"></div>');
					
					lineDiv.push('<div style="width: 5px; left: 31px; display: none;"></div>');
					
					lineLeft=30;
					
					allWidth += 23;
				}else{
					lineLeft=-4;	
				}
				var thisG=this;
				for(var i = 0; i < o.columns.length; i++){
					
					var th = document.createElement('th'),thHTML =o.columns[i].display,width=o.columns[i].width || o.colMinWidth;
					
					o.columns[i].sortable == true && (th.className="sort");
					
					if(o.sortField && (o.columns[i].field == o.sortField)){
						$(th).addClass(o.sortType);
					}
					
					
					
					th.onclick=(function(a){
						return function(){
							if(a.sortable == true ){
								var that=$(this);
								if(!that.attr("class") || that.hasClass("desc")){
									that[0].className='sort asc';
									that.attr("sort",'asc') ;
									o.sortType='asc';
								}else{
									that[0].className='sort desc';
									that.attr("sort",'desc') ;	
									o.sortType='desc';

								}
								that.siblings().removeClass('asc').removeClass('desc');
								o.page=1;
								
								o.sortField=a.field;
								o.onSortChange && typeof o.onSortChange =="function" && o.onSortChange(o);
								thisG.splitData(o);
							}
						}
					})(o.columns[i]);
					allWidth += width;
					
					lineLeft+=width;
					 
					//lineDiv.push('<div style="width: 5px; height:'+(o.height+32)+'px; left:'+(lineLeft+1)+'px; display: block;"></div>');
					lineDiv.push('<div style="width: 5px; left:'+(lineLeft+1)+'px; display: block;"></div>');
					
					//创建table宽度标签
					var col=document.createElement("col");
					
					if(this.getCompatible() <= 7){
						col.width=width-11;
					}else{
						col.width=width;	
					}
					colgroup.appendChild(col);
					
					 
					thHTML = '<div class="ui-datagrid-cell '+(o.columns[i].style || "")+'"  title="'+thHTML+'">'+thHTML+'</div>';
					
					th.innerHTML = thHTML;
					
					tr.appendChild(th);
				}	
				
				
					
				if (o.action && o.action.buttons){
					var th = document.createElement('th'),cellWidth=o.action.width || o.colMinWidth;
					
					allWidth += cellWidth;
					
					lineLeft=lineLeft+cellWidth;
					
					//lineDiv.push('<div style="width: 5px; height:'+(o.height+50)+'px; left:'+(lineLeft+1)+'px; display: block;"></div>');
					lineDiv.push('<div style="width: 5px; left:'+(lineLeft+1)+'px; display: block;"></div>');
					
					//创建table宽度标签
					var col=document.createElement("col");
					
					if(this.getCompatible() <= 7){
						col.width=cellWidth-11;
					}else{
						col.width=cellWidth;	
					}
					colgroup.appendChild(col);

					
					th.innerHTML='<div class="ui-datagrid-cell">'+o.action.display+'</div>';
					
					tr.appendChild(th);				
				}
				
				//全局存放对象thead colgroup，tbody colgroup；
				o.theadColgroup=colgroup;
				
				thead.appendChild(tr);
				
				//设置head表格的宽度
				$('table',o.head).append(colgroup);
				$('table',o.head).width(allWidth);
				 
				
				//设置body表格的宽度
				this.allWidth = allWidth;
				if(o.colResizable){
					o.drag.append(lineDiv.join(''));
				}
				return 	thead
				//未完成位置
			},
			getbody:function(data,o){
				
				this.tdSelect=null;
				
				var tbody = document.createElement('tbody'),colgroupTbody=document.createElement("colgroup");
				
				for (var a = 0; a < data.rows.length; a++){
					var tr=document.createElement('tr');
					if(o.striped){
						a%2 == 1 && (tr.className='odd');
					}
					data.rows[a].id && (tr.id=data.rows[a].id);
					
					data.rows[a].style && ($(tr).addClass(data.	rows[a].style));
					
					if(o.multiSelect || typeof o.multiSelect  == 'function' ){
						
//						var tdSelect=['<td align="center" name="select" column="select"><label class="ui checkbox">'];
//						
//							tdSelect.push('<input type="checkbox" class="ui-datagrid-checkbox" button="select" />');
//							tdSelect.push('<span></span></label></td>');
//							
						var tdSelect=document.createElement('td'),labelSelect=document.createElement('label'),inputSelect=document.createElement('input'),spanSelect=document.createElement('span');
						(data.rows[a].checked && !data.rows[a].disabled) && (tr.className='selected');
						inputSelect.checked=data.rows[a].checked;
						inputSelect.disabled=data.rows[a].disabled;
						inputSelect.type='checkbox';
						inputSelect.className='ui-datagrid-checkbox';
						labelSelect.className='ui checkBox';
						if(typeof o.multiSelect  == 'function'){
							o.multiSelect({row:data.rows[a]},inputSelect);
						}
						labelSelect.appendChild(inputSelect);	
						labelSelect.appendChild(spanSelect);
						tdSelect.align='center';
						tdSelect.appendChild(labelSelect);
						tdSelect=$(tdSelect);
						
						labelSelect.parentTr=tr;

						labelSelect.onchange=(function(a,tr){
							return function(e){
								if(this.checked){
									
									$(tr).addClass('selected');

								}else{

									$(tr).removeClass('selected');

								}
							};
						})(a,tr);
						if( a==0){
							var colTbody=document.createElement("col");
							
							if(this.getCompatible() <= 7){
								colTbody.width=23;
							}else{
								colTbody.width=34;	
							}
							colgroupTbody.appendChild(colTbody);
						}
						tr.appendChild(tdSelect[0]);
						//全局存放对象td input:checkbox；
						//{elem:this,row:data.rows[a]}
						this.tdSelect ? this.tdSelect.push({elem:labelSelect,row:data.rows[a],checkbox:inputSelect}):this.tdSelect=[{elem:labelSelect,row:data.rows[a],checkbox:inputSelect}];
					}	
					
					
					for(var i = 0; i < o.columns.length; i++){
					
						var td = document.createElement('td'),tdHTML = data.rows[a].cols[o.columns[i].field],text = tdHTML,div=document.createElement("div");
						div.className="ui-datagrid-cell "+(o.columns[i].style || "");
						tdHTML = tdHTML || ( typeof text == 'undefined' ?"":text);
						
						//自定义拼接内容
						o.factory.extendDataCol(div,o,o.columns[i].field,tdHTML,data.rows[a]);
						
						td.appendChild(div);
						
						if(o.columns[i].field && (o.columns[i].field == o.sortField)){
							td.className='sort';
						}
						
						if(o.addTitleToCell){
							$(td).attr("title",$(td).text());
						}
						tr.appendChild(td);
						if( a == 0 ){
							var colTbody=document.createElement("col");
							if(this.getCompatible() <= 7){
								colTbody.width=o.columns[i].width-11 || o.colMinWidth-11;
							}else{
								colTbody.width=o.columns[i].width || o.colMinWidth;
							}
							colgroupTbody.appendChild(colTbody);
						}
						
					}
					

					
					//添加操作列
					if (o.action && o.action.buttons){
						 var td = document.createElement('td'),div = document.createElement('div');
						 td.style="text-align:left"
						 div.className='ui-datagrid-cell';
						 for(var n = 0; n< o.action.buttons.length ;n++){
							 var label = document.createElement('label');
							//  if(o.addTitleToCell){
							//  	label.title = o.action.buttons[n].display;
							//  }
							 var cla=(o.action.buttons[n].isDisabled && o.action.buttons[n].isDisabled(data.rows[a]))?'ui-datagrid-link-disabled':'';
							 var claHidden=(o.action.buttons[n].isHidden && o.action.buttons[n].isHidden(data.rows[a]))?'ui-datagrid-link-hidden':'';
							 
							 label.className = "ui-datagrid-link mr5 "+ cla + claHidden + ' '+ o.action.buttons[n].btnClass;
						 	 label.innerHTML = o.action.buttons[n].display;
							if( !cla ) { //未禁用的注册点击事件
								label.onclick=(function(data,n){
			
									if (o.action.buttons[n].onClick  && typeof  o.action.buttons[n].onClick == 'function' )  return function() {var x={row:data};o.action.buttons[n].onClick(x);}
									
								})(data.rows[a],n);
							}
							div.appendChild(label);
						}
					   td.appendChild(div);
					   tr.appendChild(td);
					   if( a == 0 ){
							var colTbody=document.createElement("col");
							if(this.getCompatible() == 7){
								colTbody.width=o.action.width-11 || o.colMinWidth-11;
							}else{
								colTbody.width=o.action.width || o.colMinWidth;
							}
							colgroupTbody.appendChild(colTbody);
						}
					}

					tr.ondblclick=(function(data){
						
						if (o.onRowDblclick && typeof  o.onRowDblclick == 'function' ) return  function (){var x={row:data};o.onRowDblclick(x)};
						
					})(data.rows[a]);
					
					tr.onclick=function(e){
						
						//
						
					}
					//全局存放对象tbody tr；
					tbody.appendChild(tr);
				}
				
				
				o.t.append(colgroupTbody);
				
				o.tbodyColgroup=colgroupTbody;
				
				return 	tbody
			},
			regEvent:function(data,o){
				
				var that=this;
				
				
				$('.ui-datagrid-body-inner',o.body).scroll(function(){
					o.head.scrollLeft(this.scrollLeft);
					o.drag.css('left',-this.scrollLeft);
				})
				
				
				//head复选框注册事件
				$('input:checkbox',o.head).off('change').on('change',function(event){
					var checked = this.checked;
					$(that.tdSelect).each(function(index, element) {
						if(!this.checkbox.disabled){
							if(this.checkbox.checked=checked){
								$(this.elem.parentTr).addClass('selected');	
							}else{
								$(this.elem.parentTr).removeClass('selected');
							}
						}
                    });
					
					event.stopPropagation();
					
				});
				//复选框注册事件
				$(that.tdSelect).each(function(index, element) {
					$(this.elem).off('change.thSelect').on('change.thSelect',function(){

						var bool=true,checked=this.checked,rowId=this.rowId;

						if($('input',this).prop('checked')){
							$(this).parent().parent().addClass('selected');
						}else{
							$(this).parent().parent().removeClass('selected');
						}
						
						$(that.tdSelect).each(function(index, element) {
							if(!this.checkbox.disabled){
								var checked=this.checkbox.checked;
								if(!checked){
									bool=false;
									return false;
								};
							}
	                    });
						
						$('input:checkbox',o.head).prop('checked',bool);
						
						
					});
				});
				var pages= Math.ceil(data.total/Number(o.defaultRp));
				
				//返回setHeader
				if(o.setHeader && typeof o.setHeader =='function'){
					o.setHeader($('.refresh-head',o.foot),o);
				};
				
				//注册第一页事件
				$('.first',o.foot).off('click').on('click',function(){

					o.page=1;
					that.splitData(o);
				});
				//注册前一页事件
				$('.prev',o.foot).off('click').on('click',function(){
					
					(o.page-=1) < 1 && (o.page = 1);
					that.splitData(o);
				});
				//注册后一页事件
				$('.next',o.foot).off('click').on('click',function(){
					
					(o.page+=1) > pages && (o.page =pages);
					that.splitData(o);
				});
				//注册最后一页事件
				$('.last',o.foot).off('click').on('click',function(){
					
					o.page = Number(pages);
					that.splitData(o);
				});
				//注册刷新事件
				$('.refresh',o.foot).off('click').on('click',function(){
					that.splitData(o);
				});
				//注册单页数量修改事件
				$('.mean',o.foot).off("change").on('change',function(){
					
					o.defaultRp = this.value;
					o.page=1;
					that.splitData(o);
				});
				//注册输入页码事件
				$('.page',o.foot).off("change").on('change',function(){
					
					o.page = this.value;
					
					Number(this.value) > Number($('.pages',o.foot).text()) && (o.page = Number($('.pages',o.foot).text()))
					
					that.splitData(o);
				});
				
				if(o.colResizable){
				//注册拖动事件
					$('div',o.drag).off("mousedown").on('mousedown',function(e){
						
						that.oldClientX=e.clientX;
						
						
						that.oldLeft=this.offsetLeft;
						
						this.className='drag';
						
						that.div.css('cursor','col-resize');
						
						that.dragTag= this;
						
						that.dragTagIndex=$(that.dragTag).index();
						
						that.dragTagth=$('col',o.theadColgroup).eq(that.dragTagIndex);
						
						that.dragTagtd=$('col',o.tbodyColgroup).eq(that.dragTagIndex);
						
						that.dragTagwidth=Number(that.dragTagth.attr('width'));
						
						$('body').noSelect();
						
						
					})
					$(document).off('mouseup').on('mouseup',function(e){
						if( that.oldClientX ){
							that.dragTag.className='';
							
							var x=e.clientX-that.oldClientX;
							
							that.oldClientX = undefined;
							
							x= that.dragTagwidth + x >= o.colMinWidth ? x: o.colMinWidth-that.dragTagwidth;
							
							
							that.allWidth=that.allWidth+x;
							
							
							
							$('table',o.head).width(that.allWidth);
							
							that.div.css('cursor','');
							
							that.dragTagth.attr('width',that.dragTagwidth+x);
							
							$('table',o.body).width(that.allWidth);
							
							
							that.dragTagtd.attr('width',that.dragTagwidth+x);
							
							var dragTagIndex = that.dragTagIndex-1;
							
							if(!o.multiSelect){
								dragTagIndex = that.dragTagIndex;
							}
							//改变对象的columns的width
							if(o.columns[dragTagIndex]){
								o.columns[dragTagIndex].width=that.dragTagwidth+x;
								for (var i=0; i<o.allColumns.length;i++){
									if(o.columns[dragTagIndex].field == o.allColumns[i].field){
										o.allColumns[i].width=o.columns[dragTagIndex].width;
									}
								}
							}else{
								o.action && (o.action.width=that.dragTagwidth+x);
							}
							
							
							that.dragTag.style.left= x+that.oldLeft+'px';
							
							$(that.dragTag).siblings().each(function(index, element) {
								
								if(index >= that.dragTagIndex){
									
									$(element).css('left',element.offsetLeft+x)	
								}
							});
						
						}
						
						$('body').noSelect(false);
						
					}).off('mousemove').on('mousemove',function(e){
							
						
							if( that.oldClientX ){
								
								var x=e.clientX-that.oldClientX;
								
								if ( that.dragTagwidth + x >= o.colMinWidth){
								
									that.dragTag.style.left= x+that.oldLeft+'px';
								
								}
								$('body').noSelect();
							}
					});
				}
			},
			splitData:function(o,a){
				//调用清除储存选中行
				o.head.scrollLeft('0');
				
				$('input:checkbox',o.head).prop("checked",false);
				//拼接AJAX数据升序降序
				
				var params = $.extend(o.params||{},o.gridSearch||{},{ 'page': o.page, 'pageSize': o.defaultRp, 'sortField': o.sortField, 'sortType': o.sortType });	
				//console.log(params)
				o.body.html(this.bodyHtml(o));
				
				o.body.children("div").height(o.nowHeight);
				
				o.body.children("div").css("line-height",o.nowHeight+"px");
				
				if(!o.pageCross){
					this.deleteCacheRows(o)
				}
				
				this.ajax(params,o);
			},
			deleteCacheRows:function(o){
				//分页判断

				o.selectedRows=[];
			}
		};	
		
		g.setHtml(a);
	}
	
	//渲染grid
	$.fn.datagrid = function(o){
		if(o.columns && o.allColumns){
			var data=[];
			for (var i=0;i<o.columns.length;i++){
				var arr={};
				for(var a=0;a<o.allColumns.length;a++){
					if (o.allColumns[a].field == o.columns[i].field){
						arr= $.extend({},o.allColumns[a],o.columns[i]);
						data.push(arr);
						break
					}
				}
			}
			o.columns=data;
		}else{
			if(o.allColumns && !o.columns){
				o.columns = $.extend([],o.allColumns);
			}else if(o.columns && !o.allColumns){
				o.allColumns = $.extend([],o.columns);	
			}	
		}
		return this.each(function(){
			var _this = this;
			$.drawgrid(_this, o);
		});
	};	
	
	
	//刷新表格头
	$.fn.gridRefreshHead = function(o){
		return this.each(function(){
			var _this = this;
			this.gridData.oldO=$.extend({},this.gridData.o);
			$.extend(this.gridData.o,o);
			
			if(o.columns){
				var data=[];
				for (var i=0;i<o.columns.length;i++){
					var arr={};
					for(var a=0;a<this.gridData.o.allColumns.length;a++){
						if (this.gridData.o.allColumns[a].field == o.columns[i].field){
							arr= $.extend({},this.gridData.o.allColumns[a],o.columns[i]);
							data.push(arr);
							break
						}
					}
				}
				this.gridData.o.columns=data;
			}else{
				this.gridData.o.columns = $.extend([],this.gridData.o.allColumns);	
			}
			this.gridData.o.drag.html("");
			$('table',this.gridData.o.head).html("").append(this.gridData.g.gethead(this.gridData.o));
			$("div",this.gridData.o.drag).height(this.gridData.o.nowHeight+50);
			$(this).gridRefresh();//调用表格刷新
		});
	};
	
	//查询内容
	$.fn.gridSearch = function(a){
		return this.each(function(){
			if(this.gridData && this.gridData.g){
				
				$(':checkbox',this.gridData.o.head).prop('checked',false);
				this.gridData.o.page = 1,
				this.gridData.o.gridSearch = a;
				this.gridData.o.params={//解决后一次默写参数不需要问题
					"page.":this.gridData.o.params["page.page"],
					"pageSize":this.gridData.o.params["pageSize"],
					"sortField":this.gridData.o.params["sortField"],
					"sortType":this.gridData.o.params["sortType"]
				}
				this.gridData.g.splitData(this.gridData.o,a);
			}
		});	
	}
	//刷新内容
	$.fn.gridRefresh = function(){
		return this.each(function(){
			if(this.gridData && this.gridData.g){
				$(':checkbox',this.gridData.o.head).prop('checked',false);
				this.gridData.g.splitData(this.gridData.o,this.gridData.o.gridSearch);
			}
		});
	};
	
	//返回选择项
	$.fn.getSelectedRows = function(){
		var selectedRows=[];
		
		this.each(function(){
			if(this.gridData && this.gridData.g && this.gridData.g.tdSelect){
				var tdSelect = this.gridData.g.tdSelect;
				$(tdSelect).each(function(){
					if($(this.checkbox).prop('checked')){
						selectedRows.push(this.row);
					}
				});
			}
		});
		return selectedRows;
	};
	
	$.fn.getParams =function(){
		var params;
		this.each(function(){
			if(this.gridData && this.gridData.g){
				params = this.gridData.o.params;
			}
		});
		return params;
	};
	
	var browser = $.browser;
	if(!browser){
		function userAgentMatch(ua){
			ua = ua.toLowerCase();
			var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf('compatible') < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];
			return {
				browser: match[1] || '',
				version: match[2] || '0'
			};
		};
		var matched = userAgentMatch(navigator.userAgent);
		browser = {};
		if(matched.browser){
			browser[matched.browser] = true;
			browser.version = matched.version;
		}
		// Chrome is Webkit, but Webkit is also Safari.
		if(browser.chrome){
			browser.webkit = true;
		}else if(browser.webkit){
			browser.safari = true;
		}
	}
	
	
	
	// 禁止文本选中
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
})(jQuery);