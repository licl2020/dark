<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>发送记录列表</title>
<link type="text/css" rel="stylesheet"
	href="/core/theme/global.css" />
<link type="text/css" rel="stylesheet"
	href="/core/theme/default/ui.css" />
	<link type="text/css" rel="stylesheet"
	href="/core/css/page.css" />
	<script type="text/javascript"
	src="/js/operation/cuurrent_host.js"></script>
</head>

<body>
	<div class="ui-forms">
		<div class="ui-forms-bodr">
			<form id="form" action="${request.contextPath}/zdrk/sendRecordList"  data-ajax="true" data-ajax-mode="replace"  onsubmit="return cleandisabled()"  method="post">
				<table class="ui-form">
					<tbody>
						<tr class="ui-form-row">
							<td class="ui-form-label" width="5%">
								<label>发送日期</label>
							</td>
							<td class="ui-form-control" width="45%" colspan="3">
								<table class="ui-combo-controls">
									<tbody>
										<tr>
											<td width="120">
												<div class="ui-text ui-datepicker ui-text-lg">
													<input type="text" autocomplete="off" class="ui-text-text "  name="startTime" value="${bean.startTime!""}" id="startDate" />
													<i class="iconfont ui-text-icon ">&#xe615;</i>
												</div>
											</td>
											<td width="20" align="center">
												<em>~</em>
											</td>
											<td width="120">
												<div class="ui-text ui-datepicker ui-text-lg">
													<input type="text" autocomplete="off" class="ui-text-text " name="endTime"  value="${bean.endTime!""}" id="endDate" />
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
					<button class="ui-button ui-button-secondary ui-button-lg" id="btnSearch" type="submit">
						<span class="ui-button-text">搜 索</span>
					</button>

				</div>
			</form>
		</div>
		<div class="ui-datagrid-header text-right">
	</div>
	<div class="ui-datagrid" style="width: auto">
		<div class="ui-datagrid-head clearfix">
			<div class="ui-datagrid-head-inner">
				<table style="width: 1593px;">
					<colgroup>
						<col width="120">
						<col width="90">
						<col width="150">
						<col width="150">
						<col width="90">
						<col width="200">
					</colgroup>
					<thead>
						<tr>
							<th><div title="发送时间" class="ui-datagrid-cell asd">发送时间</div></th>
							<th><div title="发送人 " class="ui-datagrid-cell asd">发送人 </div></th>
							<th><div title="uuid" class="ui-datagrid-cell asd">uuid</div></th>
							<th><div title="上报id" class="ui-datagrid-cell asd">上报id</div></th>
							<th><div title="发送状态" class="ui-datagrid-cell asd">发送状态</div></th>
							<th><div title="操作" class="ui-datagrid-cell">操作</div></th>
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
						<col width="120">
						<col width="90">
						<col width="150">
						<col width="150">
						<col width="90">
						<col width="200">
					</colgroup>
					<tbody>
						 <#list list as item>
						<tr id="1">
							<td title="${item.createTime?string('yyyy-MM-dd HH:mm:ss')}"><div class="ui-datagrid-cell asd">${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</div></td>
							<td title="${item.sendName!""}"><div class="ui-datagrid-cell asd">${item.sendName!""}</div></td>
						    <td title="${item.reportUuid!""}"><div class="ui-datagrid-cell asd">${item.reportUuid!""}</div></td>
						   <td title="${item.reportId!""}"><div class="ui-datagrid-cell asd">${item.reportId!""}</div></td>
							<td title="<#if (item.state) ==1>成功<#else >失败</#if>"><div class="ui-datagrid-cell asd"><#if (item.state) ==1>成功<#else >失败</#if></div></td>
							<td  style="text-align: left;">
							<div class="ui-datagrid-cell">
							            <#if (item.state) ==2>
									    <label class="ui-datagrid-link mr5  ui-button fml-edit-btn cxfs"   data-id="${item.id!""}" data-u="ds" >重新发送</label>
									    </#if>
									    <label class="ui-datagrid-link mr5  ui-button fml-edit-btn see"   data-id="${item.id!""}" data-u="ds" >查看详细信息</label>
								</div>
								
								</td>
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
	           ${page.getPageStr()!""}
		</div>
	</div>
	</div>
	  <!-- 数据加载中的动画 -->
<!--  <#if  (list?size<=0) > -->
<!--   <div class="fml-no-data ui-datagrid-body-inner" > -->
<!--       <span class="loading">数据加载中...</span> -->
<!--   </div> -->
<!-- </#if> -->
	<script type="text/javascript" src="/core/js/third/jQuery/jQuery-1.8.3.js"></script>
				<script src="/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
	<script type="text/javascript" src="/core/js/libs/core.js"></script>
	<script type="text/javascript" src="/core/js/libs/driver.js"></script>
	<script type="text/javascript" src="/core/js/libs/components/jQuery.form.js"></script>
	<script type="text/javascript" src="/core/js/libs/components/jQuery.datagrid.js"></script>
	<script type="text/javascript" src="/core/js/libs/components/jQuery.selecter.js"></script>
	<script type="text/javascript" src="${basePath}/core/js/libs/components/jQuery.dialog.js"></script>
	<script type="text/javascript" src="/core/js/third/mousewheel/jquery.pictureViewer.js"></script>
	<script type="text/javascript" src="/js/operation/js_commplete.js"></script>
		<script type="text/javascript" src="${basePath}/zdrk/js/handle.js"></script>
		<script type="text/javascript" src="${basePath}/core/js/libs/components/jQuery.datepicker.js"></script>
</body>

</html>