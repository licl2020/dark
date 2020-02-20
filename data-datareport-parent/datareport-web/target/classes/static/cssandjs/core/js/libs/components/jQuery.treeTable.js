// JavaScript Document
	
if(typeof Core4j == 'undefined'){
	// 导入WdatePicker脚本
	window.core.includeJs('../../core/js/third/treeTable/js/Core4j.js');
	window.core.includeJs('../../core/js/third/treeTable/js/TableTree4j.js');
	(function($){
		function nowTreeTable(a){
			if(!this.id){
				var Id='treeGrid'+Math.floor(Math.random()*Math.pow(10,12));	
				this.id=Id
			}
			this.o=a;
			this.className="ui-datagrid-body-inner";
			$(this).height(this.o.height);
			$(this).css("line-height",this.o.height+'px');
				var that=this,
				g={
					setBoxHtml:function(o){
						that.width=[],that.allWidth=0;
						for(var i =0 ; i<o.columns.length;i++){
							that.width.push(o.columns[i].width);
							that.allWidth+=Number(o.columns[i].width.replace(/\D/g,""));
						}
						var div=document.createElement("div"),divO=document.createElement("div");
						div.className="ui-datagrid ui-treegrid";
						divO.className="ui-datagrid-body";
						$(that).wrap(div);
						$(that).wrap(divO);	
						var treeGrid=$(that).parent("div").parent("div");
						treeGrid.prepend(this.getHeadHtml(o));
						that.foot=$('<div class="ui-datagrid-foot"></div>');
						that.foot.append(this.getFootHtml(o))
						this.setFootEvent(o);
						treeGrid.append(that.foot);
						
						this.initiaData(o);
					},
					getHeadHtml:function(o){
						var width=that.width,i=0;
						var table=document.createElement("table"),thead=document.createElement("thead"),tr=document.createElement("tr"),div=document.createElement("div"),
						divO=document.createElement("div");
						for(i;i<width.length;i++){
							var span=document.createElement("div");
							var th=document.createElement("th");
							span.className="default-tt-node-content";
							th.width=width[i];
							span.innerHTML=o.columns[i].display;
							th.name=o.columns[i].dataIndex;
							th.appendChild(span);
							tr.appendChild(th);
						}
						thead.appendChild(tr);
						table.appendChild(thead);
						table.width=that.allWidth;
						divO.id=that.id+"-head-box";
						div.id=that.id+"-head";
						divO.className="ui-datagrid-head clearfix";
						div.className="ui-datagrid-head-inner";
						div.appendChild(table);
						divO.appendChild(div);
						that.hendDiv=divO;
						
						return $(divO);	
					},
					getFootHtml:function(o){
						var html=['<div class="ui-datagrid-foot-total">共<span>'+o.total+'</span>条</div>'];
							html.push(' <div class="ui-datagrid-foot-buttons">');
								html.push(' <div class="group">');
									html.push(' <div class="ui-datagrid-foot-button first" title="首页"></div>');
									html.push(' <div class="ui-datagrid-foot-button prev" title="上一页"></div>');
								html.push(' </div>');
								html.push('<div class="group">');
									html.push('<div class="current"><label class="label">第</label><input type="text" class="page" value="'+o.page+'"><label class="label">页</label></div>');
									html.push('<div class="total"> <label class="label">共</label><label class="pages">'+o.pages+'</label><label class="label">页</label></div>');
								html.push(' </div>');
								html.push('<div class="group">');
									html.push('<div class="ui-datagrid-foot-button next" title="下一页"></div><div class="ui-datagrid-foot-button last" title="尾页"></div>');
								html.push(' </div>');
									html.push('<div class="group">');
									html.push(' <div class="ui-datagrid-foot-button refresh" title="刷新"></div>');
								html.push(' </div>');
								
								html.push('<div class="group">');
									html.push('<select name="mean" class="mean">');
									for(var i = 0; i< o.rpOptions.length ; i++){
										
										html.push('<option  '+((o.rpOptions[i] == o.defaultRp)?"selected":"")+' value="'+o.rpOptions[i]+'">'+o.rpOptions[i]+'</option>');
										
									}
									html.push(' </select>');
								html.push(' </div>');
							html.push(' </div>');
						return html.join("");
					},
					setFootEvent:function(o){
						var foot=that.foot,tHAt=this,input=$(".ui-datagrid-foot-buttons .group:eq(1) input:text",foot);
						
						
						$(".ui-datagrid-foot-buttons .group:eq(0) .ui-datagrid-foot-button:eq(0)",foot).click(function(e) {
							o.page=1;
							 tHAt.initiaData(o,that.params);
						});
						
						$(".ui-datagrid-foot-buttons .group:eq(0) .ui-datagrid-foot-button:eq(1)",foot).click(function(e) {
							o.page=input.val()-1;
							 tHAt.initiaData(o,that.params);
						});
						
						input.change(function(e) {
							o.page=$(this).val()-0;
							 tHAt.initiaData(o,that.params);
						});
						
						$(".ui-datagrid-foot-buttons .group:eq(2) .ui-datagrid-foot-button:eq(0)",foot).click(function(e) {
							o.page=input.val()-0+1;
							 tHAt.initiaData(o,that.params);
						});
						
						$(".ui-datagrid-foot-buttons .group:eq(2) .ui-datagrid-foot-button:eq(1)",foot).click(function(e) {
							o.page= o.pages;
							 tHAt.initiaData(o,that.params);
						});
						
						$(".ui-datagrid-foot-buttons .group:eq(3) .ui-datagrid-foot-button",foot).click(function(e) {
							 o.page=input.val()-0;
							 tHAt.initiaData(o,that.params);
						});
						
						$(".ui-datagrid-foot-buttons .group:eq(4) select",foot).change(function(e) {
							o.defaultRp=$(this).val()-0;
							 tHAt.initiaData(o,that.params);
						});
						
					},
					changeFoot:function(o){
						$(".ui-datagrid-foot-buttons .group:eq(1) input:text",that.foot).val(o.page);
						$(".ui-datagrid-foot-buttons .group:eq(1) .total .pages",that.foot).html(o.pages);
						$(".ui-datagrid-foot-total span",that.foot).html(o.total);
					},
					setCorrectPage:function(o){
						if(o.page<1){
							o.page =1;	
						}
						if(o.page > o.pages){
							o.page =o.pages;	
						}
					},
					ajax:function(o,params){
						//console.log(params)
						var tHAt=this;
							$.ajax({
								type: o.type,
								url: o.url,
								data: params,
								className:o.className,
								dataType: o.dataType,
								success: function(data){
									o.onComplete && typeof o.onComplete == "function" && o.onComplete(data);//请求完成后数据返回
									if(data && data.rows && data.rows.length >0){
										$(that).html("").css("line-height","18px");
										o.data=data;
										o.total=data.total;
										o.page=data.page;
										o.pages=Math.ceil(data.total/o.defaultRp);
										
										o.preProcessData && typeof o.preProcessData == "function" && o.preProcessData(data);//数据预处理
										tHAt.changeFoot(o);
										
										tHAt.newtrzzTable(o);
									}else{
										$(that).html('<span class="nothing">'+o.noDataTips+'</span>');
										o.total=0;
										o.page=1;
										o.pages=1;
										tHAt.changeFoot(o);	
									}
									
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
					initiaData:function(o,params){
						$(that).html('<span class="lodeing">'+o.loadingTips+'</span>');
						var columns=[],dataObject={};
						for(var i=0; i<o.columns.length;i++){
							columns.push({dataIndex:o.columns[i].dataIndex});
							dataObject[o.columns[i].dataIndex]=o.columns[i].display;
							o.columns[i].renderFunction=o.renderFunction[o.columns[i].dataIndex] || null;
						}
						o.jsonFIFAHeaders=[{
							columns:columns,
							dataObject:dataObject,
							trAttributeNames:['class','style'],
							trAttributeValueObject:{class:'headerbg',style:''}
						}];
						if(o.url){
							this.setCorrectPage(o);
							params=$.extend({"page.pageNum":o.page,"page.pageSize":o.defaultRp},o.params,params);
							this.ajax(o,params);
						}else{
							//this.newtrzzTable(o);	
						}
					},
					copy:function(sObj){ //添加对象深拷贝 
						if(typeof sObj !== "object"){   
							return sObj;   
						}   
						var s = {};   
						if(sObj.constructor == Array){   
							s = [];   
						}   
						for(var i in sObj){   
							s[i] = this.copy(sObj[i]);   
						}   
						return s;   
					},
					newtrzzTable:function(o){
						this.fifaGirdTree=new Core4j.toolbox.TableTree4j({
							columns:o.columns,
							treeMode:'gird',
							renderTo:that.id,
							useLine:true,
							useIcon:true,
							id:'Table-'+that.id,
							useCookie:false,
							scriptRootPath:o.scriptRootPath,
							onExpandNodeEvents:[o.fifaExpandNodeClick],
							onRowObjectClick:o.onRowObjectClick,
							onBuildTreeAddNodeEvents:o.onBuildTreeAddNodeEvents,
							//headers:o.jsonFIFAHeaders, //定义标题
							selectMode:'single'
		
						});
						var data=this.copy(o.data.rows);
						this.fifaGirdTree.rootNodes=[];
						this.fifaGirdTree.build(data,true);	
						$('#Table-'+that.id).width(that.allWidth);
						$(that).scroll(function(e) {
							that.hendDiv.scrollTo($(this).scrollLeft(),0);
						});
					}
				};
			
			g.setBoxHtml(this.o);
			
			this.g=g;
		};
		$.fn.treeTable=function(o){
				var defaults = {
					dataType: null, // 数据集合
					url: false, // 请求地址
					params: null, // 请求参数
					type: 'POST', // 请求方式
					dataType: 'json',
					scriptRootPath:'../../core/js/third/treeTable/',//JS的默认路径
					columns:null,
					loadingTips: '数据加载中', // 加载数据时的文本提示
					noDataTips: '未搜索到匹配项',
					renderFunction:{},//设置某一列的数据处理
					data:null,	
					total: 0, // 共多少条数据
					page: 1, // 当前页码
					pages: 1, // 共多少页
					rpOptions: [25, 50, 100], // 每页显示多少条数据的条件选项
					defaultRp: 25, // 默认每页显示多少条数
					onComplete:null,//数据返回后处理
					preProcessData:null,//数据预处理
					fifaExpandNodeClick:function(node,tree){}//点击打开时处理的回调函数
			}
			var settings = $.extend(defaults, o || {});	
				return this.each(function(){
					
					this.innerHTML="";
					nowTreeTable.call(this,settings);
				});
		};	
		
		$.fn.treeTableRefresh=function(){//刷新
			return this.each(function(){
				
				this.innerHTML="";
				
				this.g.fifaGirdTree="";
				
				this.g.fifaGirdTreeData="";
				
				this.g.initiaData(this.o,this.params);
				
			})
		};
		$.fn.treeTableSearch=function(params){//查询
			return this.each(function(){
				
				this.params=params;
				
				this.innerHTML="";
				
				this.g.fifaGirdTree="";
				
				this.g.fifaGirdTreeData="";
				
				this.o.page = 1;
				
				this.g.initiaData(this.o,this.params);
				
			})
		};
		$.fn.sortByColumnIndex=function(o){
			return this.each(function(){
				for(var i=0 ;i<this.g.fifaGirdTree.columns.length;i++){
					this.g.fifaGirdTree.columns[i].dataIndex == o && this.g.fifaGirdTree.sortByColumnIndex(i);
				}
				
			})
		};
	})(jQuery);
}