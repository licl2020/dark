<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>网约房数据分析系统</title>
<meta name="keywords" content="网约房数据分析系统">
<meta name="description" content="网约房数据分析系统">
	<script type="text/javascript"
		src="cssandjs/core/js/third/jQuery/jQuery-1.8.3.min.js"></script>
<link rel="stylesheet" href="cssandjs/js/layer/2.4/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="cssandjs/css/layout.css" />
<script type="text/javascript" src="cssandjs/js/operation/cuurrent_host.js"/>
<script src="cssandjs/js/jquery/1.9.1/jquery.js"></script>
<script type="text/javascript" src="cssandjs/js/operation/js_commplete.js"></script>
<script type="text/javascript" src="cssandjs/js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="cssandjs/js/operation/js_commplete.js"></script>
<script src="cssandjs/js/cryptojs3.2/rollups/aes.js"></script>
<script src="cssandjs/js/cryptojs3.2/components/mode-ecb-min.js"></script>
<script src="cssandjs/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
<script type="text/javascript" src="cssandjs/js/operation/verify.js"></script>

</head>
<body>
<style>
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.jump-container {
	width: 340px;
	padding:10px;	
	height: 200px;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -170px;
	margin-top: -100px;
}

.jump-content {
	height: 180px;
	background: #f7f7f7;
	color: #565656;
	text-align: center;
	vertical-align: middle;
	line-height: 180px;
}
</style>
<div class="jump-container">
	<div class="jump-content">
		<span
			class="col-xs-3  col-lg-4 col-md-4 control-label no-padding-right"
			for=""username"">${content}</span> <span><img
			src="cssandjs/images/loading/loading-s.gif"
			alt="loading" /></span>
	</div>
</div>
<script type="text/javascript">
	setTimeout(function() {
		 top.location.href =cuurrent_host+"/Login";
	}, 2000)
</script>
</body>
</html>