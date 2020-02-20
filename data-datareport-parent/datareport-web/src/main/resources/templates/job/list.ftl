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
		<form id="form" action="${request.contextPath}/job/list" data-ajax="true"  data-ajax-mode="replace" method="post">
		<table class="ui-form">
				<tbody>
					<tr class="ui-form-row">
						<td class="ui-form-label" width="5%"><label>分组名</label></td>
						<td class="ui-form-control" width="5%">
							<div class="ui-text ui-text-lg">
								<input type="text" class="ui-text-text" name="groupName"
									autocomplete="off" maxlength="20" placeholder="分组名" value="${bean.groupName?if_exists}" />
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
	<@shiro.hasPermission name="job:add">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnAdd"> <em class="ui-button-text">新增</em>
		</span>
   </@shiro.hasPermission>
   <@shiro.hasPermission name="job:updateallstate">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnZtAll"> <em class="ui-button-text">暂停所有任务</em>
		</span>
			<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnYYAll"> <em class="ui-button-text">启动所有任务</em>
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
						<col width="90">
						<col width="90">
					    <col width="90">
						<col width="250">
					</colgroup>
					<thead>
						<tr>
						    <th><div title="任务名" class="ui-datagrid-cell asd">分组名</div></th>
							<th><div title="任务名" class="ui-datagrid-cell asd">任务名</div></th>
							<th><div title="CRON表达式" class="ui-datagrid-cell asd">CRON表达式</div></th>
							<th><div title="任务类" class="ui-datagrid-cell asd">任务类</div></th>
							<th><div title="任务类名" class="ui-datagrid-cell asd">状态</div></th>
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
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="250">
					</colgroup>
					<tbody>
					 <#list list as item>
						<tr id="1">
					    <td title="${item.groupName!""}"><div class="ui-datagrid-cell asd">${item.groupName!""}</div></td>
						<td title="${item.jobName!""}"><div class="ui-datagrid-cell asd">${item.jobName!""}</div></td>
					    <td title="${item.cronExp!""}"><div class="ui-datagrid-cell asd">${item.cronExp!""}</div></td>
					    <td title="${item.className!""}"><div class="ui-datagrid-cell asd">${item.className!""}</div></td>
					    <td title="${item.stateCode!""}"><div class="ui-datagrid-cell asd">${item.stateCode!""}</div></td>
							<td style="text-align: left;">
							<div class="ui-datagrid-cell">
							<@shiro.hasPermission name="job:del">
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn btnDel"  data-groupName="${item.groupName!""}" data-jobName="${item.jobName!""}"  >删除</label>
							 </@shiro.hasPermission>
							<@shiro.hasPermission name="job:update">
							<label class="ui-datagrid-link mr5  ui-button fml-edit-btn" data-groupName="${item.groupName!""}" data-jobName="${item.jobName!""}">修改</label>
							 </@shiro.hasPermission>
							<@shiro.hasPermission name="job:updatestate">
							<#if (item.state) ==2>
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn updatestate" data-state="1" data-groupName="${item.groupName!""}" data-jobName="${item.jobName!""}">启动</label>
							<#else>
							<label class="ui-datagrid-link mr5  ui-button fml-show-btn updatestate" data-state="2" data-groupName="${item.groupName!""}" data-jobName="${item.jobName!""}" >暂停</label>
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
		<script type="text/javascript" src="${basePath}/cssandjs/job/js/handle.js"></script>
		<script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.datepicker.js"></script>
		</body>

</html>

