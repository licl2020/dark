//获取当前网址，如： http://localhost:8080/Tmall/index.jsp 
var curWwwPath=window.document.location.href; 
//获取带"/"的项目名，如：/Tmall 
var pathName=window.document.location.pathname; 
var pos=curWwwPath.indexOf(pathName); 
//获取主机地址，如： http://localhost:8080 
var cuurrent_host=curWwwPath.substring(0,pos); 
//var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
//cuurrent_host=cuurrent_host+projectName;