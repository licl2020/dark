// JavaScript Document
var  pullDown=function(o){
			this.height=o.height || "auto";
			this.extendCell=o.extendCell || "";
			this.data=o.data || "";
			this.Class=o.Class ||"ui-pullDown-options";
	};
	pullDown.prototype={
		addHtml:function(){
			var that=this,div=document.createElement("div"),ul=document.createElement("ul"),iE=document.createElement("i"),span=document.createElement("span");
			this.div=div;
			div.className=this.Class;
			ul.className="ui-pullDown-options-ul";
			
			for(var i=0; i<this.data.length;i++){
				var li=document.createElement("li");	
				 if(this.extendCell && typeof this.extendCell === 'function'){
						this.extendCell(li,this.data[i],div);
                 }else{
					    li.innerHTML=this.data[i]; 
				} 
				if(i==this.data.length-1){
					li.className="last"	;
				}
				if(i==0){
					li.className="first";
				}
				$(li).click(function(e){
					that.removerDiv();
					e.stopPropagation();
				})
				ul.appendChild(li);
			}
			span.className="icon-sanjiao"
			span.appendChild(iE);
			div.appendChild(span);
			div.appendChild(ul);
			
			$(div).height(this.height);
			
			
		},
		into:function(ele){
			var that=this;
			if(!!this.div){
				this.removerDiv();
			}
			if(ele.nodeName=='INPUT'){
				ele=ele.parentNode;	
			}
			ele.style.position="relative";
			
			this.addHtml();
			
			ele.appendChild(this.div);
			
			this.ele=ele;
			
			$("body").click(function(e) {
				 that.removerDiv();
			});
		},
		removerDiv:function(){
			var that=this;
			$(that.div).remove();
			that.div="";
		}
	};