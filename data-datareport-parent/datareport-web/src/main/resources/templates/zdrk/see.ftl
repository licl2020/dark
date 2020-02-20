<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>查看异常信息</title>
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
    <div class="ui-datagrid mb80" style=" width:auto">
        <div class="ui-forms-bodr">
                <table class="ui-form">
                    <tbody>
                        <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>发送数据</label>
                            </td>
                            <td class="ui-form-control" colspan="3">
                                <div class="ui-textarea">
                                    <textarea type="text" class="ui-textarea-text" name="content" id="content" disabled style="height: 200px">${msgData!""}</textarea>
                                </div>
                            </td>
                        </tr>
                         <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>异常信息</label>
                            </td>
                            <td class="ui-form-control" colspan="3">
                                <div class="ui-textarea">
                                    <textarea type="text" class="ui-textarea-text" name="content" id="content" disabled style="height: 200px">${msgContent!""}</textarea>
                                </div>
                            </td>
                        </tr>
                         <tr class="ui-form-row">
                            <td class="ui-form-label" width="5%">
                                <label><span class="fml-red">*</span>发送时间</label>
                            </td>
                            <td class="ui-form-control" colspan="3">
                                 <div class="ui-text ui-text-lg">
                                    <input type="text" disabled  class="ui-text-text"  value="${time?string('yyyy-MM-dd HH:mm:ss')}"  id="name" />
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
        </p>
    </div>
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
        <script type="text/javascript" src="/core/js/libs/components/jQuery.treeSelecter.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.selecter.js"></script>
    <script type="text/javascript" src="/core/js/libs/components/jQuery.validator.js"></script>
    <script src="/js/jquery.form.js"></script>
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
                $('.ui-textarea-text').verify('required');
            },
            btnCloseClick: function () {
                var dialog = jQuery.getDialog('see');
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
                   					that.closeDialog(true,'interfaceLoginfo',resp.message);
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
        };
        $(function () {
            loadUi.initialize()
            var json=$("#content").val();
            if(json){
            	 $("#content").val(formatJson(json));
            }
        });
        function initRequest(formData, jqForm, options) {
    		var queryString = $.param(formData); //组装数据，插件会自动提交数据
    		return true;
    	}
        
        // 工具方法
        var formatJson = function(json, options) {
                            var reg = null,
                                formatted = '',
                                pad = 0,
                                PADDING = '    '; // one can also use '\t' or a different number of spaces
                            // optional settings
                            options = options || {};
                            // remove newline where '{' or '[' follows ':'
                            options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
                            // use a space after a colon
                            options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
         
                            // begin formatting...
         
                            // make sure we start with the JSON as a string
                            if (typeof json !== 'string') {
                                json = JSON.stringify(json);
                            }
                            // parse and stringify in order to remove extra whitespace
                            json = JSON.parse(json);
                            json = JSON.stringify(json);
         
                            // add newline before and after curly braces
                            reg = /([\{\}])/g;
                            json = json.replace(reg, '\r\n$1\r\n');
         
                            // add newline before and after square brackets
                            reg = /([\[\]])/g;
                            json = json.replace(reg, '\r\n$1\r\n');
         
                            // add newline after comma
                            reg = /(\,)/g;
                            json = json.replace(reg, '$1\r\n');
         
                            // remove multiple newlines
                            reg = /(\r\n\r\n)/g;
                            json = json.replace(reg, '\r\n');
         
                            // remove newlines before commas
                            reg = /\r\n\,/g;
                            json = json.replace(reg, ',');
         
                            // optional formatting...
                            if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                                reg = /\:\r\n\{/g;
                                json = json.replace(reg, ':{');
                                reg = /\:\r\n\[/g;
                                json = json.replace(reg, ':[');
                            }
                            if (options.spaceAfterColon) {
                                reg = /\:/g;
                                json = json.replace(reg, ': ');
                            }
         
                            $.each(json.split('\r\n'), function(index, node) {
                                var i = 0,
                                    indent = 0,
                                    padding = '';
         
                                if (node.match(/\{$/) || node.match(/\[$/)) {
                                    indent = 1;
                                } else if (node.match(/\}/) || node.match(/\]/)) {
                                    if (pad !== 0) {
                                        pad -= 1;
                                    }
                                } else {
                                    indent = 0;
                                }
         
                                for (i = 0; i < pad; i++) {
                                    padding += PADDING;
                                }
                                formatted += padding + node + '\r\n';
                                pad += indent;
                            });
                            return formatted;
                        };
    </script>
</body>

</html>