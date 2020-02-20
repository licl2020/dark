 
if(jQuery){
	jQuery.form = {
		reset: function(selector, callback){
		    var form = $(selector).get(0);
		    
		    // reset
		    form.reset();
		    
		    try{
		        // reset selecter
		        $('select', form).change().selecter();
		    }catch(e){
		
		    }
			
			if(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)&& navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[0].match(/\d+(?=.)/) <=9){
				$('input[placeholder]').blur();
			};
			
		    // callback
		    if(callback && typeof callback === 'function'){
		        callback();
		    }
		}
	};
}
/*清空表单*/
var toResetForm = function(id, callback){
    jQuery && jQuery.form && jQuery.form.reset(id, callback);
};

/*返回表单Json*/
var getFormJson = function(form){
	
	var json = [];
	
	jQuery(form).each(function(){
		
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