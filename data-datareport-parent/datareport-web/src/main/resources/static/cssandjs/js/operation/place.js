$(function(){
	
	$("#province").change(function(){ 
		var province=$("#province").val();
		selectadmin(province,null,null);
		$(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	});
	
	$("#city").change(function(){ 
		var city=$("#city").val();
		selectadmin(null,city,null);
		$(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	}); 
	
	$("#county").change(function(){ 
		var city=$("#county").val();
		selectadmin(null,null,city);
		$(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	}); 
	 
	
});





//查询市
function selectadmin(province,city,county){
	$.ajax({
		type:"get",
		async:true,
		url:cuurrent_host+"/admin/selectadmin.action",
		data:{province:province,city:city,county:county},
		success:function(data){
			 var data2 = eval(data);
			 var obj = document.getElementById("adminid");
			 $("#adminid").find("option:selected").text("");  
			 $("#adminid").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].nickname,data[i].id);
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#adminid").append("<option value=''>请选择采集点</option>"); 
		     }
		}
		
	});
}

