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
		<form id="form" action="${basePath}/systemlog/list" data-ajax="true" data-ajax-mode="replace" method="post">
		<table class="ui-form">
					<tbody>
			<tr class="ui-form-row">
							<td class="ui-form-label" width="5%">
								<label>起止日期</label>
							</td>
							<td class="ui-form-control" colspan="7">
								<table class="ui-combo-controls">
									<tbody>
										<tr>
											<td width="180">
												<div class="ui-text ui-datepicker ui-text-lg">
													<input type="text" autocomplete="off" class="ui-text-text " name="start" value="${start?if_exists}" id="startDate" />
													<i class="iconfont ui-text-icon ">&#xe615;</i>
												</div>
											</td>
											<td width="20" align="center">
												<em>~</em>
											</td>
											<td width="180">
												<div class="ui-text ui-datepicker ui-text-lg">
													<input type="text"  autocomplete="off" class="ui-text-text " name="end"  value="${end?if_exists}" id="endDate" />
													<i class="iconfont ui-text-icon ">&#xe615;</i>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="ui-forms-footer">
					<button class="ui-button ui-button-secondary ui-button-lg"  type="submit" id="btnSearch">
						<span class="ui-button-text" >搜 索</span>
					</button>

				</div>
		</form>
	</div>
	<div class="ui-datagrid" style="width: auto">
		<div class="ui-datagrid-head clearfix">
			<div class="ui-datagrid-head-inner">
				<table style="width: 1593px;">
					<colgroup>
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
					</colgroup>
					<thead>
						<tr>
							<th><div title="序号" class="ui-datagrid-cell asd">序号</div></th>
							<th><div title="操作人" class="ui-datagrid-cell asd">操作人</div></th>
							<th><div title="操作时间" class="ui-datagrid-cell asd">操作时间</div></th>
							<th><div title="方法名称" class="ui-datagrid-cell asd">方法名称</div></th>
							<th><div title="参数信息" class="ui-datagrid-cell asd">参数信息</div></th>
							<th><div title="返回值" class="ui-datagrid-cell asd">返回值</div></th>
							<th><div title="描述" class="ui-datagrid-cell asd">描述</div></th>
							<th><div title="操作类型" class="ui-datagrid-cell asd">操作类型</div></th>
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
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
						<col width="150">
					</colgroup>
					<tbody>
					 <#list list as operationlog >
						<tr id="1">
							<td title=""><div class="ui-datagrid-cell asd">${operationlog_index+1}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.userName}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.createTime?datetime('yyyy-MM-dd hh:mm:ss')}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.method}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.args_String}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.returnValue_String}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.logDescribe}</div></td>
							<td title=""><div class="ui-datagrid-cell asd">${operationlog.operationType}</div></td>
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
				<script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.datepicker.js"></script>
<script type="text/javascript"
	src="${basePath}/cssandjs/logs/js/handle.js"></script>
