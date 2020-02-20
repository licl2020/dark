<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网约房信息库</title>
    <link type="text/css" rel="stylesheet" href="${basePath}/cssandjs/core/theme/global.css" />
    <link type="text/css" rel="stylesheet" href="${basePath}/cssandjs/core/theme/default/ui.css" />
</head>

<body>
    <div class="ui-forms mb80">
        <div class="ui-forms-bodr">
            <form id="form"   method="post" <#if (bean.id)??> action="${basePath}/modular/update"  <#else> action="${basePath}/modular/add" </#if>  > 
                <table class="ui-form">
                    <tbody>
                         <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>权限名称</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="20" name="name" id="mName"
                                        autocomplete="off" placeholder="权限名称" value="${bean.name?if_exists}" />
                                </div>
                            </td>
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>权限类型</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <select placeholder="功能类型" autocomplete="off" name="type" id="mType" class="ui-text-lg">
                                    <option <#if (bean.type)??> <#if (bean.type == 1)>  selected = "selected" </#if> </#if>    value="1">菜单</option>
                                    <option <#if (bean.type)??> <#if (bean.type == 2)>  selected = "selected" </#if> </#if>    value="2">页面</option>
                                    <option <#if (bean.type)??> <#if (bean.type == 3)>  selected = "selected" </#if> </#if>    value="3">按钮</option>
                                </select>
                            </td>
                        </tr>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>是否可分配</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <select placeholder="是否可分配" autocomplete="off" id="isdistribution" class="ui-text-lg" name="isdistribution">
                                    <option <#if (bean.isdistribution)??> <#if (bean.isdistribution == 1)>   selected = "selected" </#if> </#if>   value="1">可分配</option>
                                    <option <#if (bean.isdistribution)??> <#if (bean.isdistribution == 2)>   selected = "selected" </#if> </#if>   value="2">不可分配</option>
                                </select>
                            </td>
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>上级菜单</label>
                            </td>
                              <td class="ui-form-control" width="20%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" id="gxdwTree" name="readOnlyParentName" value="${parent_name?if_exists}" autocomplete="off"
                                        placeholder="上级菜单" />
                                       <input name="parentId" type="hidden" id="mParentId" value="${bean.parentId?if_exists}">
                                    <input name="id" type="hidden" id="id" value="${bean.id?if_exists}">
                                    <input name="yldyo" type="hidden" id="yldyo" value="${bean.parentId?if_exists}">
                                       <input name="parentType" type="hidden" id="parentType">
                                        <div class="ui-treeselecter-options">
                                        	<div class="options nobg">
                                        	 <div id="myTree" class="ztree blue"></div>
                                        	</div>
                                        </div>
                                        
                                         
                                    	</div>
                                </div>
                            </td>
                        </tr>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>权限标识</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                 <input type="text" class="ui-text-text" maxlength="20" name="permission" id="permission"
                                        autocomplete="off" placeholder="权限标识" value="${bean.permission?if_exists}" />
                                </div>
                            </td>
                             <td class="ui-form-label" width="5%">
                             <label>访问路径</label>
                            </td>
                             <td class="ui-form-control" width="45%">
                              <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="50" name="visitUrl" id="mMothd"
                                        autocomplete="off" placeholder="访问路径"  value="${bean.visitUrl?if_exists}"/>
                                </div>
                            </td>
                            <td colspan="2"></td>
                        </tr>
                                                <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>排序</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="number" class="ui-text-text" maxlength="10" name="orderNum" id="orderNum"
                                        autocomplete="off" placeholder="排序" value="${bean.orderNum?if_exists}" onKeyUp="this.value=this.value.replace(/\D/g,'')" />
                                </div>
                            </td>
                             <td class="ui-form-label" width="5%">
                                <label>Ico图标</label>
                            </td>
                             <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="50" name="iconcls" id="mMothd"
                                        autocomplete="off" placeholder="Ico图标"  value="${bean.iconcls?if_exists}"/>
                                </div>
                            </td>
                            <td colspan="2"></td>
                        </tr>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label>备注</label>
                            </td>
                            <td class="ui-form-control" colspan="3">
                                <div class="ui-textarea">
                                    <textarea type="text" maxlength="200" class="ui-textarea-text" id="remark" name="remark" style="height:100px">${bean.remark?if_exists}</textarea>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
<div class="dialog-foot">
        <p class="ui-button-center">
            <button id="btnClose" class="ui-button ui-button-border-info ui-button-lg">
                <em class="ui-button-text">关闭</em>
            </button>
            <button id="btnSave"  type="submit" class="ui-button ui-button-secondary ui-button-lg">
                <em class="ui-button-text">保存</em>
            </button>
        </p>
    </div>
            </form>
        </div>
    </div>
    
    <script type="text/javascript" src="${basePath}/cssandjs/js/operation/cuurrent_host.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/third/jQuery/jQuery-1.8.3.js"></script>
    			<script src="${basePath}/cssandjs/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="${basePath}/cssandjs/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="${basePath}/cssandjs/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/core.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/driver.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.form.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.dialog.js"></script>
    	<script type="text/javascript" src="${basePath}/cssandjs/js/operation/js_commplete.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.treeSelecter.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.selecter.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/core/js/libs/components/jQuery.validator.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/js/area/doorlock.js"></script>
    <script type="text/javascript" src="${basePath}/cssandjs/js/ztree/js/index.js"></script>	
    <script src="${basePath}/cssandjs/js/jquery.form.js"></script>
    <script>
$(function () {
	  $('#gxdwTree').treeSelecter({treeId:'roleOptions'})
	  $("#parentType").val(parseInt($("#mType").val())-1);
	    initAuth(click);
        function click(treeNode){
        	  $("#gxdwTree").val(treeNode.name);
        	  $("#mParentId").val(treeNode.id);
              $("#parentType").val(treeNode.type);
              $('.ui-treeselecter-options').hide();
              $("#gxdwTree").parent().removeClass('ui-text-error')
        	  $("#gxdwTree").parent().find('.fml-error').remove()
        }
        
        //$('div.ui-treeselecter-options').on('click',function(e.stopPropagation();){});
    
});
</script>
    <script type="text/javascript">
        var loadUi = {
            initialize: function () {
                this.btnClose = $('#btnClose');
                this.btnSave = $('#btnSave');
                this.ANIMATE_TIME = 5000;
                this.initEvent();
            },
            initEvent: function () {
                this.btnClose.on('click', $.proxy(this.btnCloseClick, this));
                this.btnSave.on('click', $.proxy(this.btnSaveClick, this));
		        $('#mName,#mType,#gxdwTree,#mController,mMothd,mParameter').verify('required');
                $('#mSort').verify('required number');
            },
            btnCloseClick: function () {
                var dialog = jQuery.getDialog('perDialog');
                dialog.close();
            },
            btnSaveClick: function () {
                var flag = $.verify('#form');
              //获取选中的父级菜单的authType
                var authType=$("#mType").val();
            	var parentType=$("#parentType").val();
            	var parentId=$("#mParentId").val();
            	if(parentId==""||parentId=="null"){
            		alert("您还未选择上级菜单,请选择!");
            		flag= false;
            	}
            	if(parentType=="0"){
            		if(authType=="2"){
            			alert("根目录下不能添加页面功能!");
            			flag= false;
            		}else if(authType=="3"){
            			alert("根目录下不能添加按钮功能!");
            			flag= false;
            		}
            	}else if(parentType=="1"){
            		if(authType=="1"){
            			alert("菜单功能下不能添加菜单功能!");
            			flag= false;
            		}else if(authType=="3"){
            			alert("菜单功能下不能添加按钮功能!");
            			flag= false;
            		}
            	}else if(parentType=="2"){
            		if(authType=="1"){
            			alert("页面功能下不能添加菜单功能!");
            			flag= false;
            		}
            		else if(authType=="2"){
            			alert("页面功能下不能添加页面功能!");
            			flag= false;
            		}
            	}else{
            		alert("按钮功能下不能添加其他功能!");
            		flag= false;
            	}
                if(flag){
                    	var that=this;
                        var Json = getFormJson("#form");
                        var options = {
                    			beforeSubmit :initRequest,
                    			success : function(resp) {
                    			//	var responJson = eval('(' + resp + ')');
                    				if (resp.code == 200) {
                    					that.closeDialog(true,'perDialog',resp.message);
                    				} else {
                    					$.showConfirm({
                    						icon: 'warning',
                    						content: resp.message
                    					});
                    				}
                    			}
                    		};
                    $('form').ajaxForm(options);
                }else{
                	 return false;
                }
      
            },
            closeDialog: function (ref, dialogID, o) {
                var dialog = jQuery.getDialog(dialogID);
                var win = tabs.getIframeWindow(dialog.config.tabId);
                if (ref && win && win.window.loadUi) {
                    win.window.loadUi.gridRefresh(o)
                }
                if (dialog) {
                    dialog.close();
                }
            }
        };
        $(function () {
            $('select').selecter()
            loadUi.initialize()
        });
        
        function initRequest(formData, jqForm, options) {
    		var queryString = $.param(formData); //组装数据，插件会自动提交数据
    		return true;
    	}
    </script>
</body>

</html>