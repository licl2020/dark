//form提交前
function check() {
	 document.getElementById("button").style.display = "none";
	var masterName = $("#name").val();
	var password = $("#password").val();
	var check =true;
	if (!masterName) {
		alert("请输入 用户名");
		check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
	}
	if (!password) {
		alert("请输入密码");
		check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
	}
	if(password.length<6){
	    alert("密码至少大于等于6位");
	    check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
	}
	if(password.length>20){
		alert("密码长度不能大于20位!");
		check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
	}
	if(password.indexOf(" ")>=0){
		alert("密码中不能含有空格!");
		check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
	}
	var regx = /^[0-9a-zA-Z]+$/;
    if(!regx.test(password))
    {
        alert("密码只能由数字和字母组成");
        check=false;
		 document.getElementById("button").style.display = "block";
		return mySubmit(check);
    } 
	if(check){
		$.ajax( {  
            type : "POST",  
            url : cuurrent_host+"/LoginVerify.action",  
            data : {
				'name' : masterName,
				'password' : password
			},  
            success : function(data) {  
            
            	if(data.code == 200){
            		 var url =$("#url").val(); 
            		 if(url==""|| url=="null"){
            			 window.location.href = cuurrent_host+"/landingpoint/selectsystem.action";
            	    	}else{
            	    		var path = getCookie(url);
            	    		if(path==""|| path=="null"){
            	    			window.location.href = cuurrent_host+"/landingpoint/selectsystem.action";
            	    		}else{
            	    			clearCookie(url);
            	    			path = path.substring(path.indexOf("\"")+1,path.lastIndexOf("\""));
            	    			window.location.href =path;
            	    		}
            	    		
            	    	}
            		
            		}else {
            			document.getElementById("button").style.display = "block";
    					alert(data.message);
				}
                
            }  
        });
		return mySubmit(false);
	}
};

function mySubmit(flag){  
    return flag;  
}  

/*$(function(){
	$.ajax({
		async : false,
		url : '${rc.contextPath}/admin/IsLogin.action',
		data : {},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var obj=eval( "(" + date + ")" );
			if (obj.status =="error") {
				alert(obj.message);
			} else {
				alert(obj.message);
				 window.location.href = "/shop/index/v/{$storeId}/sales-promotion";
			}
		},
		error : function() {
			alert("异常！");
			return false;
		}
	});
});*/

function changeImg(){
    var img = document.getElementById("img"); 
    img.src = cuurrent_host+"/genCaptcha.action?date=" + new Date();;
}
