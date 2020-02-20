// 
(function($){
	
	// 收起下拉单选框
	$.fn.collapseSelecter = function(){
		return this.each(function(){
			$(this).css('z-index', '').find('div.ui-selecter-options').html('').css('display', 'none');
		});
	};
	
	// 收起下拉复选框
    $.fn.collapseMultiSelecter = function(){
    	return this.each(function(){
        	$(this).css('zIndex', '').find('div.ui-multiselecter-options').off().remove();
        });
    };
    
	// 收起下拉树形选择框
	$.fn.collapseTreeSelecter = function(){
		return this.each(function(){
            $(this).css('z-index', '').find('div.ui-treeselecter-options').css('display', 'none');
        });
	};
	
	// 收起输入自动补全
	$.fn.collapseAutocomplete = function(){
		
	};
	
})(jQuery);

Namespace.register('ui.components');
// 收缩控件
ui.components.collapse = function(){
    // 下拉单选框
    $('div.ui-selecter').collapseSelecter();
    // 下拉复选框
    $('div.ui-multiselecter').collapseMultiSelecter();
    // 下拉树形选择框
    $('div.ui-treeselecter').collapseTreeSelecter();
    // 输入自动补全
    $('.ui-autocomplete-options').off().remove();
};

ui.components.setDisabled = function(id,bool){
    if(bool){
        var div= document.createElement("div");
        div.className="btn-ajax-disabled";
         $('#'+id).addClass('ajax-disabled').append(div);
    }else{
       $('#'+id).removeClass('ajax-disabled').children('.btn-ajax-disabled').remove();
    }
};

// 获取当前窗口对象和顶级窗口对象
var win = window, winTop = win.top;

// 引用顶级窗口的tabs对象
var tabs = winTop.tabs;

// 在IE6浏览器下，避免iframe出现纵向滚动条时页面右侧溢出
var ie6IframeScroll = function(){
    $('html').css('overflow-y', $('html')[0].scrollHeight < $('html').height() ? 'auto' : 'scroll');
};

$(function(){
	$('.ui-datagrid-body-inner').scroll(function(){
		$(this).parent('.ui-datagrid-body').prev('.ui-datagrid-head').scrollLeft(this.scrollLeft);
	})
    // 
    if(core.isIe6 && core.isIframePage){
        $(winTop).resize(function(){
            ie6IframeScroll();
        });
    }
    
    // 
    $('body')
    // ui-text , ui-textarea : mouseover , mouseout, focus , blur
    .off('mouseover.text').on('mouseover.text', '.ui-text, .ui-textarea', function(){
        if(!$(this).hasClass('ui-text-error') && !$(this).hasClass('ui-text-disabled')){
            $(this).addClass('ui-text-hover');
        }
    })
    .off('mouseout.text').on('mouseout.text', '.ui-text, .ui-textarea', function(){
        $(this).removeClass('ui-text-hover');
    })
    .off('focus.text').on('focus.text', '.ui-text, .ui-textarea', function(){
        if(!$(this).hasClass('ui-text-error') && !$(this).hasClass('ui-text-disabled')){
            $(this).addClass('ui-text-focus');
        }
    })
    .off('blur.text').on('blur.text', '.ui-text, .ui-textarea', function(){
        $(this).removeClass('ui-text-focus');
    })
    // ui-button : mouseover , mouseout
    .off('mouseover.button').on('mouseover.button', '.ui-button', function(){
        $(this).addClass('ui-button-hover');
    })
    .off('mouseout.button').on('mouseout.button', '.ui-button', function(){
        $(this).removeClass('ui-button-hover ui-button-active');
    })
    // ui-button-info : mouseover , mouseout
    .off('mouseover.buttonInfo').on('mouseover.buttonInfo', '.ui-button-info', function(){
        $(this).addClass('ui-button-info-hover');
    })
    .off('mouseout.buttonInfo').on('mouseout.buttonInfo', '.ui-button-info', function(){
        $(this).removeClass('ui-button-info-hover ui-button-active');
    })
    // ui-button-warn : mouseover , mouseout
    .off('mouseover.buttonWarn').on('mouseover.buttonWarn', '.ui-button-warn', function(){
        $(this).addClass('ui-button-warn-hover');
    })
    .off('mouseout.buttonWarn').on('mouseout.buttonWarn', '.ui-button-warn', function(){
        $(this).removeClass('ui-button-warn-hover ui-button-active');
    })
    // ui-button-success : mouseover , mouseout
    .off('mouseover.buttonSuccess').on('mouseover.buttonSuccess', '.ui-button-success', function(){
        $(this).addClass('ui-button-success-hover');
    })
    .off('mouseout.buttonSuccess').on('mouseout.buttonSuccess', '.ui-button-success', function(){
        $(this).removeClass('ui-button-success-hover ui-button-active');
    })
    // ui-button-danger : mouseover , mouseout
    .off('mouseover.buttonDanger').on('mouseover.buttonDanger', '.ui-button-danger', function(){
        $(this).addClass('ui-button-danger-hover');
    })
    .off('mouseout.buttonDanger').on('mouseout.buttonDanger', '.ui-button-danger', function(){
        $(this).removeClass('ui-button-danger-hover ui-button-active');
    })
    // ui-button-inverse : mouseover , mouseout
    .off('mouseover.buttonInverse').on('mouseover.buttonInverse', '.ui-button-inverse', function(){
        $(this).addClass('ui-button-inverse-hover');
    })
    .off('mouseout.buttonInverse').on('mouseout.buttonInverse', '.ui-button-inverse', function(){
        $(this).removeClass('ui-button-inverse-hover ui-button-active');
    })
    // ui-button : mousedown , mouseup
    .off('mousedown.button').on('mousedown.button', '.ui-button', function(){
        $(this).addClass('ui-button-active');
    })
    .off('mouseup.button').on('mouseup.button', '.ui-button', function(){
        $(this).removeClass('ui-button-active');
    })
    // ui-link : mouseover , mouseout
    .off('mouseover.link').on('mouseover.link', '.ui-link', function(){
        $(this).addClass('ui-link-hover');
    })
    .off('mouseout.link').on('mouseout.link', '.ui-link', function(){
        $(this).removeClass('ui-link-hover');
    })
    // ui-notification > close , ui-notification-mini > close : click
    .off('click.notification').on('click.notification', '.ui-notification .close, .ui-notification-mini .close', function(){
        $(this).parent().remove();
    })
    // ui-collapsible > ui-widget-header : click
    .off('click.collapsible').on('click.collapsible', '.ui-collapsible .ui-widget-head', function(){
        var n = $(this).next();
        if(n.is(':visible')){
            n.css('display', 'none');
        }else{
            n.css('display', 'block');
        }
    });

});

function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象    
    var obj = ev.target || ev.srcElement; //获取事件源    
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型    
    //获取作为判断条件的事件类型    
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况    
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，    
    //并且readOnly属性为true或disabled属性为true的，则退格键失效    
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效    
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
    //判断    
    if (flag2 || flag1) return false;
}
//禁止后退键 作用于Firefox、Opera   
document.onkeypress = forbidBackSpace;
//禁止后退键作用于IE、Chrome   
document.onkeydown = forbidBackSpace; 
