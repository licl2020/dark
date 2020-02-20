/**
 * Author: HuBin 
 * time:2015-1-13
**/
(function($){
	
	$.fn.placeholder = function(opts){
		
		opts = $.extend({}, {
						
			color: '#c5c2c2'
			
		}, opts);
		
		return this.each(function(){
			if(!(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)&& navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[0].match(/\d+(?=.)/) <=9)) return ;
			
			var target = $(this), input;
			
			if(target.is('div')){
			    
			    input = target.find(':text:first');
			    
			}else if(target.is(':text')){
			    
			    input = target;
			    
			}

			if (!input.attr("placeholder") || !(target=input.parent(".ui-text"))) return;
			
			input.context.value=input.context.defaultValue;
			
			var val = $.trim(input.val()),div=document.createElement('div');
			
			div.className="ui-text-title";
			
			div.innerHTML=input.attr('placeholder');
			div.style.color=opts.color;
			div.style.display="none";
			
			if(!val){
				div.style.display="block";
			}
			
			$(div).click(function(){
				
				input.focus();
				
			});
			input.on("input propertychange",function(){
				if(!$.trim($(this).val())){
					div.style.display="block";	
				}else{
					div.style.display="none";	
				}
			});
			input.on("blur",function(){
				if(!$.trim($(this).val())){
					div.style.display="block";	
				}else{
					div.style.display="none";	
				}
			});
			
			input.after(div);
			
		});
		
	};
})(jQuery);
