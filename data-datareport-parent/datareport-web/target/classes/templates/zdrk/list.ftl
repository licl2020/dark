<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>上报列表</title>
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
			<form id="form" action="${request.contextPath}/zdrk/list"  data-ajax="true" data-ajax-mode="replace"   method="post">
				<table class="ui-form">
					<tbody>
						<tr class="ui-form-row">
							<td class="ui-form-label" width="5%">
								<label>上报日期</label>
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
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			onclick="javascript:location.reload();"> <em class="ui-button-text">刷新</em>
		</span>
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="uploadExcel"> <em class="ui-button-text">上报重点人口</em>
		</span>	
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			>
			<a class="btn btn-primary"  href="/file/zdrk_mb.xlsx" >上报模板下载</a>
		</span>
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			>
			<a class="btn btn-primary"  href="/file/zdrk_xg_sjx_sm.xlsx" >相关数据项说明下载</a>
		</span>
			
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
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="300">
					</colgroup>
					<thead>
						<tr>
							<th><div title="上报时间" class="ui-datagrid-cell asd">上报时间</div></th>
							<th><div title="上报状态 " class="ui-datagrid-cell asd">上报状态 </div></th>
							<th><div title="上报人" class="ui-datagrid-cell asd">上报人</div></th>
							<th><div title="上报类别" class="ui-datagrid-cell asd">上报类别</div></th>
							<th><div title="上报总数" class="ui-datagrid-cell asd">上报总数</div></th>
							<th><div title="校验通过数" class="ui-datagrid-cell asd">校验通过数</div></th>
							<th><div title="校验异常数" class="ui-datagrid-cell asd">校验异常数</div></th>
							<th><div title="上报成功数" class="ui-datagrid-cell asd">上报成功数</div></th>
							<th><div title="上报异常数" class="ui-datagrid-cell asd">上报异常数</div></th>
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
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="300">
					</colgroup>
					<tbody>
						 <#list list as item>
						<tr id="1">
							<td title="${item.reportTime?string('yyyy-MM-dd HH:mm:ss')}"><div class="ui-datagrid-cell asd">${item.reportTime?string('yyyy-MM-dd HH:mm:ss')}</div></td>
							<td title="<#if (item.reportState) ==1>数据校验中<#elseif  (item.reportState) ==2>数据校验未通过<#elseif  (item.reportState) ==3>数据上报中<#elseif  (item.reportState) ==4>上报异常<#elseif  (item.reportState) ==5>等待上报反馈中<#else >上报完成</#if>"><div class="ui-datagrid-cell asd"><#if (item.reportState) ==1>数据校验中<#elseif  (item.reportState) ==2>数据校验未通过<#elseif  (item.reportState) ==3>数据上报中<#elseif  (item.reportState) ==4>上报异常<#elseif  (item.reportState) ==5>等待上报反馈中<#else >上报完成</#if></div></td>
							<td title="${item.reportName!""}"><div class="ui-datagrid-cell asd">${item.reportName!""}</div></td>
							<td title="<#if (item.reportType) ==1>实有人<#elseif  (item.reportType) ==2>实有单位<#else >重点人</#if>"><div class="ui-datagrid-cell asd"><#if (item.reportType) ==1>实有人<#elseif  (item.reportType) ==2>实有单位<#else >重点人</#if></div></td>
							<td title="${item.reportCount!""}"><div class="ui-datagrid-cell asd">${item.reportCount!""}</div></td>
							<td title="${item.checkSuccessCount!""}"><div class="ui-datagrid-cell asd">${item.checkSuccessCount!""}</div></td>
							<td title="${item.checkErrorCount!""}"><div class="ui-datagrid-cell asd">${item.checkErrorCount!""}</div></td>
							<td title="${item.reportSuccessCount!""}"><div class="ui-datagrid-cell asd">${item.reportSuccessCount!""}</div></td>
							<td title="${item.reportErrorCount!""}"><div class="ui-datagrid-cell asd">${item.reportErrorCount!""}</div></td>
							<td  style="text-align: left;">
							<div class="ui-datagrid-cell">
									    <label class="ui-datagrid-link mr5  ui-button fml-edit-btn zdrkDetails"   data-id="${item.id!""}" data-u="ds" >查看数据详情</label>
									     <#if item.checkExcelUrl?default("")?trim?length gt 1>
									    <label class="ui-datagrid-link mr5  ui-button fml-edit-btn " ><a href="${item.checkExcelUrl}" >下载校验文件</a></label>
									    </#if>
									    <#if item.reportExcelUrl?default("")?trim?length gt 1>
									    <label class="ui-datagrid-link mr5  ui-button fml-edit-btn " ><a href="${item.reportExcelUrl}" >下载上报文件</a></label>
								        </#if>
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