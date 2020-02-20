<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>数据上报</title>
	<link type="text/css" rel="stylesheet" href="cssandjs/core/theme/global.css" />
	<link type="text/css" rel="stylesheet" href="cssandjs/core/theme/default/iconfont/iconfont.css" />
	<link type="text/css" rel="stylesheet" href="cssandjs/core/theme/default/ui.css" />
	<link type="text/css" rel="stylesheet" href="cssandjs/logon/css/login.css">
</head>

<body style="padding:0">
	<div class="fml-login-box">
		<div class="fml-login">
			<div class="login-logo ">
				<img src="cssandjs/logon/images/logo.png" />
				<h4 class="mt20" style="color:#fff;font-size:28px;font-weight: normal">数据上报</h4>
			</div>
			<div class="fml-login-input">
				<form id="form" autocomplete="new-password">

					<h3>用户登录</h3>
					<div class="ui-text ui-text-xlg " style="overflow:hidden ;">
						<i class="ui-login-icon iconfont">&#xe632;</i>
						<input type="text" id="name"  maxlength="20"  autocomplete="off"    disableautocomplete   class="ui-text-text pl30" placeholder="请输入用户名" />
						
					</div>
					<div class="fml-error fml-red "></div>
					<div class="ui-text ui-text-xlg mt10" style="overflow:hidden">
						<i class="ui-login-icon iconfont">&#xe673;</i>
						<input type="text" id="password" autocomplete="new-password"  maxlength="20"oncopy="return false;"   onpaste="return false;" oncut="return false;"  onfocus="this.type='password'"  disableautocomplete   class="ui-text-text  pl30" placeholder="请输入密码" />
						
					</div>
					<div class="fml-error fml-red "></div>
					<div class="ui-checkboxs pt10 mb10">
						<label class="ui " style="border-left:1px solid #999;color:#999;padding-left:10px;cursor: pointer;" id="forget">
							<span onclick="ss()">忘记密码</span>
						</label>
					</div>
					<div class="text-center mt20">
						<div class="ui-button ui-button-secondary fml-login-btn ui-button-xlg" id="btnLogin" style="display:block;text-align: center">
							<em class="ui-button-text" style="float:none">登录</em>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
	<script type="text/javascript" src="cssandjs/core/js/third/jQuery/jQuery-1.8.3.min.js"></script>
		<script type="text/javascript" src="cssandjs/js/operation/cuurrent_host.js"></script>
	    <script type="text/javascript" src="cssandjs/core/js/libs/core.js"></script>
         <script type="text/javascript" src="cssandjs/core/js/libs/components/jQuery.dialog.js"></script>
	<script type="text/javascript" src="cssandjs/logon/js/login.js"></script>

    <script type="text/javascript" src="cssandjs/js/operation/js_commplete.js"></script>
    <script type="text/javascript" src="cssandjs/js/operation/cookie.js"></script>

     <script type="text/javascript">
       function ss(){
//     	   alert("忘记密码请联系管理员");
    	   $.showConfirm({
				icon: 'succeed',
				content: '忘记密码请联系管理员'
			});
       }
       
       $(function(){
    	 //判断是否在iframe中
   		if(self!=top){
   			parent.window.location.replace(window.location.href);
   		}
    	    $('.ui-text-text').on('blur',function(){
			let val=$(this).val();
			if(!val)return
			let reg=/^[^ ]+$/
			if(!reg.test(val)){
				alert('不能有空格')
			}else{
				$(this).parent('.ui-text').css('borderColor','#afbdd4')
				$(this).parent('.ui-text').next('.fml-error').text('')
			}
		})
		
       })
     </script>
    
</body>

</html>