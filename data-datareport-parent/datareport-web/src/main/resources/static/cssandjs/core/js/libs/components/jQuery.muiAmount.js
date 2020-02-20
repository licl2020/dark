function muiAmount(o){
	this.muiAmountInput= (o.muiAmountInput[0].tagName =="INPUT"?o.muiAmountInput:$("input",o.muiAmountInput))
	this.muiAmountIncrease=o.muiAmountIncrease || this.muiAmountInput.siblings("i:eq(0)");
	this.muiAmountDecrease=o.muiAmountDecrease || this.muiAmountInput.siblings("i:eq(1)");	
	this.minNum = typeof o.minNum == 'number' ? o.minNum:0;
	this.maxNum = typeof o.maxNum == 'number' ? o.maxNum:Infinity;
	this.intervalTime=100;
	var that=this;
};
muiAmount.prototype={
	init:function(o){
		var that=this;
		typeof o =="number" && this.muiAmountInput.val(o)
		this.muiAmountInput.keydown(function(e) {//输入框
			
			switch(e.keyCode){
				case 38:
					that.SetInterval(1);
				break;
				case 40:
					that.SetInterval(-1);
				break;
			}
		});
		this.muiAmountInput.keyup(function(e) {//输入框
			switch(e.keyCode){
				case 38:
					that.getClearInterval();
				break;
				case 40:
					that.getClearInterval();
				break;
			}
		});
		this.muiAmountInput.on("input propertychange",function(e) {//输入框
			that.setValue(that.muiAmountInput.val().replace(/\D/g,""));
		});
		this.muiAmountIncrease.mousedown(function(e) {//输入框
			that.SetInterval(1);
		})
		this.muiAmountDecrease.mousedown(function(e) {//输入框
			that.SetInterval(-1);
		})
		$(document).mouseup(function(e) {//输入框
			that.getClearInterval();
		});
	},
	SetInterval:function(a){
		var that=this;
		if(!that.isInterval){//控制多次调用
			that.minusTime=0;
			that.setNvalue(a);
			that.isInterval=true;
			that.clearInterval=setInterval(function(){
				that.minusTime=that.minusTime+1;
				if(that.minusTime/10){
					that.minusTime=90;	
				}
				that.setNvalue(a);
			},that.intervalTime);
			
		}
	},
	setNvalue:function(a){
		var value=Number(this.muiAmountInput.val().replace(/\D/g,"")) || 0;
		this.setValue(value+a);	
	},
	getClearInterval:function(){
		this.isInterval=false;
		clearInterval(this.clearInterval);
	},
	setValue:function(value){
		var that=this.muiAmountInput;
		if(this.maxNum < value){
			that.val(this.maxNum);
		}else if(this.minNum > value){
			this.getClearInterval();
			that.val(this.minNum);
		}else{
			that.val(value);
		}
	},
	setReset:function(o){
		o= o||0;
		this.muiAmountInput.val(o);
	}	
}