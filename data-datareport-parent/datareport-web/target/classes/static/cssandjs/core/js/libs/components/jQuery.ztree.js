(function(){
	if(!(jQuery && jQuery.fn && jQuery.fn.zTree)){
		
		document.write('<link type="text/css" rel="stylesheet" href="'+cuurrent_host+"/cssandjs/core/js/third/zTree/css/zTreeStyle/zTreeStyle.css"+'" />');
		
		$.holdReady(true);
		$.getScript(cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.core-3.2.min.js", function() {
		     $.holdReady(false);
		});
		$.getScript(cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.excheck-3.2.min.js", function() {
		     $.holdReady(false);
		});
		$.getScript(cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.core-3.2.min.js", function() {
		     $.holdReady(false);
		});
		$.getScript(cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.core-3.2.min.js", function() {
		     $.holdReady(false);
		});
		
		document.write('<script type="text/javascript" src=""><\/script>');
		
		document.write('<script type="text/javascript" src="'+cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.excheck-3.2.min.js"+'"><\/script>');
		
		document.write('<script type="text/javascript" src="'+cuurrent_host+"/cssandjs/core/js/third/zTree/js/jQuery.ztree.exedit-3.2.min.js"+'"><\/script>');
		
	}
})()