if(!$.dialog){
	document.write('<script type="text/javascript" src="../core/js/libs/components/jQuery.dialog.js"><\/script>');
}
document.write('<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>');
document.write('<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="../core/js/third/lodop_print/install_lodop32.exe"><\/embed>');
document.write('<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="../core/js/third/lodop_print/install_lodop64.exe"><\/embed>');
document.write('<\/object>');
var LODOP;
var printInfo = {
	print_preview:function(html, width, height,url){
		//验证是否安装控件
		if(print_check()){
			//初始化控件
			print_init();
			//打印
			print(html, width, height,2,url);
		}else{
			//控件下载页面
			print_down();
		}
	},
	print:function(html, width, height,url){
		//验证是否安装控件
		if(print_check()){
			//初始化控件
			print_init();
			//打印
			print(html, width, height,1,url);
		}else{
			//控件下载页面
			print_down();
		}
	}
};

function print_init(){
	LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM')); 
	LODOP.SET_LICENSES("杭州爱惠信息技术有限公司","BD60DF8932E17DF16509D1E7F683CA3B","","");
	LODOP.PRINT_INIT('');
};

function print_check(){
	var bool = true;
    try{
        var LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        if((LODOP==null) || (typeof(LODOP.VERSION) == 'undefined') || (typeof(LODOP.VERSION) == 'unknown')){
            bool = false;
        }            
    }catch(err){
    	bool = false;
    }
    return bool;
};

function print_down(){
	var PRINT_SRC_PATH;
	if (navigator.userAgent.indexOf('Win64') >= 0){
        PRINT_SRC_PATH = '../core/js/third/lodop_print/install_lodop64.exe';
    }else{
        PRINT_SRC_PATH = '../core/js/third/lodop_print/install_lodop32.exe';
    } 
	
	var installHTML = '<div class=\'ui-model-install\'>'
        + '<h4 style=\'\'>请安装安全打印控件，已保证打印预览正常使用！</h4>'
        + '<p style=\'\'><label>控件安装完成后，请重启浏览器！</label></p>'
        + '<div style=\'\'><a href=\'' + PRINT_SRC_PATH + '\' target=\'_self\'>立即下载</a></div>'
        + '</div>';
	
	//下载打印控件提示对话框
	$.dialog({
		lock: true,
		title: '消息',
		content: installHTML,
		ok: function(){
			return true;
		}
	});

};

function print(html, width, height,type,url){
	LODOP.SET_PRINT_PAGESIZE(1, width, height, '');
	if(html instanceof Array){
		for (var o in html) {
			LODOP.NewPageA();
			LODOP.ADD_PRINT_HTM(5, 0, "RightMargin:0mm","BottomMargin:5mm", html[o]);
		}
		if(url){
			LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='"+url+"'>");
			LODOP.SET_SHOW_MODE("BKIMG_PRINT",1);
		}
		if(type==1){
			LODOP.PRINT();
		}else{
			LODOP.PREVIEW();
		}
		//LODOP.PRINT_DESIGN();
	}
};

/**************************
  本函数根据浏览器类型决定采用哪个对象作为控件实例：
  IE系列、IE内核系列的浏览器采用oOBJECT，
  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
  对于64位浏览器指向64位的安装程序install_lodop64.exe。
**************************/
function getLodop(oOBJECT,oEMBED){
    var oLODOP=oEMBED;		
	try{		     
		if (navigator.appVersion.indexOf("MSIE")>=0) 
			oLODOP=oOBJECT;
	}catch(err){
	}
	return oLODOP; 
}
