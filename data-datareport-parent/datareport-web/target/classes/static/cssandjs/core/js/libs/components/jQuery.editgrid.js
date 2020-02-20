
// 声明editgrid构造函数
var Editgrid = function(o){
	this.defaultAddrow=o.defaultAddrow || false;//默认最后添加一行
	this.disabledBool=false;
	this.addRowCallback=o.addRowCallback || '';
	this.id = o.renderTo;
	this.headId = this.id + '-head';
	this.bodyId = this.id + '-body';
	this.disabledId = this.id + '-disabled';
	this.tableId = this.id + '-table';
	this.activeRowId = this.id + '-active';
	this.height=o.height || 200;
	if(o.cols){
		this.setCols(o.cols);
	}
};

Editgrid.prototype.setCols = function(cols){
	if(cols){
		var width = 0, colgroup = [], thead = [];
		
		colgroup.push('<colgroup>');
		colgroup.push('<col style="width:50px;" />');
		thead.push('<thead>');
		thead.push('<tr>');
		thead.push('<th>序号</th>');
		for(var i = 0, len = cols.length; i < len; i++){
			var col = cols[i];
			if(col){
				width += col.width;
				colgroup.push('<col style="width:' + col.width + 'px;" />');
				thead.push('<th>' + col.display + '</th>');
			}
		}
		colgroup.push('</colgroup>');
		thead.push('</tr>');
		thead.push('</thead>');
	}
	
	var html = [];
	html.push('<div id="' + this.headId + '" class="ui-editgrid-head clearfix">');
	html.push('<div class="ui-editgrid-head-inner">');
	html.push('<table style="width:' + width + 'px;">');
	html.push(colgroup.join(''));
	html.push(thead.join(''));
	html.push('</table>');
	html.push('</div>');
	html.push('</div>');
	html.push('<div class="ui-editgrid-body">');
	html.push('<div id="' + this.bodyId + '" class="ui-editgrid-body-inner" style="height:'+this.height+'px;">');
	html.push('<table id="' + this.tableId + '" style="width:' + width + 'px;">');
	html.push(colgroup.join(''));
	html.push('<tbody>');
	html.push('</tbody>');
	html.push('</table>');
	html.push('<div id="' + this.disabledId + '" class="ui-editgrid-disabled-inner'+(this.disabledBool? "": " none")+'" style="height:'+this.height+'px;width:' + (width+50)  + 'px;">');
	html.push('</div>');
	html.push('</div>');
	html.push('</div>');
	$('#' + this.id).addClass('ui-editgrid').html(html.join(''));
	
	this.cols = cols;

	this.cacheDom();
	this.addEventHandlers();
};

// 
Editgrid.prototype.cacheDom = function(){
	this.box = $('#' + this.id);
	this.head = $('#' + this.headId);
	this.body = $('#' + this.bodyId);
	this.table = $('#' + this.tableId);
};

Editgrid.prototype.getLocationR=function (elm) {
	if(elm.createTextRange) { // IE          
		var range = document.selection.createRange();  
		 range.setEndPoint('StartToStart', elm.createTextRange());                           
		if(range.text.length>=elm.value.length-1){
			return true;	
		}; 
	} else if(typeof elm.selectionStart == 'number') { // Firefox  
		if(elm.selectionStart>=elm.value.length){
			return true;	
		}; 
	} 
} 

Editgrid.prototype.getLocationL=function (elm) {
	if(elm.createTextRange) { // IE              
		var range = document.selection.createRange();
		range.setEndPoint('StartToStart', elm.createTextRange());                   
		if(range.text.length<=0){
			return true;	
		}; 
	} else if(typeof elm.selectionStart == 'number') { // Firefox 
		if(elm.selectionStart<=0){
			return true;	
		}; 
	} 
} 
//设置光标位置
Editgrid.prototype.setcursor=function(elm,length,keyDownCtrl,endCursor){
	if(elm && elm.createTextRange) { // IE         
		if(keyDownCtrl){
			txtRan = elm.createTextRange();
			txtRan.select();
		}else{   
			txtRan = elm.createTextRange();
			txtRan.move("character",endCursor);
			txtRan.select();	
		}
	} else if(elm && typeof  elm.selectionStart == 'number') { // Firefox 
		setTimeout(function(){
			if(keyDownCtrl){
				elm.setSelectionRange( 0, length);
			}else{
				elm.setSelectionRange( endCursor, endCursor);
			}
		},5);
	} 	
	
};
// 注册editgrid事件
Editgrid.prototype.addEventHandlers = function(){
	var grid = this,keyDownCtrl=false;
	grid.body.off()
	// body区滚动条滚动事件
	.on('scroll', function(){
		grid.head.get(0).scrollLeft = this.scrollLeft;
	})
	// 行获得光标事件
	.on('focus', 'tr', function(){
		var row = $('#' + grid.activeRowId);
		if(row.length > 0){
			row.removeClass('active').removeAttr('id');
		}
		
		$(this).attr('id', grid.activeRowId).addClass('active');
	})
	// 键盘按下事件
	.on('keydown', 'input', function(e){
		var row = $('#' + grid.activeRowId), inputs = row.find(':text'), enableInputs = inputs.filter(':not([readonly])'), i = inputs.index(this), j = enableInputs.index(this), code = e.keyCode;
		// 确认
		if(code == 13){
			if(grid.cols && grid.cols[i] && grid.cols[i].addrow){
				// 创建行标签
				var row = grid.createRow();
				// 将行元素添加到grid中
				grid.addRow(row, function(g, tr){});
			}
			else{
				enableInputs.eq(j + 1).trigger('focus');
			}
		}
		// 左
		else if(code == 37 && !(j-1<0)&& (keyDownCtrl || grid.getLocationL(this)) ){
			enableInputs.eq(j - 1).trigger('focus');
			//重置光标位置
			var elm=enableInputs.eq(j - 1),length=elm.val()?elm.val().length:0;
			elm.trigger('focus');
			elm=elm[0];
			grid.setcursor(elm,length,keyDownCtrl,length);
			e.preventDefault();
		}
		// 上
		else if(code == 38){
			var elm=row.prev().find(':text:not([readonly])').eq(j),length=elm.val()?elm.val().length:0;
			elm.trigger('focus');
			elm=elm[0];
			grid.setcursor(elm,length,keyDownCtrl,length);
			e.preventDefault();
		}
		// 右
		else if(code == 39 && ( keyDownCtrl || grid.getLocationR(this))){
			enableInputs.eq(j + 1).trigger('focus');
			//重置光标位置
			var elm=enableInputs.eq(j + 1),length=elm.val()?elm.val().length:0;
			elm.trigger('focus');
			elm=elm[0];
			
			grid.setcursor(elm,length,keyDownCtrl,0);
			e.preventDefault();
		}
		// 下
		else if(code == 40){
			var elm=row.next().find(':text:not([readonly])').eq(j),length=elm.val()?elm.val().length:0;
			elm.trigger('focus');
			elm=elm[0];
			grid.setcursor(elm,length,keyDownCtrl,length);
			e.preventDefault();
		}
		else if(code == 17){
			keyDownCtrl=true;
		}
	})
	// 键盘放开按键事件
	.on('keyup', 'input', function(e){
		if(e.keyCode == 17){
			keyDownCtrl=false;
		}
	})
	;
};


//创建行标签并赋值
Editgrid.prototype.createRowVal = function(obj){
	var tr = [];
	tr.push('<tr>');
	for(var i = 0, len1 = this.cols.length; i < len1; i++){
		var col = this.cols[i], ipts = [];
		if(col.tags){
			var tags = col.tags.split(','), readonly = col.readonly ? ' readonly="readonly"' : '', cls = col.readonly ? ' class="readonly"' : ''
			for(var j = 0, len2 = tags.length; j < len2; j++){
				var key = tags[j];
				var val=eval("obj."+key)==undefined?"":eval("obj."+key);
				if(j == 0){
					//ipts.push('<input type="text" id="' + key + '" name="' + key + '" value="'+eval("obj."+key)?eval("obj."+key):""+'"' + readonly + cls + ' style="width:' + (col.width - 3) + 'px;" />');
					ipts.push('<input type="text" name="' + key + '" value="'+val+'"' + readonly + cls + ' style="width:' + (col.width - 3) + 'px;" />');
				}else{
					//ipts.push('<input type="hidden" id="' + key + '" name="' + key + '"  value='+eval("obj."+key)?eval("obj."+key):""+'/>');
					ipts.push('<input type="hidden" name="' + key + '" value="'+val+'" />');
				}
			}
		}
		tr.push('<td>' + ipts.join('') + '</td>');
	}
	tr.push('</tr>');
	
	return tr.join('');
};

// 创建行标签
Editgrid.prototype.createRow = function(factory){
	if(factory){
		return factory(this);
	}
	
	var tr = [];
	tr.push('<tr>');
	for(var i = 0, len1 = this.cols.length; i < len1; i++){
		var col = this.cols[i], ipts = [];
		if(col.tags){
			var tags = col.tags.split(','), 
			onBlur = col.onBlur ? ' onblur="'+ col.onBlur +'(this)"' : '',
			readonly = col.readonly ? ' readonly="readonly"' : '', 
			maxlength = col.maxlength ? ' maxlength="'+col.maxlength+'"' : '', 
			cls = col.readonly ? ' class="readonly"' : '',
			auto='',icon='';
			if(col.automatch){
				auto = '<div class="ui-text-auto ui-autocomplete w200">';
				icon = '<i class="ui-text-icon"></i></div>';
			}
			
			for(var j = 0, len2 = tags.length; j < len2; j++){
				var key = tags[j];
				if(j == 0){
					ipts.push(auto + '<input type="text" name="' + key + '"' + readonly + maxlength + onBlur + cls + ' style="width:' + (col.width - 3) + 'px;" />' + icon);
				}else{
					ipts.push('<input type="hidden" name="' + key + '" />');
				}
			}
		}
		tr.push('<td>' + ipts.join('') + '</td>');
	}
	tr.push('</tr>');
	
	return tr.join('');
};

//获得选中行
Editgrid.prototype.getSelectRow = function(){
	var row = $('#' + this.activeRowId);
	return row;
};
// 新增行
Editgrid.prototype.addRow = function(html, callback){
	if(this.disabledBool) return false;
	var tr = $(html),grid=this;

	tr.prepend('<td class="no">0</td>');

	this.table.find('tbody').append(tr);
	this.setRowIndex();
	this.scrollToBottom();
	this.focusTextbox();

	// 添加行完成回调函数
	if(this.addRowCallback && typeof this.addRowCallback =='function'){
		this.addRowCallback(this, tr);
	}
	if(callback && typeof callback =='function'){
		callback(this, tr);
	}
	// 键盘按下事件
	$('tr input',this.body).on('input propertychange', function(){
		if(grid.defaultAddrow && $(this).parents('tr').index()+1 == $('tr',grid.body).length){
			grid.addRowNofocu(grid.createRow());
		}
	})
};
// 删除行
Editgrid.prototype.deleteRow = function(){
	if(this.disabledBool) return false;
	var row = $('#' + this.activeRowId),nextRow=row.prev(),grid=this;
	if(nextRow.length<=-0){
		nextRow=row.siblings().eq(-1);	
	}
	if(row.length > 0){
		row.remove();
		this.setRowIndex();
	}
	$(':text:enabled:first',nextRow).trigger('focus');
	// 键盘按下事件
	$('tr input',this.body).on('input propertychange', function(){
		if(grid.defaultAddrow  && $(this).parents('tr').index()+1 == $('tr',grid.body).length){
			grid.addRowNofocu(grid.createRow());
		}
	})
};
// 默认新增行
Editgrid.prototype.addRowNofocu = function(html){
	if(this.disabledBool) return false;
	var tr = $(html),grid=this;
	
	tr.prepend('<td class="no">0</td>');

	this.table.find('tbody').append(tr);
	this.setRowIndex();
	this.scrollToBottom();

	// 添加行完成回调函数
	if(this.addRowCallback && typeof this.addRowCallback =='function'){
		this.addRowCallback(this, tr);
	}
	// 键盘按下事件
	$('tr input',this.body).on('input propertychange', function(){
		if(grid.defaultAddrow  && $(this).parents('tr').index()+1 == $('tr',grid.body).length){
			grid.addRowNofocu(grid.createRow());
		}
	})
};
//清空列表
Editgrid.prototype.deleteAllRows = function(){
	if(this.disabledBool) return false;
	var trs = this.table.find('tr');
	for(var i = 0, len = trs.length; i < len; i++){
		var tr = trs.eq(i);
		tr.remove();
	}
};

// 设置行序号
Editgrid.prototype.setRowIndex = function(){
	var trs = this.table.find('tr');
	for(var i = 0, len = trs.length; i < len; i++){
		var tr = trs.eq(i);
		tr.find('td:first').text(i + 1);
	}
};

// 滚动条滚动到底部
Editgrid.prototype.scrollToBottom = function(){
	if(this.body){
		this.body.get(0).scrollTop = this.body.get(0).scrollHeight;
	}
};

// 让新增行的首个可用文本框获得光标
Editgrid.prototype.focusTextbox = function(){
	this.table.find('tr:last :text:enabled:first').trigger('focus');
};

// 设置高度
Editgrid.prototype.setHeight = function(height){
	if(this.disabledBool) return false;
	this.body.css('height', height);
};

// 获取数据
Editgrid.prototype.getData = function(){
	var trs = this.table.find('tr'), data = [];
	for(var i = 0, len1 = trs.length; i < len1; i++){
		var tr = trs.eq(i), inputs = tr.find('input'), json = {}, empty = true;
		for(var j = 0, len2 = inputs.length; j < len2; j++){
			var input = inputs.eq(j), key = input.attr('name'), value = input.val();
			json[key] = value;
			if(value){
				empty = false;
			}
		}
		if(empty == false){
			data.push(json);
		}
	}
	return data;
};

// 扩展公共函数集
Editgrid.prototype.extendPrototype = function(extend){
	if(extend){
		return extend(this);
	}
	return this;
};

//设置grid是否禁用
Editgrid.prototype.setDisabled = function(bool){
	this.disabledBool=bool; 
	if(bool){
		$('#' + this.activeRowId).removeClass("active");
		$('#'+this.disabledId).width(this.body.get(0).scrollWidth);	
		$('#'+this.disabledId).height(this.body.get(0).scrollHeight);
		$('#'+this.disabledId).show();
	}else{
		$('#'+this.disabledId).hide();
	}
};































