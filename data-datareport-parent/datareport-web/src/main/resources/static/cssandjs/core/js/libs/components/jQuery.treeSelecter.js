if(!(jQuery && jQuery.fn && jQuery.fn.zTree)){
	var a =cuurrent_host+"/cssandjs/core/js/third/zTree/css/zTreeStyle/zTreeStyle.css";
	document.write('<link type="text/css" rel="stylesheet" href="'+a+'" />');
	var b =cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.core-3.2.js";
	document.write('<script type="text/javascript" src="'+b+'"><\/script>');
	var c =cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.excheck-3.2.min.js";
	document.write('<script type="text/javascript" src="'+c+'"><\/script>');
	var d =cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.exedit-3.2.min.js";
	document.write('<script type="text/javascript" src="'+d+'"><\/script>');
	
}

(function($){
	
	// 工厂
	var shared = {};
	
	// 创建html结构
	shared.getHtml = function(opts){
		
		var html = [];
		
		html.push('<div class="ui-treeselecter-options"' + (opts.loadType == 'window.load' ? ' style="display:none;"' : '') + '>');
		
		html.push('<div class="options">');
		
		html.push('<ul class="ztree" id="' + opts.treeId + '"></ul>');
		
		html.push('</div>');
		
		// 是否输出“包含子机构”复选框
		if(opts.showIncludeSubChk){
			
			html.push('<div class="buttons"><div><input type="checkbox" class="ui-check" id="' + opts.treeId + '-checkbox" /><label for="' + opts.treeId + '-checkbox">包含子机构</label></div></div>' );
			
		}
		// 是否输出“操作按钮”复选框
		if(opts.showAction){
			html.push('<div class="buttons">');
			html.push('<span class="ui-button ui-button-secondary fr" id="' + opts.treeId + '-close" ><em class="ui-button-text">'+(opts.showAction.close || "确定")+'</em></span>');
			html.push('<span class="ui-button ui-button-border-info fr mr5" id="' + opts.treeId + '-clean" ><em class="ui-button-text">'+(opts.showAction.clean || "清空")+'</em></span>');
			html.push('</div>');
		}
		html.push('</div>');
		
		return html.join('');
		
	};
	
	// 加载树结构
	shared.init = function(div, opts, config, nodes){
			
		if(nodes && nodes.constructor === Array && nodes.length > 0){
			
			// 加载树节点
			$.fn.zTree.init($('#' + opts.treeId), config, nodes);
			
			// 去除加载进度图标
			$('div.options', div).addClass('nobg');
			
		}else{
			
			// alert('Data source is error！');
			
		}
		if(opts.onComplete && typeof opts.onComplete == 'function'){
			
			opts.onComplete(opts.treeId);

		}
		
	};
	
	// 根据文本框内的值初始化选中节点
	shared.resetState = function(value, opts){
		
		if(value != ''){
			
			var zTree = $.fn.zTree.getZTreeObj(opts.treeId);
			
			if(opts.checkEnable){ // 复选
				
				zTree.checkAllNodes(false);
				
				var vals = value.split(','), len = vals.length;
				
				for(var i = 0; i < len; i++){
					
					var node = zTree.getNodeByParam('name', vals[i], null);
					
					zTree.checkNode(node, true, false, false);
					
				}
				
			}else{ // 单选
				
				var node = zTree.getNodeByParam('name', value, null);
				
				zTree.selectNode(node, false);
				
			}
			
		}
		
	};
	
	$.fn.treeSelecter = function(opts){
		var that=this;
		opts = $.extend({}, {
			
			treeId: null,
			
			readonly: true,
			
			scrollRange: 10,
			
			loadType: 'tag.click', // or window.load
			
			method: 'POST',
			
			url: null,
			
			cache: false,
			
			format: 'json',
			
			nodes: null,
			
			showIcon: true,
			
			checkEnable: false,
			
			checkReaction: null,
			
			showIncludeSubChk: false,
			
			onTextClick: null,
			
			onCleanClack:null,
			
			beforeOnClick: null,
			
			onClick: null,
			
			beforeOnCheck: null,
			
			onCheck: null,
			
			showAction:true,
			
			onComplete: null
			
		}, opts);
		
		// 树形插件配置
		var config = {
			
			view: {
				
				expandSpeed: '',
				
				selectedMulti: false,
				
				autoCancelSelected: false
				
			},
			
			data: {
				
				simpleData: {
					
					enable: true
					
				}
				
			},
			
			check: {
				
				enable: false,
				
				chkboxType: { 'Y': 'ps', 'N': 'ps' }
				
			},
			
			callback: {
				
				beforeClick: null,
				
				onClick: null,
				
				beforeCheck: null,
				
				onCheck: null
				
			}
			
		};
		
		var beforeOnClick = function(treeId, node, flag){
			
			var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
			
			zTree.checkNode(node, !node.checked, true, true);
				
			return false;
			
		};
		
		var beforeOnCheck = function(treeId, node){
			
			var zTree = $.fn.zTree.getZTreeObj(treeId), parent = node.getParentNode();
			
			while(parent != null){
				
				zTree.checkNode(parent, false, false, false);
				
				parent = parent.getParentNode();
				
			}
			
			var child =  zTree.getNodesByParam('checked', true, node);
			
			if(child && child.length > 0){
				
				for(var i=0; i<child.length; i++){
					
					zTree.checkNode(child[i], false, false, false);
					
				}
				
			}
			
		};
		
		return this.each(function(){
			
			var target = $(this), div, input;
			
			if(target.is('div')){
				
				div = target;
				
				input = target.find(':text:first');
				
			}else if(target.is(':text')){
				
				div = target.parent();
				
				input = target;
				
			}
			
			// 添加ui-treeselecter类
			div.addClass('ui-treeselecter');
			
			// 设置文本框只读属性
			if(opts.readonly){
				
				input.attr('readonly', 'readonly');
				
			}
			
			// 是否输出节点图标
			if(!opts.showIcon){
				
				config.view.showIcon = opts.showIcon;
				
			}
			
			// 是否启用复选模式
			if(opts.checkEnable){
				
				config.check.enable = opts.checkEnable;
				
				config.callback.beforeClick = beforeOnClick;
				
			}
			
			//  启用复选模式时勾选的联动配置
			if(opts.checkReaction){
				
				config.check.chkboxType = opts.checkReaction;
				
			}
			
			// 单击之前
			if(opts.beforeOnClick && typeof opts.beforeOnClick === 'function'){
				
				config.callback.beforeClick = opts.beforeOnClick;
				
			}
			
			// 单击时
			if(opts.onClick && typeof opts.onClick === 'function'){
				config.callback.onClick = opts.onClick;
				
			}
			
			// 勾选之前
			if(opts.beforeOnCheck && typeof opts.beforeOnCheck === 'function'){
				
				config.callback.beforeCheck = opts.beforeOnCheck;
				
			}
			
			// 勾选时
			if(opts.onCheck && typeof opts.onCheck === 'function'){
				
				config.callback.onCheck = opts.onCheck;
				
			}
			
			function load(){
				
				// 如果未指定组件的id，则提示后返回，不执行下一步操作
				if(!opts.treeId){
						
					alert('TreeId is undefined!');
					
					return false;
					
				}
				
				// 获取控件对象，通过其长度判断是否已加载
				var box = $('div.ui-treeselecter-options', div);
				
				that.get(0).treeselecterConfig={
					opts:opts,
					config:config
				};
				if(box.length == 0){ // 尚未加载
					
					// 创建前先隐藏或清除其他控件
					ui.components.collapse();
					
					// 创建html标签
					var html = shared.getHtml(opts);
					
					box = $(html);
					
					// 添加到文档流中
					div.append(
						
						box.click(function(e){
							
							e.stopPropagation();
							
						})
						
					).css('z-index', 100);
					
					if(opts.url){ //请求url地址获取节点数据
						
						var url = null;
						
						switch(typeof opts.url){
							
							case 'string':
								
								url = opts.url;
								
								break;
								
							case 'function':
								
								url = opts.url();
								
								break;
							
							default: break;
							
						}
						
						$.ajax({
							
							type: opts.method,
							
							url: url,
							
							cache: opts.cache,
							
							dataType: opts.format,
							
							success: function(nodes){
								console.log(nodes)
								var childNodes=nodes.data;
								for (var i = 0, l = childNodes.length; i < l; i++) {
				                	childNodes[i].id =  childNodes[i].orgid;
				                	childNodes[i].uporgid =  childNodes[i].id;
				                	childNodes[i].type =  childNodes[i].type;
				                    childNodes[i].name = childNodes[i].orgname.replace(/\.n/g, '.');
				                }
//				                return childNodes;
								shared.init(div, opts, config, childNodes);
								
							},
							error: function(){
								
								box.html('<div class=\'failed\'>请求失败，请刷新页面重试！</div>');
								
							}
							
						});
						
					}else if(opts.nodes){
						
						shared.init(div, opts, config, opts.nodes);
						
					}else{
						
						alert('Data source is not set!');
						
					}
					if(opts.showAction){
						
						$('#' + opts.treeId + '-close', box).click(function(){
							$('div.ui-treeselecter-options', div).hide();
						})
						$('#' + opts.treeId + '-clean', box).click(function(){
							$('input:text', div).val("");
							var treeObj = $.fn.zTree.getZTreeObj(opts.treeId);
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length>0) { 
								treeObj.cancelSelectedNode(nodes[0]);
							}
							if(opts.onCleanClack && typeof opts.onCleanClack =="function"){
								opts.onCleanClack($('input:text', div))
							}
						})
					}
					if(opts.showIncludeSubChk){
						
						$('#' + opts.treeId + '-checkbox', box).click(function(){
							
							var zTree = $.fn.zTree.getZTreeObj(opts.treeId);
							
							if(this.checked){
								
								var checked = zTree.getCheckedNodes(true);
								
								for(var i=0; i<checked.length; i++){
									
									zTree.checkNode(checked[i], false, false, true);
									
								}
								
								zTree.setting.callback.beforeCheck = beforeOnCheck;
								
							}else{
								
								zTree.setting.callback.beforeCheck = null;
								
							}
							
						});
						
					}
					
				}else{ // 已加载
					
					if(box.is(':visible')){
						
						div.css('z-index', '');
						
						box.css('display', 'none');
						
					}else{
						
						// 弹出前先隐藏或清除其他控件
						ui.components.collapse();
						
						div.css('z-index', 100);
						
						box.css('display', 'block');
						
					}
					
				}
				
			}
			
			// 页面加载完成时即加载树结构
			if(opts.loadType == 'window.load'){
				
				load();
				
			}
			
			// 组件点击事件
			div.click(function(e){
				
				if($(this).hasClass('ui-text-disabled')){
					return false;
				}
				load();
				
				if(opts.onTextClick != null && typeof opts.onTextClick == 'function'){
					
					opts.onTextClick(opts.treeId);
					
				}
				
				e.stopPropagation();
				
			});
			$(document).click(function(){
				
				// 点击页面其它区域隐藏或清除其他控件
				ui.components.collapse();
				
			});
			
		});
		
	};
	
})(jQuery);
