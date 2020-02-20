// JavaScript Document
$.fn.file= function(o){
	o = $.extend({}, {}, o);
	return this.each(function(){
		if(this.type == "file"){
			var that=this,div=document.createElement("div"),input=document.createElement("input"),span=document.createElement("span"),em=document.createElement("em");
			div.className="ui-text ui-file";
			input.readOnly=true;
			input.type="text";
			input.className="ui-text-text";
			input.placeholder="附件路径";
			span.className ="ui-button ui-button-info ui-button-file";
			em.className="ui-button-text";
			em.innerHTML="选择";
			span.appendChild(em);
			div.appendChild(input);
			div.appendChild(span);  
			$(this).after(div); 
			div.onclick=function(){
				$(that).trigger("click");
			}; 
			
			$(this).change(function(e) {
				input.value=this.value;
			});
		}
	});
};