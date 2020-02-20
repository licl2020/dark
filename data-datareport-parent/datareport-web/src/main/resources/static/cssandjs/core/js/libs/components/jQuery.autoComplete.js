/**
 * Author: dingwei 2013-03-16
 * Description: crm.automatch.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	var autoCompleteId = 0;
	
	$.fn.autoComplete = function(o){
		// 扩展参数
		o = $.extend({}, {
			width: 500,
			height: 200,
			zIndex: 100,
			type: 'POST',
			async: false,
			cache: false,
			url: null,
			beforeKeyUp:null,
			isEdit:false,
			params: {},
			dataType: 'json',
			millisec: 500,
			noDataTips: '无符合结果',
			failedTips: '请求失败，请刷新后重试',
			onRowClick: null
		}, o);
		
		// 公共函数库
		var g = {
			// 列表
			grid: null,
			// 高亮行的索引
			activeIndex: 0,
			// 判断列表是否存在且为显示状态
			gridIsExisted: function(){
				return this.grid != null && this.grid.is(':visible');
			},
			// 卸载列表
			unInstall: function(){
				this.grid != null && this.grid.off().remove();
				
				if(this.input){
					this.input.removeAttr('data-autocomplete');
				}
			},
			// 请求数据
			populate: function(){
				// 卸载前一次请求的列表
				this.unInstall();
				
				// 重置高亮行的索引
				this.activeIndex = 0;
				
				var keywords = g.input.val();
				if(g.input.attr('readonly')){
					//alert(input.attr("readOnly"));
					return;
				} 
				if(o.isEdit == true){
                	if(keywords.substr(0, 1) != '/'){
                    	return;
                	}
                	if(keywords.substr(0, 1) == '/'){
                    	l_keyword = keywords.substr(1);
                    	if(l_keyword.length == 0){
                        	l_keyword = '%';
                    	}
                    	keywords = l_keyword;
                	}
            	}
			
				var paramsEx = {};
				if(core.isFunction(o.params)){
					//$.extend(paramsEx, o.params());
				}else{
					$.extend(paramsEx, o.params);
				}
				var params = $.extend({}, { keywords: keywords }, paramsEx);
				
				$.ajaxSingle({
					type: o.type,
					url: o.url,
					data: params,
					async: o.async,
					cache: o.cache,
					dataType: o.dataType,
					success: function(result){
						// Array
						if(core.isArray(result)){
							g.fields = o.fields;
							g.rows = result;
						}
						// Json
						else if(core.isPlainObject(result)){
							g.fields = result.fields;
							g.rows = result.rows;
						}
						
						// 创建列表
						g.spliceOptions();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						
					}
				});
			},
			// 创建列表
			spliceOptions: function(){
				var tw = 0;
				
				// 创建列头
				var ths = [];
				for(var i = 0, colsLen = this.fields.length; i < colsLen; i++){
					var column = this.fields[i];
					// 隐藏
					var display = column.hide ? 'display:none;' : '';
					// 宽度
					var width = parseInt(column.width, 10);
					// 创建列头
					ths.push('<th style="' + display + 'width:' + width + 'px;">' + column.display + '</th>');
					// 计算表格总宽度
					if(!column.hide){
						tw += width + 1; // 1为单元格右边框宽度
					}
				}
				
				// 创建行
				var trs = [];
				for(var j = 0, rowsLen = this.rows.length; j < rowsLen; j++){
					var row = this.rows[j], cls = '';
					
					// 设置指定的行高亮
					if(j == this.activeIndex){
						cls = 'class="on"';
					}
					
					trs.push('<tr ' + cls + '>');
					for(var k = 0, colsLen = this.fields.length; k < colsLen; k++){
						var column = this.fields[k];
						// 隐藏
						var display = column.hide ? 'display:none;' : '';
						// 宽度
						var width = parseInt(column.width, 10);
						// 创建列
						trs.push('<td style="' + display + 'width:' + width + 'px;">' + row[column.field] + '</td>');
					}
					trs.push('</tr>');
				}
				
				var list = [], id = 'autoComplete' + autoCompleteId++,  pos = this.getPosition();
				list.push('<div id="' + id + '" class="ui-autocomplete-options" style="top:' + pos.top + 'px; left:' + pos.left + 'px; z-index:' + o.zIndex + '; width:' + o.width + 'px;">');
				list.push('<div class="ui-autocomplete-options-head">');
				list.push('<div class="ui-autocomplete-options-head-inner">');
				list.push('<table style="width:' + tw + 'px;">');
				list.push('<thead>');
				list.push('<tr>');
				list.push(ths.join(''));
				list.push('</tr>');
				list.push('</thead>');
				list.push('</table>');
				list.push('</div>');
				list.push('</div>');
				list.push('<div class="ui-autocomplete-options-body">');
				list.push('<div class="ui-autocomplete-options-body-inner" style="height:' + o.height + 'px;">');
				list.push('<table style="width:' + tw + 'px;">');
				list.push('<tbody>');
				list.push(trs.join(''));
				list.push('</tbody>');
				list.push('</table>');
				list.push('</div>');
				list.push('</div>');
				list.push('</div>');
				
				// 插入到文档流中
				$('body').append(list.join(''));
				
				// 
				this.grid = $('#' + id);
				
				//
				if(this.input){
					this.input.attr('data-autocomplete', true);
				}
				
				// 注册事件
				this.addProp();
			},
			// 定位
			getPosition: function(){
				var pos = { top: 0, left: 0 };
				
				var os = this.inputWrapper.offset(), iptTop = os.top, iptLeft = os.left, iptHeight = this.inputWrapper.height() + 2, w = $(window).width(), h = $(window).height();
				
				// top
				if((iptTop + iptHeight + o.height + 29) < h){
					pos.top = iptTop + iptHeight - 1;
				}else{
					pos.top = h - o.height - 29;
				}
				
				// left
				if((iptLeft + o.width + 2) < w){
					pos.left = iptLeft;
				}else{
					pos.left = w - (o.width + 2);
				}
				
				return pos;
			},
			// 注册事件
			addProp: function(){
				var _this = this;
				
				_this.grid
				// 容器单击事件
				.off('click.autoComplete').on('click.autoComplete', this, function(e){
					// 阻止事件冒泡
					_this.focus();
					e.stopPropagation();
				})
				// 行单击事件
				.off('click.autoCompleteBody').on('click.autoCompleteBody', '.ui-autocomplete-options-body-inner tr', function(e){
					var i = $(this).index(), data = g.rows[i], flag = true;
					
					// beforeOnRowClick
					if(core.isFunction(o.beforeOnRowClick)){
						flag = o.beforeOnRowClick(data, g.input);
					}
					
					if(flag){
						// 
						$(this).addClass('on').siblings().removeClass('on');
						
						// onRowClick
						if(core.isFunction(o.onRowClick)){
							o.onRowClick(data, g.input);
						}
						
						// 卸载列表
						g.unInstall();
					}
					_this.blur().focus();
				});
				
				_this.grid.find('.ui-autocomplete-options-body-inner').on('scroll', function(){
					_this.grid.find('.ui-autocomplete-options-head').get(0).scrollLeft = this.scrollLeft;
				});
			}
		};
		
		return this.each(function(){
			// 查找目标文本框
			if($(this).is('div')){
				g.inputWrapper = $(this);
			    g.input = $(this).find(':text').eq(0);
			}else if($(this).is(':text')){
				g.inputWrapper = $(this).parent();
			    g.input = $(this);
			}
			
			// 定义时间戳
			var timer = null;
			
			g.inputWrapper.addClass('ui-autocomplete');
			
			// 注册文本框事件
			g.input
			// 
			.attr('autocomplete', 'off')
			// 获得光标事件
			.off('focus.autoComplete').on('focus.autoComplete', function(e){
				// 隐藏其它控件
				//crm.clearOtherControls();
				// 阻止事件冒泡
				//e.stopPropagation();
			})
			// 失去光标事件
			.off('blur.autoComplete').on('blur.autoComplete', function(e){
				var oldVal = $(this).attr('oldvalue'); 
				var isEdit = o.isEdit;
				if (isEdit){
					return;
				}
				if(oldVal){
					var newVal = $(this).val();
					if(oldVal != newVal){
						var ships = $(this).attr('ships');
						if(ships){
							var tr = $(this).parents('tr').eq(0);
							ships = ships.split(',');
							resetValues(tr, ships);
						}
					}
				}
			})
			// 单击事件
			.off('click.autoComplete').on('click.autoComplete', function(e){
				// 隐藏其它控件
				//crm.clearOtherControls();
				// 阻止事件冒泡
				//e.stopPropagation();
			})
			// 键盘按下事件
			.off('keydown.autoComplete').on('keydown.autoComplete', function(e){
				// 判断列表是否存在且为显示状态
				if(g.gridIsExisted()){
					var body = g.grid.find('.ui-autocomplete-options-body-inner'), code = e.keyCode;
					// 向上
					if(code == 38){
						g.activeIndex--;
						if(g.activeIndex == -1){
							g.activeIndex = g.rows.length - 1;
						}
						
						var prev = body.find('tr').eq(g.activeIndex);
						
						prev.addClass('on').siblings().removeClass('on');
						
						// 执行滚动
						var top = prev.position().top, height = prev.get(0).clientHeight;
						top = core.isIe ? (top + 1) : top;
						if(top < 0){
							body.get(0).scrollTop -= height;
						}else if(g.activeIndex == (g.rows.length - 1)){
							body.get(0).scrollTop = g.activeIndex * height;
						}
					}
					// 向下
					else if(code == 40){
						g.activeIndex++;
						if(g.activeIndex == g.rows.length){
							g.activeIndex = 0;
						}
						
						var next = body.find('tr').eq(g.activeIndex);
						
						next.addClass('on').siblings().removeClass('on');
						
						// 执行滚动
						var top = next.position().top, height = next.get(0).clientHeight;
						top = core.isIe ? (top + 1) : top;
						if(o.height - top < height){
							body.get(0).scrollTop += height;
						}else if(g.activeIndex == 0){
							body.get(0).scrollTop = 0;
						}
					}
					// 回车
					else if(code == 13){
						var selected = body.find('tr.on');
						if(selected.length != 0){
							selected.click();
						}else{
							// 卸载列表
							g.unInstall();
						}
					}
				}
			})
			// 按键弹起事件
			.off('keyup.autoComplete').on('keyup.autoComplete', function(e){
				var code = e.keyCode;  
				// 过滤回车、向左、向上、向右、向下按键
				if(code != 13 && code != 37 && code != 38 && code != 39 && code != 40){
					 if(core.isFunction(o.beforeKeyUp)){
						var flag = o.beforeKeyUp();
						if(!flag){
							return false;
						} 
					} 
					// 清除前一次时间戳
					clearTimeout(timer);
					// 延迟请求
					timer = setTimeout(function(){
						// 请求数据
						g.populate();
					}, o.millisec);
				}
			});
			
			// 点击页面其他区域
			$(document).off('click.autoComplete').on('click.autoComplete', function(e){
				// 隐藏其它控件
				ui.components.collapse();
			});
		});
	};
})(jQuery);
