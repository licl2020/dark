    $.ajaxSetup( {
    	//设置ajax请求结束后的执行动作
    	complete : 
    	function(XMLHttpRequest, textStatus) {
    	// 通过XMLHttpRequest取得响应头，sessionstatus
    	var REDIRECT = XMLHttpRequest.getResponseHeader("REDIRECT");
    	if (REDIRECT == "REDIRECT") {
    	var win = window;
    	while (win != win.top){
    	win = win.top;
    	}
    	var json = XMLHttpRequest.getResponseHeader("CONTENTPATH");
    	var obj = JSON.parse(json);
    	$.showConfirm({
			icon: 'succeed',
			content: decrypt(obj.message)
		});
    	setTimeout(function() {
    		top.location.href= obj.url;
    	}, 2000)
    	
    	}
    	}
    	});
    

    
    /**
     * 加密（需要先加载lib/aes/aes.min.js文件）
     * @param word
     * @returns {*}
     */
    function encrypt(word){
        var key = CryptoJS.enc.Utf8.parse("sbsbsbsbsbsbsbsb");
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    }

    /**
     * 解密
     * @param word
     * @returns {*}
     */
    function decrypt(word){
        var key = CryptoJS.enc.Utf8.parse("sbsbsbsbsbsbsbsb");
        var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }