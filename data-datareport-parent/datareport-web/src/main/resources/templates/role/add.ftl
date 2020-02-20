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
            <form id="form"   method="post" <#if (bean.id)??> action="${basePath}/role/update"  <#else> action="${basePath}/role/add" </#if>  > 
                <table class="ui-form">
                    <tbody>
                            <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>角色名称</label>
                            </td>
                            <td class="ui-form-control" width="95%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" name="name" maxlength="20" autocomplete="off" placeholder="角色名称"
                                        id="name" value="${bean.name?if_exists}"/>
                                        <input class="text-box single-line" name="id"  type="hidden" value="${bean.id?if_exists}">
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
		        $('#name').verify('required');
            },
            btnCloseClick: function () {
                var dialog = jQuery.getDialog('roleDialog');
                dialog.close();
            },
            btnSaveClick: function () {
                var flag = $.verify('#form');
                var that=this;
                if(flag){
                    var Json = getFormJson("#form");
                    var options = {
                			beforeSubmit :initRequest,
                			success : function(resp) {
                			//	var responJson = eval('(' + resp + ')');
                				if (resp.code == 200) {
                					that.closeDialog(true,'roleDialog',resp.message);
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
            beforeClick:function(treeId, treeNode) {
           	 click(treeNode);
           }
        };
        
        function initRequest(formData, jqForm, options) {
    		var queryString = $.param(formData); //组装数据，插件会自动提交数据
    		return true;
    	}
        $(function () {
            loadUi.initialize()
        });
    </script>
</body>

</html>