<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网约房信息库</title>
<link type="text/css" rel="stylesheet"
	href="/cssandjs/core/theme/global.css" />
<link type="text/css" rel="stylesheet"
	href="/cssandjs/core/theme/default/ui.css" />
	<link type="text/css" rel="stylesheet"
	href="/cssandjs/core/css/page.css" />
	<script type="text/javascript"
	src="/cssandjs/js/operation/cuurrent_host.js"></script>

<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript"
	src="/cssandjs/js/My97DatePicker/4.8/WdatePicker.js"></script>
	</head>

<body>
<div class="ui-forms">
	<div class="ui-forms-bodr">
		<form id="form" action="${request.contextPath}/user/list" data-ajax="true" onsubmit="return cleandisabled()" data-ajax-mode="replace" method="post">
		</form>
	</div>
	<div class="ui-datagrid-header text-right">
	<@shiro.hasPermission name="admin:add">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnAdd"> <em class="ui-button-text">新增</em>
		</span>
   </@shiro.hasPermission>
	</div>
	<div class="ui-datagrid" style="width: auto">
		<div class="ui-datagrid-head clearfix">
			<div class="ui-datagrid-head-inner">
				<table style="width: 1593px;">
					<colgroup>
						<col width="90">
						<col width="90">
						<col width="250">
					</colgroup>
					<thead>
						<tr>
							<th><div title="用户名" class="ui-datagrid-cell asd">用户名</div></th>
							<th><div title="状态" class="ui-datagrid-cell asd">状态</div></th>
							<th><div class="ui-datagrid-cell">操作</div></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="ui-datagrid-body" id="grid">
			<div class="ui-datagrid-body-inner"
				style="text-align: center;">
				<table width="1593">
					<colgroup>
						<col width="90">
						<col width="90">
						<col width="250">
					</colgroup>
					<tbody>
					 <#list list as item>
						<tr id="1">
						<td title="${item.uName!""}"><div class="ui-datagrid-cell asd">${item.uName!""}</div></td>
					    <td title="<#if (item.state) ==1>启用 <#else >禁用</#if>"><div class="ui-datagrid-cell asd"><#if (item.state) ==1>启用 <#else >禁用</#if></div></td>
							<td style="text-align: left;">
							<div class="ui-datagrid-cell">
							<@shiro.hasPermission name="admin:del">
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn btnDel"  data-id="${item.id}" >删除</label>
							 </@shiro.hasPermission>
							<@shiro.hasPermission name="admin:update">
							<label class="ui-datagrid-link mr5  ui-button fml-edit-btn" data-id="${item.id}">修改</label>
							 </@shiro.hasPermission>
							<@shiro.hasPermission name="admin:resetpassword">
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn btnReset" data-id="${item.id}">重置密码</label>
							 </@shiro.hasPermission>
							<@shiro.hasPermission name="admin:updatestate">
							<#if (item.state) ==2>
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn updatestate" data-state="1"  data-id="${item.id}" >启用</label>
							<#else>
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn updatestate" data-state="2" data-id="${item.id}" >禁用</label>
							</#if>
							 </@shiro.hasPermission>
								</div></td>
						</tr>
							</#list>
						<#if  (list?size<=0) >
						<div class="fml-no-data ui-datagrid-body-inner" >
                            <span >暂无数据</span>
                           </div>
                        </#if>
					</tbody>
				</table>
			</div>
		</div>
		<div class="ui-datagrid-drag" style="top: 0px;"></div>
			<div class="ui-datagrid-foot" id="page-box" >
	           ${page.getPageStr()}
		</div>
	</div>
</div>

	<script type="text/javascript" src="/cssandjs/core/js/third/jQuery/jQuery-1.8.3.js"></script>
				<script src="/cssandjs/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="/cssandjs/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="/cssandjs/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/libs/core.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/libs/driver.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/libs/components/jQuery.form.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/libs/components/jQuery.datagrid.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/libs/components/jQuery.selecter.js"></script>
	<script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.dialog.js"></script>
	<script type="text/javascript" src="/cssandjs/core/js/third/mousewheel/jquery.pictureViewer.js"></script>
	<script type="text/javascript" src="/cssandjs/js/operation/js_commplete.js"></script>
		<script type="text/javascript" src="${basePath}/cssandjs/user/js/handle.js"></script>
		<script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.datepicker.js"></script>
		</body>

</html>

