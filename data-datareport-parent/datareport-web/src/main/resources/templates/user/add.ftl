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
            <form id="form"    method="post"  <#if (bean.id)??> action="${basePath}/admin/update"  <#else> action="${basePath}/admin/add" </#if>  > 
                <table class="ui-form">
                    <tbody>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>用户名</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                                <div class="ui-text ui-text-lg">
                                    <input type="text" class="ui-text-text" maxlength="20" name="uName" id="yhName" autocomplete="off" value="${bean.uName?if_exists}" placeholder="用户名"
                                        id="jrsName" />
                                        <input class="text-box single-line" name="id"  type="hidden" value="${bean.id?if_exists}">
                                </div>
                            </td>
                        </tr>
                        <tr class="ui-form-row">
                           <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>角色</label>
                            </td>
                            <td class="ui-form-control" width="45%">
                               <select autocomplete="off" placeholder="角色" class="ui-text-lg" name="rId" id="roleid">
                                     <#list roleList as item>
                                   <option <#if (bean.rId)??> <#if (item.id == bean.rId)>  selected = "selected" </#if> </#if>  value="${item.id?if_exists}">${item.name?if_exists}</option>
                                      </#list>
                                </select>
                            </td>
                            <td colspan="2"></td>
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
                $('#province').verify('required');
                $('#city').verify('required');
                $('#mobile').verify('mobile required');
            },
            btnCloseClick: function () {
                var dialog = jQuery.getDialog('editInfo');
                dialog.close();
            },
            btnSaveClick: function () {
                var flag = $.verify('#form');
                var that=this;
             	 var type=$("#orgtype").val();
              	 var city=$("#city").val();
              	 var area_no=$("#area_no").val();
              	 var parea_no=$("#parea_no").val();
              	 var district=$("#district").val();
                 if(type){
           			
           			if(type==2 && city && city != area_no){
           				$.showConfirm({
              				icon: 'warning',
              				content: '管辖机构与市不匹配，请重新选择'
              			})
              			return false;
           			}
           			
           			if(type==3 && parea_no && city && parea_no != city){
           				$.showConfirm({
              				icon: 'warning',
              				content: '管辖机构与市不匹配，请重新选择'
              			})
              			return false;
           			}
           			if(type==3 && district && area_no && district != area_no){
           				$.showConfirm({
              				icon: 'warning',
              				content: '管辖机构与县/区不匹配，请重新选择'
              			})
              			return false;
           			}
           		}
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