<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网约房信息库</title>
<link type="text/css" rel="stylesheet"
	href="${basePath}/cssandjs/core/theme/global.css" />
<link type="text/css" rel="stylesheet"
	href="${basePath}/cssandjs/core/theme/default/ui.css" />
	<link type="text/css" rel="stylesheet"
	href="${basePath}/cssandjs/core/css/page.css" />
</head>

<script type="text/javascript"
	src="${basePath}/cssandjs/js/operation/cuurrent_host.js"></script>

<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript"
	src="${basePath}/cssandjs/js/My97DatePicker/4.8/WdatePicker.js"></script>
<style>
.btn-box {
	width: 100%;
	height: 44px;
	line-height: 44px;
	text-align: right;
	margin-bottom: 10px;
	margin-left: 0;
}

.no-padding-right {
	padding-right: 0;
}

.input-text {
	height: 31px;
	border: 1px solid #d0d0d0;
	padding: 5px 6px;
	width: 120px;
}

.col-xs-9 {
	float: right;
}

td>.btn {
	margin-right: 10px
}
</style>
<div class="ui-forms">
	<div class="ui-forms-bodr">
		<form id="form" action="${basePath}/role/list" data-ajax="true" data-ajax-mode="replace" method="post">
			<table class="ui-form">
				<tbody>
					<tr class="ui-form-row">
						<td class="ui-form-label" width="5%"><label>角色名</label></td>
						<td class="ui-form-control" width="5%">
							<div class="ui-text ui-text-lg">
								<input type="text" class="ui-text-text" name="name"
									autocomplete="off" maxlength="20" placeholder="角色名" value="${bean.name?if_exists}" />
							</div>
						</td>
						<td class="ui-form-label" width="5%">
							</td>
							<td class="ui-form-control" width="5%">
						</td>
					</tr>
				</tbody>
			</table>
			<div class="ui-forms-footer">
				<button class="ui-button ui-button-secondary ui-button-lg"
					id="btnSearch" type="submit"> 搜 索
				</button>

			</div>
		</form>
	</div>
	<div class="ui-datagrid-header text-right">
	   <@shiro.hasPermission name="role:add">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnAdd"> <em class="ui-button-text">新增</em>
		</span>
		</@shiro.hasPermission>
<!-- 		<@shiro.hasPermission name="role:reset"> -->
<!-- 		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg" -->
<!-- 			id="refResh"> <em class="ui-button-text">应用</em> -->
<!-- 		</span> -->
<!-- 		</@shiro.hasPermission> -->
	</div>
	<div class="ui-datagrid" style="width: auto">
		<div class="ui-datagrid-head clearfix">
			<div class="ui-datagrid-head-inner">
				<table style="width: auto">
					<colgroup>
						<col width="80">
						<col width="200">
						<col width="180">
					</colgroup>
					<thead>
						<tr>
							<th><div title="序号" class="ui-datagrid-cell asd">序号</div></th>
							<th><div title="角色名称" class="ui-datagrid-cell asd">角色名称</div></th>
							<th><div class="ui-datagrid-cell">操作</div></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="ui-datagrid-body" id="grid">
			<div class="ui-datagrid-body-inner"
				style="text-align: center;">
				<table style="width: auto">
					<colgroup>
						<col width="80">
						<col width="200">
						<col width="180">
					</colgroup>
					<tbody>
					 <#list list as item >
						<tr id="1">
							<td title=""><div class="ui-datagrid-cell asd">${item_index+1}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${item.name}</div></td>
							<td style="text-align: left;">
							<div class="ui-datagrid-cell">
							<@shiro.hasPermission name="role:update">
							<label class="ui-datagrid-link mr5  ui-button fml-edit-btn" data-id="${item.id}">修改</label>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="role:del">
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn btnDel" data-id="${item.id}" >删除</label>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="role:rolelist">
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn fenpeiauth" data-id="${item.id}" >分配权限</label>
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

<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/third/jQuery/jQuery-1.8.3.js"></script>
				<script src="${basePath}/cssandjs/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="${basePath}/cssandjs/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="${basePath}/cssandjs/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/core.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/driver.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/components/jQuery.form.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/components/jQuery.datagrid.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/components/jQuery.selecter.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/components/jQuery.dialog.js"></script>
		<script type="text/javascript" src="${basePath}/cssandjs/js/operation/js_commplete.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/core/js/libs/components/jQuery.treeSelecter.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/role/js/handle.js"></script>
