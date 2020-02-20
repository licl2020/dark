<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网约房信息库</title>
    <link type="text/css" rel="stylesheet" href="/core/theme/global.css" />
    <link type="text/css" rel="stylesheet" href="/core/theme/default/ui.css" />
</head>

<body>
    <div class="ui-forms mb80">
        <div class="ui-forms-bodr">
            <form id="form" action="${request.contextPath}/zdrk/uploadExcel"  method="post" enctype="multipart/form-data">
                <table class="ui-form">
                    <tbody>
                            <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>文件</label>
                            </td>
                            <td class="ui-form-control" width="95%">
                                <div class="ui-text ui-text-lg">
                                         <input class="ui-text-text" id="file" name="file" type="file" >
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
    
    <script type="text/javascript" src="/js/operation/cuurrent_host.js"></script>
    <script type="text/javascript" src="/core/js/third/jQuery/jQuery-1.8.3.js"></script>
    			<script src="/js/cryptojs3.2/rollups/aes.js"></script>
	<script src="/js/cryptojs3.2/components/mode-ecb-min.js"></script>
	<script src="/js/cryptojs3.2/components/pad-nopadding-min.js"></script>
    <script type="text/javascript" src="/core/js/libs/core.js"></script>
    <script type="text/javascript" src="/core/js/libs/driver.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.form.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.dialog.js"></script>
    	<script type="text/javascript" src="/js/operation/js_commplete.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.selecter.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.validator.js"></script>
    <script type="text/javascript" src="/js/jquery.form.js" ></script>
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
                var dialog = jQuery.getDialog('zdrksc');
                dialog.close();
            },
            btnSaveClick: function () {
            	$("#btnSave").hide()
                var flag = $.verify('#form');
           	 var val = $("#file").val(); //获取
      	   if(!val){
      	    alert("请选择excel文件");
      	  flag=false;
      	$("#btnSave").show()
      	    return false;
      	   }
      	 if(!checkData()){
      		return false;
      	 }
      	
      	 
                var that=this;
                if(flag){
                    var Json = getFormJson("#form");
                    var options = {
                			beforeSubmit :initRequest,
                			success : function(resp) {
                				var responJson = eval('(' + resp + ')');
                				if (responJson.code == 200) {
                					that.closeDialog(true,'zdrksc',responJson.describe);
                				} else {
                					$.showConfirm({
                						icon: 'warning',
                						content: responJson.describe
                					});
                					$("#btnSave").show()
                				}
                			}
                		};
                    $('form').ajaxForm(options);
                }else{
                	 $("#btnSave").show()
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
        
        checkData=function (){    
        	       var fileDir = $("#file").val();    
        	       var suffix = fileDir.substr(fileDir.lastIndexOf("."));    
        	       if("" == fileDir){  
		        		$.showConfirm({
							icon: 'warning',
							content: '选择需要导入的Excel文件！'
						});
						$("#btnSave").show() 
		        	           return false;    
		        	       }    
        	       if(".xls" != suffix && ".xlsx" != suffix ){ 
		        		$.showConfirm({
							icon: 'warning',
							content: '选择模板格式的文件导入'
						});
				$("#btnSave").show()    
        	           $('#file').val("");
        	           return false;    
        	       }    
        	       return true;    
        	    }
    </script>
</body>

</html>