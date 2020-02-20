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
            <form id="form">
                <!-- <table class="ui-form">
                    <tbody>
                             <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label>分配权限</label>
                            </td>
                            <td class="ui-form-control" width="20%"> -->
                               <div class="ui-treeSelecter">
                                       <!--  <input type="hidden" name="jurisdictionid" value="$!{bean.jurisdictionid}"  id="jurisdictionid"/> -->
                                       <!--  <input type="text" class="ui-text-text"   id="jurisdictionid" name="jurisdictionid" value="" autocomplete="off" 
                                        placeholder="分配权限" readonly="readonly"> -->
                                        
                                       <input name="id" type="hidden" id="id" value="${bean.id}">
                                        <div class="ui-treeselecter-options">
                                        	<div class="options nobg">
                                        	 <div id="myTree" class="ztree blue"></div>
                                        	</div>
                                        </div>
                                        
                                         
                                    	</div>
                               <!--  </div> -->
                         <!--    </td>
                        </tr>
                    </tbody>
                </table> -->
<div class="dialog-foot">
        <p class="ui-button-center">
            <a id="btnClose" class="ui-button ui-button-border-info ui-button-lg">
                <em class="ui-button-text">关闭</em>
            </a>
            <a id="save"   class="ui-button ui-button-secondary ui-button-lg">
                <em class="ui-button-text">保存</em>
            </a>
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
    <script src="${basePath}/cssandjs//js/role.js"></script>
</script>
    <script type="text/javascript">
	$(function () {
		$('#btnClose').click(function (e) {
			var dialog = jQuery.getDialog('roleDialog');
			dialog.close();
		});
	});
    </script>
</body>

</html>