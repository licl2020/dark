(function(window, undefind){
 	var verify = function(selector){
		return new verify.fn.init(selector);
	};
	verify.fn = verify.prototype = {
		error_words : {
			email : "电子邮件的格式不正确,正确的如xxx@xx.com！",
			pass : "密码长度必须为6-20位，请使用字母加数字的组合!",
			nopass : "两次密码输入不一样，请重输!",
			mob : "手机最少必须11个数字，如133********!",
			tel : "请下确输入电话号码，如如0579-******!",
			telandmob : "请输入正确的号码******!",
			check : "没有选中请选择!",
			file : "未上传或不支持该文件格式,请检查您文件的扩展名",
			money : "请输入正确的金钱格式:1.00",
			isnull : "不能为空!",
			isnull2 : "不能为null!",
			alphanumeric:"只能输入字母和数字",
			chineseandenglish:"最短两个字符,最长不操过50个字符,且只能输入汉字和字母或者数字",
			address:"只能含有字母、中文、数字、下划线 但是不可以含有特殊字符,并且最短两个字符,最长不操过50个字符",
			card_id:"请输入有效的15位或者18位身份证号码",
			numandenglish:"只能输入字母和数字,并且最短位10位最长不超过100个字符",
			ip:"请输入正确的IP地址",
			chinese:"请输入中文、字母、或者数字",
			namecheck:"请输入中文、字母、或者数字,且只能输入25个字符",
			isspace :"不能带有空格",
		},
		jQuery : "",
		init : function(selector) {
			this.jQuery = $(selector);
			return this
		},
		submitform : function() {
			for (i = 0; i < arguments.length; i++) {
				if (arguments[i] == "" || arguments[i] == undefined) {
					continue;
				}
				
				obj = this.jQuery.find("[name='"+arguments[i]+"']").get(0) || null;
				if (obj == null) {
					continue;
				}
				obj_type = obj.tagName.toLowerCase();				
				eval("returnstr = this.verify_"+obj_type+"(\""+arguments[i]+"\")");

				if (!returnstr) {
					return returnstr;
				}				
			}
			return true;
		},
		 //验证input的数据
		verify_input : function(name) {
			obj = this.jQuery.find("[name='"+name+"']");
			val = $(obj).val();
			type_name = $(obj).attr("type").toLowerCase();
			id = $(obj).attr("id").toLowerCase();
			cn_name = $(obj).attr("cname");
			switch  (type_name) {
			    //验证input type=text的数据
				case "text":
					if (!this.is_null(val)&&!(cn_name =="身份证号")&&!(cn_name =="身份证")&&!(cn_name =="负责人身份证号码")&&!(cn_name =="身份证号码")) {
						alert(cn_name+":"+this.error_words.isnull);
						$(obj).focus();
						return false;
					}else if(!this.is_space(val)){
						alert(cn_name+":"+this.error_words.isspace);
						$(obj).focus();
						return false;
						
					}else if(!this.is_null2(val)){
						alert(cn_name+":"+this.error_words.isnull2);
						$(obj).focus();
						return false;
					} else{
						
						if(id.toLowerCase().indexOf("miss") > -1){
							 return true;
						 }
						if(id.toLowerCase().indexOf("alphanumeric") > -1){
							if (!this.is_alphanumeric(val)) {
								alert(cn_name+":"+this.error_words.alphanumeric);
								$(obj).focus();
								return false;
							} else
								return true;
						 }
						
						 if(id.toLowerCase().indexOf("chineseandenglish") > -1){
							if (!this.is_chineseandEnglish(val) || val.length < 2 || val.length > 50) {
								alert(cn_name+":"+this.error_words.chineseandenglish);
								$(obj).focus();
								return false;
							} else{
								return true;
							}
						 }
						 
						 if(id.toLowerCase().indexOf("address") > -1){
								if (!this.is_arrdess(val) || val.length < 2 || val.length > 50) {
									alert(cn_name+":"+this.error_words.address);
									$(obj).focus();
									return false;
								} else{
									return true;
								}
							 }
						 
						 if(id.toLowerCase().indexOf("cardid") > -1){ // 为空不验证,否则进行身份证校验
							 if(val == "" || val == null){
								 
								 return true;
								 
							 }else{
								 
								 if( !(val.length == 15 || val.length == 18) || !this.is_card_id(val)){
									 alert(cn_name+":"+this.error_words.card_id);
										$(obj).focus();
										return false;
								 }else{
									    return true;
								 }
								 
							 }
							 
						 }
						 
						 if(id.toLowerCase().indexOf("ip") > -1){
							 if (!this.is_ip(val)) {
									alert(cn_name+":"+this.error_words.ip);
									$(obj).focus();
									return false;
								} else{
									return true;
								}
							 					    
						 }
						 
						 if(id.toLowerCase().indexOf("telandmob") > -1){
							 if (!this.is_telandmob(val)) {
									alert(cn_name+":"+this.error_words.telandmob);
									$(obj).focus();
									return false;
								} else{
									return true;
								}
						 }
						 
						 if (id.toLowerCase().indexOf("email") > -1) {
							 if (!this.is_email(val)) {
									alert(cn_name+":"+this.error_words.email);
									$(obj).focus();
									return false;
								} else{
									return true;
								}
						 }
						 
						 if (id.toLowerCase().indexOf("phone") > -1) {
							 if (!this.is_mob(val)) {
									alert(cn_name+":"+this.error_words.mob);
									$(obj).focus();
									return false;
								} else{
									return true;
								}
						 }
						 
						 if(id.toLowerCase().indexOf("numandenglish") > -1){
							 if(!this.is_alphanumeric(val) ||  val.length > 100 || val.length < 10 ){
								 alert(cn_name+":"+this.error_words.numandenglish);
									$(obj).focus();
									return false;
							 }else{
								 return true;
							 }
						 }
						 
						 if(id.toLowerCase().indexOf("chinese") > -1){
							 if(!this.is_chineseandEnglish(val) ||  val.length > 100 || val.length < 1){
								 alert(cn_name+":"+this.error_words.chinese);
									$(obj).focus();
									return false;
							 }else{
								 return true;
							 }
						 }
						 
						 if(id.toLowerCase().indexOf("namecheck") > -1){
							 if(!this.is_chineseandEnglish(val) ||  val.length > 25 || val.length < 1){
								 alert(cn_name+":"+this.error_words.chinese);
									$(obj).focus();
									return false;
							 }else{
								 return true;
							 }
						 }
						 
					}
					
					break;
			//验证input type=password的数据	
				case "password":
					if (name.toLowerCase().indexOf("re") > -1) {
						if (!this.is_pass(val)) {
							alert("确认"+this.error_words.pass);
							$(obj).focus();
							return false;
						} else {
							tmp_obj = this.jQuery.find("[name='"+name.substr(2)+"']");
							if ($(tmp_obj).val() != $(obj).val()) {
								alert(this.error_words.nopass);
								$(obj).val("");
								$(tmp_obj).val("")
								return false;
							} else
								return true;
						}						
					} else {
						if (!this.is_pass(val)) {
							alert(this.error_words.pass);
							$(obj).focus();
							return false;
						} else
							return true;
					}
					break;
			   //验证input type=checkbox的数据	
				case "checkbox":
					if (this.get_checked(obj) == 0) {
						cn_name = $(obj).attr("cname");
						alert(cn_name+":"+this.error_words.check);
						return false;
					} else
						return true;
					break;
			  //验证input type=file的数据	
				case "file":
					if (!this.is_null(val)) {
						cn_name = $(obj).attr("cname");
						alert(cn_name+":"+this.error_words.file);
						return false;
					} else
						return true;
					break;
			}
		},
		//验证下拉框的数据	
		verify_select : function(name) {
			obj = this.jQuery.find("[name='"+name+"']");
			if ($(obj).val() == "") {
				alert(cn_name+":"+"请选择" + $(obj).find("option:eq(0)").text());
				return false;
			} else {
				return true;
			}
		},
		//验证文本域的数据	
		verify_textarea : function(name) {
			obj = this.jQuery.find("[name='"+name+"']");
			val = $(obj).val();
			if (!this.is_null(val)) {
				cn_name = $(obj).attr("cname");
				alert(cn_name+":"+this.error_words.isnull);
				$(obj).focus();
				return false;
			} else
				return true;
		},
		is_email : function(str){
			var patrn=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if (!patrn.exec(str)) return false;
			return true;
		},
		is_mob : function(str){
			var patrn=/^1[3|4|5|7|8]\d{9}$/;
			if (!patrn.exec(str) || str.length != 11) return false;
			return true;
		},
		is_tel : function(str){
			//debugger
			var patrn=/\d{3}-\d{8}|\d{4}-\d{7}/;
			if (!patrn.exec(str)) return false;
			return true;
		},
		is_telandmob : function(str){
			var f =true;
			if(!this.is_mob(str) && !this.is_tel(str)){
				f=false;
			}			
			return f;
		},
		is_null : function(str){
			str=this.trim(str);
			if (str=='') return false;
			return true;
		},
		is_null2 : function(str){
			str=this.trim(str);
			if (str.toLowerCase()=="null") return false;
			return true;
		},
		is_space: function(str){
			str=this.trim(str);
			if (str.indexOf(" ")>=0) return false;
			return true;
		},
		is_pass : function(str){
			str=this.trim(str);
			var patrn=/^([\w.]){6,20}$/;
			if (!patrn.exec(str)) return false;
			return true;
		},
		is_file : function(str){
			if(str.search(/\.jpg|\.jpeg|\.bmp|\.gif|\.emf|\.wmf|\.xbm|\.png$/i) == -1)
				return false;
			else
				return true;
		},
		is_money : function(str){
			if(str.search(/^([1-9][0-9]*)?[0-9]\.[0-9]{2}$/i) == -1)
				return false;
			else
				return true;
		},
		trim : function(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		},
		get_checked : function(obj){
			var x=0;			
			for (i=0; i<$(obj).length; i++) 
			{
				if($(obj).eq(i).attr("checked"))
				x++;
			}
			return x;
		},
		only_num : function(){
			this.jQuery.val(this.jQuery.val().replace(/\D/g,''));
		},
		only_float : function(){
			this.jQuery.val(this.jQuery.val().replace(/([^-\d\./]+)*/g,''));
		},
		only_money : function(){
			if (!this.is_money(this.jQuery.val())) {
				alert(this.error_words.money);
				this.jQuery.val("1.00");
			}
		},
		//只能输入英文和s数字
		is_alphanumeric: function(str){
			var patrn=/^[a-zA-Z0-9_]{1,100}$/;
			if (!patrn.exec(str)) return false;
			return true;
		},
		//只能输入汉字和字母
		is_chineseandEnglish:function(str){
			var patrn=/^[\u4E00-\u9FA5A-Za-z0-9]+$/;
			if (!patrn.test(str)) return false;
			return true;
		},
		//身份证验证
		is_card_id:function(str){
			if(!checkCard(str)){
				return false;
			}else{
				return true;
			}
		},
		//地址验证
		is_arrdess:function(str){
			var patrn = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
			if (!patrn.test(str)) return false;
			return true;
		},
		//ip地址验证
		is_ip:function(str){
			var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		    var reg = str.match(exp);
		    if(reg==null)
		    {
		    return false;
		    }else{
		    	return true;
		    }
		}
	};
	verify.fn.init.prototype = verify.fn;
	window.verify = verify;	
})( window );


var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 
	    21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏", 
	    33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南", 
	    42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆", 
	    51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃", 
	    63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
	   }; 

 checkCard = function(obj) 
 { 
  //var card = document.getElementById('card_no').value; 
  //是否为空 
  // if(card === '') 
  // { 
  //  return false; 
  //} 
  //校验长度，类型 
  if(isCardNo(obj) === false) 
  { 
   return false; 
  } 
  //检查省份 
  if(checkProvince(obj) === false) 
  { 
   return false; 
  } 
  //校验生日 
  if(checkBirthday(obj) === false) 
  { 
   return false; 
  } 
  //检验位的检测 
  if(checkParity(obj) === false) 
  { 
   return false; 
  } 
  return true; 
 }; 

 //检查号码是否符合规范，包括长度，类型 
 isCardNo = function(obj) 
 { 
  //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
  var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/; 
  if(reg.test(obj) === false) 
  { 
   return false; 
  } 
  return true; 
 }; 

//检查号码是否符合规范，包括长度，类型 
 isCardNo = function(obj) 
 { 
  //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
  var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/; 
  if(reg.test(obj) === false) 
  { 
   return false; 
  } 
  return true; 
 }; 
 //取身份证前两位,校验省份 
 checkProvince = function(obj) 
 { 
  var province = obj.substr(0,2); 
  if(vcity[province] == undefined) 
  { 
   return false; 
  } 
  return true; 
 }; 
 //检查生日是否正确 
 checkBirthday = function(obj) 
 { 
  var len = obj.length; 
  //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字 
  if(len == '15') 
  { 
   var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
   var arr_data = obj.match(re_fifteen); 
   var year = arr_data[2]; 
   var month = arr_data[3]; 
   var day = arr_data[4]; 
   var birthday = new Date('19'+year+'/'+month+'/'+day); 
   return verifyBirthday('19'+year,month,day,birthday); 
  } 
  //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X 
  if(len == '18') 
  { 
   var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/; 
   var arr_data = obj.match(re_eighteen); 
   var year = arr_data[2]; 
   var month = arr_data[3]; 
   var day = arr_data[4]; 
   var birthday = new Date(year+'/'+month+'/'+day); 
   return verifyBirthday(year,month,day,birthday); 
  } 
  return false; 
 }; 
 //校验日期 
 verifyBirthday = function(year,month,day,birthday) 
 { 
  var now = new Date(); 
  var now_year = now.getFullYear(); 
  //年月日是否合理 
  if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) 
  { 
   //判断年份的范围（3岁到100岁之间) 
   var time = now_year - year; 
   if(time >= 0 && time <= 130) 
   { 
    return true; 
   } 
   return false; 
  } 
  return false; 
 }; 
 //校验位的检测 
 checkParity = function(obj) 
 { 
  //15位转18位 
  obj = changeFivteenToEighteen(obj); 
  var len = obj.length; 
  if(len == '18') 
  { 
   var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
   var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
   var cardTemp = 0, i, valnum; 
   for(i = 0; i < 17; i ++) 
   { 
    cardTemp += obj.substr(i, 1) * arrInt[i]; 
   } 
   valnum = arrCh[cardTemp % 11]; 
   if (valnum == obj.substr(17, 1)) 
   { 
    return true; 
   } 
   return false; 
  } 
  return false; 
 }; 
 //15位转18位身份证号 
 changeFivteenToEighteen = function(obj) 
 { 
  if(obj.length == '15') 
  { 
   var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
   var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
   var cardTemp = 0, i;  
   obj = obj.substr(0, 6) + '19' + obj.substr(6, obj.length - 6); 
   for(i = 0; i < 17; i ++) 
   { 
    cardTemp += obj.substr(i, 1) * arrInt[i]; 
   } 
   obj += arrCh[cardTemp % 11]; 
   return obj; 
  } 
  return obj; 
 };