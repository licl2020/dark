
/**************************************************
** 扩展日期对象的原型链，新增功能：格式化日期对象、添
** 加日、周、月份以及年份等
**************************************************/
// 格式化日期对象
Date.prototype.format = function(format){
	
	var o = {
		
		// 月份
		'M+': this.getMonth() + 1,
		
		// 日
		'd+': this.getDate(),
		
		// 小时
		'h+': this.getHours(),
		
		// 分
		'm+': this.getMinutes(),
		
		// 秒
		's+': this.getSeconds(),
		
		// 季度
		'q+': Math.floor((this.getMonth() + 3) / 3),
		
		// 毫秒
		'S': this.getMilliseconds()
		
	};
	
	if(/(y+)/.test(format)){
		
		format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
		
	}
	
	for(var key in o){
		
		if(new RegExp('(' + key + ')').test(format)){
			
			format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[key]) : (('00' + o[key]).substr(('' + o[key]).length)));
			
		}
		
	}
	
	return format;
	
};

// 添加日
Date.prototype.addDays = function(d){
	
	this.setDate(this.getDate() + d);
	
};

// 添加周
Date.prototype.addWeeks = function(w){
	
	this.addDays(w * 7);
	
};

// 添加月份
Date.prototype.addMonths = function(m){
	
	var d = this.getDate();
	
	this.setMonth(this.getMonth() + m);
	
	if(this.getDate() < d){
		
		this.setDate(0);
		
	}
	
};

// 添加年份
Date.prototype.addYears = function(y){
	
	var m = this.getMonth();
	
	this.setFullYear(this.getFullYear() + y);
	
	if(m < this.getMonth()){
		
		this.setDate(0);
		
	}
	
};


/**************************************************
** 获取当前日期和星期
**************************************************/
var getDateAndWeek = function(){
	
	// 获取当前日期对象
	var now = new Date();
	
	// 获取当前年份、月份、日、星期索引
	var year = now.getFullYear(), month = now.getMonth() + 1, date = now.getDate(), day = now.getDay();
	
	// 返回结果
	return year + '年' + month + '月' + date + '日    星期' + '日一二三四五六'.charAt(day);
	
};


/**************************************************
** 判断表单是否被修改
**************************************************/
var formIsChanged = function(form){
	
	var elem = null, isChanged = false;
	
    cycle: for(var i = 0, l = form.elements.length; i < l; i++){
    	
        elem = form[i];
        
        switch(elem.type){
        	
            case 'text':
            	
            case 'password':
            	
            case 'textarea':
            	
            case 'file':
            	
            case 'hidden':
            	
                if(elem.defaultValue != elem.value){
                	
                    isChanged = true;
                    
                    break cycle;
                    
                }
                
                break;
                
            case 'checkbox':
            	
            case 'radio':
            	
                if (elem.defaultChecked != elem.checked){
                	
                    isChanged = true;
                    
                    break cycle;
                    
                }
                
                break;
                
            case 'select-one':
            	
            case 'select-multiple':
            	
                for(var n = 0, oLength = elem.options.length; n < oLength; n++){
                	
                    if(elem.options[n].defaultSelected != elem.options[n].selected){
                    	
                        isChanged = true;
                        
                        break cycle;
                        
                    }
                    
                }
                
            	break;
            
        }
        
    }
    
    return isChanged;
	
};


/**************************************************
** 获取或过滤提交数据
**************************************************/
// 过滤提交的字段名称
var checkPostName = function(name, filter){
	
	if(name != null && name != ''){
		
		if(filter != null && filter != ''){
			
			var ns = [];
			
			if(isString(filter)){ // 字符串
				
				ns = filter.split(',');
				
			}else if(isArray(filter)){ // 数组
				
				ns = filter;
				
			}
			
			for(var i = 0, l = ns.length; i < l; i++){
				
				if(name.indexOf(ns[i]) > -1){
					
					return true;
					
				}
				
			}
			
			return false;
			
		}
		
		return true;
		
	}
	
	return false;
	
};

// 
var getPostJson = function(filter){
	
	var json = {};
	
	// 单行文本框
	jQuery(':text').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = jQuery.trim(this.value);
			
		}
		
	});
	
	// 隐藏域标签
	jQuery('input:hidden').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = jQuery.trim(this.value);
			
		}
		
	});
	
	// 密码文本框
	jQuery(':password').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = jQuery.trim(this.value);
			
		}
		
	});
	
	// 选中的单选按钮
	jQuery(':radio:checked').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = this.value;
			
		}
		
	});
	
	// 选中的复选按钮
	jQuery(':checkbox:checked').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = this.value;
			
		}
		
	});
	
	// 多行文本框
	jQuery('textarea').each(function(){
		
		if(checkPostName(this.name, filter)){
			
			json[this.name] = jQuery.trim(this.value);
			
		}
		
	});
	
	// 下拉单选框
	jQuery('select').each(function(){
		
   		if(checkPostName(this.name, filter)){
   			
   			json[this.name]=this.value;
   			
   		}
   		
 	});
 	
 	return json;
	
};

// 
var getTableJson = function(expr){
	
	var json = [];
	
	jQuery(expr).each(function(){
		
		var single = {};
		
		// 单行文本框
		jQuery(this).find(':text').each(function(){
			
   			single[this.name] = jQuery.trim(this.value);
   			
	 	});
	 	
		// 隐藏域标签
	 	jQuery(this).find('input:hidden').each(function(){
	 		
	   		single[this.name] = jQuery.trim(this.value);
	   		
	 	});
	 	
		// 密码文本框
	 	jQuery(this).find(':password').each(function(){
	 		
	   		single[this.name] = jQuery.trim(this.value);
	   		
	 	});
	 	
	 	// 单选按钮
	 	jQuery(this).find(':radio').each(function(){
	 		
	   		single[this.name] = this.value;
	   		
	 	});
	 	
	 	// 复选按钮
	 	jQuery(this).find(':checkbox').each(function(){
	 		
			single[this.name] = this.value;
			
	 	});
	 	
	 	// 多行文本框
	    jQuery(this).find('textarea').each(function(){
	    	
	   		single[this.name] = jQuery.trim(this.value);
	   		
	 	});
	 	
	 	// 下拉单选框
	    jQuery(this).find('select').each(function(){
	    	
	   		single[this.name] =this.value;
	   		
	 	});
		
		json.push(single);
		
	});
	
	return json;
	
};


/**************************************************
** 关于 url地址的相关处理操作
**************************************************/
// 获取 url服务器地址
var getUrlServer = function(url){
	
	if(url == null || url == ''){
		
		url = document.location.href;
		
	}
	
	var n = url.indexOf('http://'), m = url.indexOf('/', 7);
	
	if(n != -1 && m != -1){
		
		return url.substr(7, m - 7);
		
	}
	
};

// 获取 url参数集合
var getUrlParams = function(url){
	
	if(url == null || url == ''){
		
		url = document.location.href;
		
	}
	
    var s = url.indexOf('?'), str = '';
    
    if(s >- 1){
    	
    	str = url.substr(s + 1, url.length - s - 1);
    	
    }
    
    var paramParts = str.split('&'), params = {};
    
    for(var i = 0, l = paramParts.length; i < l; i++){
    	
        var part = paramParts[i];
        
        var ps = part.split('='), key = ps[0], value = ps[1];
        
        params[key] = value;
        
    }
    
    return params;
	
};

// 解码地址参数，中文会 encodeURI两次，所以对 url参数进行解码
var decodeUrlParams = function(params){
	
	if(!params){
		
		return null;
		
	}
	
	for(var key in params){
		
		if(params[key]){
			
			params[key] = decodeURI(params[key]);
			
		}
		
	}
	
	return params;
	
};

// 产生一个 Guid字符串
function newGuid(){
    var guid = '';
    
    for(var i = 1; i <= 32; i++){
		var n = Math.floor(Math.random() * 16.0).toString(16);
      	guid +=   n;
      	if((i == 8) || (i == 12) || (i == 16) || (i == 20)){
      		guid += '-';
      	}
    }
    
    return guid;    
}

function maxTextArea(sel){
	jQuery(sel).each(function(){
		var len = jQuery(this).attr("maxlength");
		jQuery(this).keyup(function () {
			this.value = this.value.slice(0, len)
		});
	});
};

//关闭当前页卡,刷新父页卡
function closeTab(ref) {
	if(ref){//是否刷新父页卡
	    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
	    if( win && win.refresh){
	        win.refresh();
	    }
    }
	tabs.close(tabs.getSelfId(window));
};


function pyWbInit(form) {
    var $inputcode = $(form).find('.pywb');
    
    $inputcode.each(function () { $(this).attr("oldvalue", $(this).val()) })
    $inputcode.bind("blur", function (event) {
    	var input_py = $(this).attr("py");
    	var input_wb = $(this).attr("wb");
        if ($(this).attr("oldvalue") != $(this).val()) {
            $.ajax({
                async: false,
                type: "POST",
                url: '../sysdata/getPyWb.html',
                data: {key: $(this).val()},
                dataType: "json",
                global: false,
                success: function (data) { 
                	if (data.msg)
            		{
                		var py = data.py;
	                	var wb = data.wb; 
	                    $("#"+input_py).val(py);
	                    $("#"+input_wb).val(wb); 
            		}else
        			{
            			alert("输入码获取失败！");
        			}
            
                }
            });

            $(this).attr("oldvalue", $(this).val())
        }
    });
};

function convertCurrency(currencyDigits) {
	// Constants:
	var MAXIMUM_NUMBER = 99999999999.99;
	// Predefine the radix characters and currency symbols for output:
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_SYMBOL = "人民币";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";
	// Variables:
	var integral; // Represent integral part of digit number. 
	var decimal; // Represent decimal part of digit number.
	var outputCharacters; // The output result.
	var parts;
	var digits, radices, bigRadices, decimals;
	var zeroCount;
	var i, p, d;
	var quotient, modulus;
	// Validate input string:
	currencyDigits = currencyDigits.toString();
	if (currencyDigits == "") {
	  //alert("Empty input!");
	  return "";
	}
	if (currencyDigits.match(/[^,.\d]/) != null) {
	  //alert("Invalid characters in the input string!");
	  return "";
	}
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
	  //alert("Illegal format of digit number!");
	  return "";
	}
	// Normalize the format of input digits:
	currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
	currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning. 
	// Assert the number is not greater than the maximum number.
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
	  //alert("Too large a number to convert!");
	  return "";
	}
	// http://www.knowsky.com/ Process the coversion from currency digits to characters:
	// Separate integral and decimal parts before processing coversion:
	parts = currencyDigits.split(".");
	if (parts.length > 1) {
	  integral = parts[0];
	  decimal = parts[1];
	  // Cut down redundant decimal digits that are after the second.
	  decimal = decimal.substr(0, 2);
	}
	else {
	  integral = parts[0];
	  decimal = "";
	}
	// Prepare the characters corresponding to the digits:
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT,CN_NINE);
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// Start processing:
	outputCharacters = "";
	// Process integral part if it is larger than 0:
	if (Number(integral) > 0) {
	  zeroCount = 0;
	  for (i = 0; i < integral.length; i++) {
	   p = integral.length - i - 1;
	   d = integral.substr(i, 1);
	   quotient = p / 4;
	   modulus = p % 4;
	   if (d == "0") {
	    zeroCount++;
	   }
	   else {
	    if (zeroCount > 0)
	    {
	     outputCharacters += digits[0];
	    }
	    zeroCount = 0;
	    outputCharacters += digits[Number(d)] + radices[modulus];
	   }
	   if (modulus == 0 && zeroCount < 4) { 
	    outputCharacters += bigRadices[quotient];
	   }
	  }
	  outputCharacters += CN_DOLLAR;
	}
	// Process decimal part if there is:
	if (decimal != "") {
	  for (i = 0; i < decimal.length; i++) {
	   d = decimal.substr(i, 1);
	   if (d != "0") {
	    outputCharacters += digits[Number(d)] + decimals[i];
	   }
	  }
	}
	// Confirm and return the final output string:
	if (outputCharacters == "") {
	  outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	if (decimal == "") {
	  outputCharacters += CN_INTEGER;
	}
	//outputCharacters = CN_SYMBOL + outputCharacters;
	outputCharacters = outputCharacters;
	return outputCharacters;
};

var openModalDialog = function(url, args, width, height, exterior){
	// 为文档路径添加随机参数，防止浏览器缓存
	url = this.modifyUrl(url, 'random', Math.random());
	// 扩展默认参数
	args = this.addModalDialogArguments(args);
	// 对话框大小、弹出位置及外观设置
	var left, top, sw = window.screen.width, sh = window.screen.height;
	width = (typeof width === 'number') ? width : 800;
	height = (typeof height === 'number') ? height : 600;
	// 在 ie6下为模态对话框增加 30px的高度 
	//if(isIe6){
	//	height += 30;
	//}
	left = (sw - width) / 2;
	top = (sh - height) / 2;
	var features = 'dialogWidth=' + width + 'px; dialogHeight=' + height + 'px; dialogLeft=' + left + 'px; dialogTop=' + top + 'px;' + (exterior ? exterior : 'toolbar=no; menubar=no; scrollbars=no; resizable=no; location=no; status=no;');
	return showModalDialog(url, args, features);
};

modifyUrl = function(url, key, value){
	var reg = new RegExp('(\\\?|&)' + key + '=([^&]+)(&|$)', 'i'), match = url.match(reg);
	if(value){
		if(match){
			return url.replace(reg, function($0, $1, $2){
				return ($0.replace($2, value));
			});
		}else{
			if(url.indexOf('?') == -1){
				return (url + '?' + key + '=' + value);
			}else{
				return (url + '&' + key + '=' + value);
			}
		}
	}else{
		if(match){
			return match[2];
		}else{
			return '';
		}
	}
};
addModalDialogArguments = function(args){
	var newArgs = { 'window': window };
	
	if(args != null){
		
		switch(typeof args){
			
			case 'object':
				
				for(var key in args){
					
					newArgs[key] = args[key];
					
				}
				
				break;
				
			case 'string':
				
				newArgs['args'] = args;
				
				break;
				
			default: break;
				
		}
		
	}
	
	return newArgs;
};


var disabledAllElement = function(){
	
	// 单行文本框
	jQuery(':text').each(function(){
		$(this).attr("disabled", "disabled");
	});
	
	// 密码文本框
	jQuery(':password').each(function(){
		$(this).attr("disabled", "disabled");
	});
	
	// 选中的单选按钮
	jQuery(':radio:checked').each(function(){
		$(this).attr("disabled", "disabled");
	});
	
	// 选中的复选按钮
	jQuery(':checkbox:checked').each(function(){
		$(this).attr("disabled", "disabled");
	});
	
	// 多行文本框
	jQuery('textarea').each(function(){
		$(this).attr("disabled", "disabled");
	});
	
	// 下拉单选框
	jQuery('select').each(function(){
		$(this).attr("disabled", "disabled");
 	});
};

var enableAllElement = function(){
	
	// 单行文本框
	jQuery(':text').each(function(){
		$(this).removeAttr("disabled");
	});
	
	// 密码文本框
	jQuery(':password').each(function(){
		$(this).removeAttr("disabled");
	});
	
	// 选中的单选按钮
	jQuery(':radio:checked').each(function(){
		$(this).removeAttr("disabled");
	});
	
	// 选中的复选按钮
	jQuery(':checkbox:checked').each(function(){
		$(this).removeAttr("disabled");
	});
	
	// 多行文本框
	jQuery('textarea').each(function(){
		$(this).removeAttr("disabled");
	});
	
	// 下拉单选框
	jQuery('select').each(function(){
		$(this).removeAttr("disabled");
 	});
};

//回车执行查询事件
var initEnterCallback = function(selector, callback){
	$(selector).keydown(function(e){
		var code = e.keyCode;
		if(code == 13 && core.isFunction(callback)){
			callback();
		}
	});
};