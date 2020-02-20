<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>单点登录管理平台</title>
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/theme/global.css" />
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/theme/default/ui.css" />
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/theme/animate.min.css" />
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/theme/default/index.css" />
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/theme/default/iconfont/iconfont.css" />
	<link type="text/css" rel="stylesheet"
		href="cssandjs/core/js/third/mousewheel/css/jquery.pictureViewer.css"></link>
	<link type="text/css" rel="stylesheet"
		href="cssandjs/home/css/index.css">
</head>

<body>
	<div id="head">
		<div alt="" id="logo">
			<a href="${request.contextPath}/index">
				<img class="logo-img" alt=""
				src="${request.contextPath}/cssandjs/home/images/logo1.png" /> 
			</a>
			
		</div>
		<div class="fml-head-drop">
			<ul id="nav">
				<li class="name fml-DropDown" id="fml-DropDown"><i
					class="iconfont icon-fangdong"></i> <em>${name!''}</em> <i
					class="iconfont icon-arrLeft-fill"></i></li>
			</ul>
			<div class="drop-menu" id="drop-menu">
				<ul>
						<li class="clearfix" data-state="4"><i class="iconfont icon-tuichu mr10"></i><span>退出</span></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="body">
	<div id="side">
<b id="btnSideToggle" class="toggle"></b>
<dl id="menu">
<!--  <dt id="_menuParent03" class="active"  > <i class="iconfont icon-xitong"></i> <span>重点人口上报</span></dt> -->
<!--  <dd class="active">  -->
<!--  <ul> -->
<!--       <li  id="_menuChild01" class="active" data-parent="0" data-tab='{"display":"上报列表","url":"${request.contextPath}/zdrk/list"}' data-child="0" > -->
<!--         <span  title="上报列表">上报列表</span> -->
<!--      </li> -->
<!--      <li  id="_menuChild01" class="active" data-parent="0" data-tab='{"display":"发送记录列表","url":"${request.contextPath}/zdrk/sendRecordList"}' data-child="0" > -->
<!--         <span  title="发送记录列表">发送记录列表</span> -->
<!--      </li> -->
<!--     </ul>  -->
<!--   </dd> -->
     <#list list as itme >
  <dt id="_menuParent03"  > <i class="iconfont icon-xitong"></i> <span>${itme.name?if_exists}</span></dt>
 <dd > 
 <ul>  
      <#list itme.itmeList as itme2 >
      <li  id="_menuChild01" class="active" data-parent="0" data-tab='{"display":"用户列表","url":"${request.contextPath}/${itme2.visitUrl?if_exists}"}' data-child="0" >
        <span  title="${itme2.name?if_exists}">${itme2.name?if_exists}</span>
     </li>
       </#list>
    </ul> 
  </dd>
  </#list>
 </dl>
 </div>
	
		<div id="main">
			<div class="tabs">
				<div class="tabs-breadcrumb">
					<div class="tabs-breadcrumb-content">
						<ul id="tabs-head" class="tabs-head">
							<li id="desk" class="lock">
								<h4 id="desk-title" title="网约房信息库">
									<i class="iconfont icon-dingwei"></i>当前位置：<span id="fmlTiltle">网约房信息库</span>
								</h4>
							</li>
						</ul>
					</div>
				</div>
				<div id="tabs-body" class="tabs-body animated fadeInRight">
					<!-- ifrmae 主体部分 -->
				</div>
			</div>
		</div>
	</div>
	<div id="foot">
		<p>
			@2018-广安科贸-数据上报V:<small>1.0</small>
		</p>
	</div>
	<script type="text/javascript"
		src="cssandjs/js/operation/cuurrent_host.js"></script>
		<script type="text/javascript" src="cssandjs/core/js/third/jQuery/jQuery-1.8.3.js"></script>
			<script src="cssandjs/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="cssandjs/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="cssandjs/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
	<script type="text/javascript"
		src="cssandjs/core/js/libs/core.js"></script>
	<script type="text/javascript"
		src="cssandjs/core/js/libs/components/jQuery.dialog.js"></script>
			<script type="text/javascript" src="cssandjs/js/operation/js_commplete.js"></script>
	<script type="text/javascript"
		src="cssandjs/core/js/third/mousewheel/jquery.mousewheel.js"></script>
	<script type="text/javascript"
		src="cssandjs/core/js/third/mousewheel/jquery.pictureViewer.js"></script>
	<script type="text/javascript" src="cssandjs/home/js/tabs.js"></script>
	<script type="text/javascript" src="cssandjs/home/js/index.js"></script>
</body>

</html>