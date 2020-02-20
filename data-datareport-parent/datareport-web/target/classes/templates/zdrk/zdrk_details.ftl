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
	<form id="form" action="${request.contextPath}/zdrk/zdrkDataList"  data-ajax="true" data-ajax-mode="replace"  onsubmit="return cleandisabled()"  method="post">
	<input name="reportId" value="${bean.reportId}" type="hidden"/>
	<div class="ui-forms">
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
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
					</colgroup>
					<thead>
					   <tr>
                            <th>
                                <div class="ui-datagrid-cell asd" title="姓名">姓名</div>
                            </th>
                            <th>
                                <div class="ui-datagrid-cell asd" title="身份证号">身份证号</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="性别">性别</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="出生日期">出生日期</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="户籍省市县区">户籍省市县区</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="户籍区划内详细地址">户籍区划内详细地址</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="民族">民族</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="重点人管理类别代码">重点人管理类别代码</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="责任民警姓名">责任民警姓名</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="责任民警身份号码">责任民警身份号码</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="责任民警联系电话">责任民警联系电话</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="登记时间">登记时间</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="管辖单位代码">管辖单位代码</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="管辖单位">管辖单位</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="数据归属单位代码">数据归属单位代码</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="数据归属单位名称">数据归属单位名称</div>
                            </th>
                              <th>
                                <div class="ui-datagrid-cell asd" title="数据状态">数据状态</div>
                            </th>
                            <th>
                                <div class="ui-datagrid-cell asd" title="异常描述">异常描述</div>
                            </th>
                                 <th>
                                <div class="ui-datagrid-cell asd" title="上报状态">上报状态</div>
                            </th>
                            <th>
                                <div class="ui-datagrid-cell asd" title="上报描述">上报描述</div>
                            </th>
                        </tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="ui-datagrid-body" id="grid">
			<div class="ui-datagrid-body-inner"
				style="text-align: center;overflow: auto !important;" >
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
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
						<col width="90">
					</colgroup>
					<tbody>
						 <#list list as item>
						<tr id="1">
					                <td title="${item.xm!""}"><div class="ui-datagrid-cell asd">${item.xm!""}</div></td>
					                <td title="${item.gmsfhm!""}"><div class="ui-datagrid-cell asd">${item.gmsfhm!""}</div></td>
					                <td title="<#if (item.xbdm) =='1'>男<#else >女</#if>"><div class="ui-datagrid-cell asd"><#if (item.xbdm) =='1'>男<#else >女</#if></div></td>
					                <td title="${item.csrq!""}"><div class="ui-datagrid-cell asd">${item.csrq!""}</div></td>
					                <td title="${item.hjdzSsxqdm!""}"><div class="ui-datagrid-cell asd">${item.hjdzSsxqdm!""}</div></td>
					                <td title="${item.hjdzQhnxxdz!""}"><div class="ui-datagrid-cell asd">${item.hjdzQhnxxdz!""}</div></td>
					                <td title="${item.mzdm!""}"><div class="ui-datagrid-cell asd">${item.mzdm!""}</div></td>
					                <td title="${item.zazdrgllbdm!""}"><div class="ui-datagrid-cell asd">${item.zazdrgllbdm!""}</div></td>
					                <td title="${item.zrmjXm!""}"><div class="ui-datagrid-cell asd">${item.zrmjXm!""}</div></td>
					                <td title="${item.zrmjGmsfhm!""}"><div class="ui-datagrid-cell asd">${item.zrmjGmsfhm!""}</div></td>
					                <td title="${item.zrmjLxdh!""}"><div class="ui-datagrid-cell asd">${item.zrmjLxdh!""}</div></td>
					                <td title="${item.djsj!""}"><div class="ui-datagrid-cell asd">${item.djsj!""}</div></td>
					                <td title="${item.spdwGajgjgdm!""}"><div class="ui-datagrid-cell asd">${item.spdwGajgjgdm!""}</div></td>
					                <td title="${item.spdwGajgmc!""}"><div class="ui-datagrid-cell asd">${item.spdwGajgmc!""}</div></td>
					                <td title="${item.sjgsdwdm!""}"><div class="ui-datagrid-cell asd">${item.sjgsdwdm!""}</div></td>
					                <td title="${item.sjgsdwmc!""}"><div class="ui-datagrid-cell asd">${item.sjgsdwmc!""}</div></td>
					                <td title="<#if (item.state) ==1>正常<#else >异常</#if>"><div class="ui-datagrid-cell asd"><#if (item.state) ==1>正常<#else >异常</#if></div></td>
					                <td title="${item.msg!""}"><div class="ui-datagrid-cell asd">${item.msg!""}</div></td>
					                <td title="<#if (item.sbState) ==1>未上报<#elseif (item.sbState) ==2>上报中<#elseif (item.sbState) ==3>上报成功<#else >上报异常</#if>"><div class="ui-datagrid-cell asd"><#if (item.sbState) ==1>未上报<#elseif (item.sbState) ==2>上报中<#elseif (item.sbState) ==3>上报成功<#else >上报异常</#if></div></td>
					                <td title="${item.sbMsg!""}"><div class="ui-datagrid-cell asd">${item.sbMsg!""}</div></td>
						</tr>
						</#list>
						<#if  (list?size<=0) >
						<tr id="2">
						<td title="暂无数据" rowspan="18">
						<div class="fml-no-data ui-datagrid-body-inner">暂无数据</div></td>
                           </tr>
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
	</form>
	  <div class="dialog-foot">
        <p class="ui-button-center">
            <a id="btnClose" class="ui-button ui-button-border-info ui-button-lg">
                <em class="ui-button-text">关闭</em>
            </a>
        </p>
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
		    <script type="text/javascript">
        $(function () {
            $('#btnClose').click(function (e) {
                var dialog = jQuery.getDialog('hotelguestinfo');
                dialog.close();
            });
        });
    </script>
</body>

</html>