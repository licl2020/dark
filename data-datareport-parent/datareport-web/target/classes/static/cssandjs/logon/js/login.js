var loadUi = {
	initialize: function () {
		this.btnLogin = $('#btnLogin');//单击登录
		this.password=$('#password');
		this.rember=$('#rember');
		this.forget=$('#forget');
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		this.btnLogin.on('click', $.proxy(this.btnLoginClick, this));
		$('#password').on('focus',function(){$('.fml-error').text()})
	},
	// 登录
	btnLoginClick:function(){
		 document.getElementById("btnLogin").style.display = "none";
			var masterName = $("#name").val();
		var password=this.password.val();//$.trim(),
		var rember=this.rember.attr('checked');
		
		if (!masterName) {
			 alertmsg("请输入 用户名")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
		}
		if (!password) {
			 alertmsg("请输入密码")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
		}
		if(password.length<6){
		    alertmsg("密码至少大于等于6位")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
		}
		if(password.length>20){
			 alertmsg("密码长度不能大于20位!")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
		}
		if(password.indexOf(" ")>=0){
			 alertmsg("密码中不能含有空格!")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
		}
		var regx = /^[0-9a-zA-Z]+$/;
	    if(!regx.test(password))
	    {
	        alertmsg("密码只能由数字和字母组成")
			 document.getElementById("btnLogin").style.display = "block";
			 return;
	    } 
	    
		var param={
			name:masterName,
			password:password,
			rember:rember?1:0
		};
		
		$.ajax( {  
            type : "POST",  
            url : cuurrent_host+"/LoginVerify",  
            data : {
				'uName' : masterName,
				'password' : password
			},  
            success : function(data) {  
            
            	if(data.code == 200){
            		 var url =$("#url").val(); 
            		 if(url==""||!url||url==null){
            			 var tz=cuurrent_host+"/index";
            			 window.location.href = tz;
            	    	}else{
            	    		var path = getCookie(url);
            	    		if(path==""|| path=="null"){
            	    			window.location.href = cuurrent_host+"/index";
            	    		}else{
            	    			clearCookie(url);
            	    			path = path.substring(path.indexOf("\"")+1,path.lastIndexOf("\""));
            	    			window.location.href =path;
            	    		}
            	    		
            	    	}
            		
            		}else {
            			$('#password').val('');
            			$('#name').val('');
            			alertmsg(data.message)
            			document.getElementById("btnLogin").style.display = "block";
            			
				}
                
            }  
        });
		console.log(param)

	},
	btnForget:function(){
		alert('你点击了忘记密码')
	}

};

function alertmsg(msg){
	$.showConfirm({
		icon: 'warning',
		content: msg
	})
}
$(function () {
	loadUi.initialize()
});