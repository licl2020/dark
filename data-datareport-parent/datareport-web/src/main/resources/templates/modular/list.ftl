<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<form id="form" action="${basePath}/modular/list" data-ajax="true" data-ajax-mode="replace" method="post">
			<table class="ui-form">
			<tbody>
						<tr class="ui-form-row">
					      <td class="ui-form-label" width="5%">
                                <label>选择菜单</label>
                            </td>
                            <td class="ui-form-control" width="20%">
                                <div class="ui-text ui-text-lg  ui-treeselecter">
                                    <input type="text" class="ui-text-text" id="gxdwTree"  name="name" value="${bean.name?if_exists}" autocomplete="off"
                                        placeholder="选择菜单" />
                                       <input type="hidden" name="parentId" value="${bean.parentId?if_exists}"  id="parentId"/>
                                        <div class="ui-treeselecter-options">
                                        	<div class="options nobg">
                                        	 <div id="myTree" class="ztree blue"></div>
                                        	</div>
                                        </div>
                                        
                                         
                                    	</div>
                                </div>
                            </td>
                            <td width="75%"></td>
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
	   <@shiro.hasPermission name="modular:add">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="btnAdd"> <em class="ui-button-text">新增</em>
		</span>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="modular:reset">
		<span class="ui-button ui-button-secondary fml-show-btn ui-button-lg"
			id="refResh"> <em class="ui-button-text">应用</em>
		</span>
		</@shiro.hasPermission>
	</div>
	<div class="ui-datagrid" style="width: auto">
		<div class="ui-datagrid-head clearfix">
			<div class="ui-datagrid-head-inner">
				<table style="width: 1593px;">
					<colgroup>
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="200">
					</colgroup>
					<thead>
						<tr>
							<th><div title="序号" class="ui-datagrid-cell asd">序号</div></th>
							<th><div title="权限名称" class="ui-datagrid-cell asd">权限名称</div></th>
							<th><div title="权限类别" class="ui-datagrid-cell asd">权限类别</div></th>
							<th><div title="是否可分配" class="ui-datagrid-cell asd">是否可分配</div></th>
							<th><div title="权限标识" class="ui-datagrid-cell asd">权限标识</div></th>
							<th><div title="是否有下级" class="ui-datagrid-cell asd">是否有下级</div></th>
							<th><div title="排序" class="ui-datagrid-cell asd">排序</div></th>
							<th><div title="Ico样式" class="ui-datagrid-cell asd">Ico图标</div></th>
							<th><div title="备注" class="ui-datagrid-cell asd">备注</div></th>
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
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="100">
						<col width="200">
					</colgroup>
					<tbody>
					  <#list list as moular >
						<tr id="1">		
							<td title="${moular_index+1}"><div class="ui-datagrid-cell asd">${moular_index+1}</div></td>
							<td title="${moular.name}"><div class="ui-datagrid-cell asd">${moular.name}</div></td>
							<td title="${mtype[moular.type?c]!}"><div class="ui-datagrid-cell asd">${mtype[moular.type?c]!}</div></td>
							<td title="<#if (moular.isdistribution) ==1>可分配 <#else >不可分配</#if>"><div class="ui-datagrid-cell asd"><#if (moular.isdistribution) ==1>可分配 <#else >不可分配</#if></div></td>
							<td title="${moular.permission}"><div class="ui-datagrid-cell asd">${moular.permission}</div></td>
							<td title="<#if (moular.mParentc) ==1>有 <#else >无</#if>"><div class="ui-datagrid-cell asd"><#if (moular.mParentc) ==1>有 <#else >无</#if></div></td>
							<td title="${moular.orderNum}"><div class="ui-datagrid-cell asd">${moular.orderNum}</div></td>
						    <td title="${moular.iconcls?if_exists}"><div class="ui-datagrid-cell asd">${moular.iconcls?if_exists}</div></td>
						    <td title="${moular.remark?if_exists}"><div class="ui-datagrid-cell asd">${moular.remark?if_exists}</div></td>
							<td style="text-align: left;">
							<div class="ui-datagrid-cell">
							<@shiro.hasPermission name="modular:del">
							<button class="ui-datagrid-link mr5  ui-button fml-show-btn btnDel"  data-id="${moular.id}" >删除</button>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="modular:update">
							<button class="ui-datagrid-link mr5  ui-button fml-edit-btn" data-id="${moular.id}" >修改</button>
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
	src="${basePath}/cssandjs/moular/js/handle.js"></script>
	    <script type="text/javascript" src="${basePath}/cssandjs/js/area/doorlock.js"></script>
	    <script type="text/javascript" src="${basePath}/cssandjs/js/ztree/js/index.js"></script>	
	    		<script>
    $(function () {
    	initAuth(click);
        function click(treeNode){
        	  $("#gxdwTree").val(treeNode.name);
        	  $("#parentId").val(treeNode.id);
        	  $('.ui-treeselecter-options').hide();
        }
    });
</script>
