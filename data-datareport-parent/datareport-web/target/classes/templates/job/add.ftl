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
            <form id="form"    method="post"  <#if (bean.jobName)??> action="${basePath}/job/update"  <#else> action="${basePath}/job/add" </#if>  > 
                <table class="ui-form">
                    <tbody>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>分组名</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="20" <#if (bean.jobName)??> readonly  unselectable="on"  </#if>  name="groupName" id="yhName" autocomplete="off" value="${bean.groupName?if_exists}" placeholder="分组名"
                                        id="jrsName" />
                                </div>
                            </td>
                        </tr>
                       <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>任务名</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="20" <#if (bean.jobName)??> readonly  unselectable="on"  </#if> name="jobName" id="yhName" autocomplete="off" value="${bean.jobName?if_exists}" placeholder="任务名"
                                        id="jrsName" />
                                </div>
                            </td>
                        </tr>
                           <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>CRON表达式</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="20"  name="cronExp" id="yhName" autocomplete="off" value="${bean.cronExp?if_exists}" placeholder="CRON表达式"
                                        id="jrsName" />
                                </div>
                            </td>
                        </tr>
                           <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>任务类</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="50" <#if (bean.jobName)??> readonly  unselectable="on"  </#if> name="className" id="yhName" autocomplete="off" value="${bean.className?if_exists}" placeholder="任务类"
                                        id="jrsName" />
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

    <script type="text/javascript">
        var loadUi = {
            initialize: function () {
                this.btnClose = $('#btnClose');
                this.btnSave = $('#btnSave');
		        this.gxdwTree = $('#gxdwTree');
                this.ANIMATE_TIME = 5000;
                this.initEvent();
            },
            initEvent: function () {
                this.btnClose.on('click', $.proxy(this.btnCloseClick, this));
                this.btnSave.on('click', $.proxy(this.btnSaveClick, this));
		        this.gxdwTree.treeSelecter(this.gxdwTreeOption);
                $('#yhName,#gxdwTree').verify('required');
            },
            btnCloseClick: function () {
                var dialog = jQuery.getDialog('editInfo');
                dialog.close();
            },
            btnSaveClick: function () {
                var flag = $.verify('#form');
                var that=this;
              	$('select').attr("disabled", false);
                if(flag){
                	
                    var Json = getFormJson("#form");
                    var options = {
                			beforeSubmit :initRequest,
                			success : function(resp) {
                			//	var responJson = eval('(' + resp + ')');
                				if (resp.code == 200) {
                					that.closeDialog(true,'editInfo',resp.message);
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
            },
            gxdwTreeOption:{
                treeId: 'treeClassification',
                showAction:true,
                async:true,
                cache:true,
                beforeClick:this.beforeClick,
                url:cuurrent_host+"/torgan/childs.action",
                onClick: function(e, treeId, node, flag){
                    $('#gxdwTree').val(node.name).focus();
                    $('#readyGxdw').val(node.id)
                }
            },
            beforeClick:function(treeId, treeNode) {
           	 click(treeNode);
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