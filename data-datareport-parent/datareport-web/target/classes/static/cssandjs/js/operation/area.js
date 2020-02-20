$(function(){
	var type=$("#type").val();
	selectprovince(type);
	if(type==1){
	selectcity('330000');
	}
	
	$("#province").change(function(){ 
		var province=$("#province").val();
		selectcity(province);
		$(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	});
	
	$("#city").change(function(){ 
		var city=$("#city").val();
		selectcounty(city);
		$(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	}); 
	 
	
});

//查询省
function selectprovince(x){
	$.ajax({
		type:"get",
		async:true,
		url:cuurrent_host+"/area/selectarea.action",
		data:{type:x},
		success:function(data){
			 var data2 = eval(data);
			 var obj = document.getElementById("province");
			 $("#province").find("option:selected").text("");  
			 $("#province").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].name,data[i].id);
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#province").append("<option value=''>请选择省</option>"); 
		     }
		}
		
	});
}




//查询市
function selectcity(id){
	$.ajax({
		type:"get",
		async:true,
		url:cuurrent_host+"/area/selectarea.action",
		data:{parentid:id},
		success:function(data){
			 var data2 = eval(data);
			 var obj = document.getElementById("city");
			 $("#city").find("option:selected").text("");  
			 $("#city").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].name,data[i].id);
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#city").append("<option value=''>请选择市</option>"); 
		     }
		}
		
	});
}

//查询区/县
function selectcounty(id){
	$.ajax({
		type:"get",
		async:true,
		url:cuurrent_host+"/area/selectarea.action",
		data:{parentid:id},
		success:function(data){
			 var data2 = eval(data);
			 var obj = document.getElementById("county");
			 $("#county").find("option:selected").text("");  
			 $("#county").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].name,data[i].id);
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#county").append("<option value=''>请选择区/县</option>"); 
		     }
		}
		
	});
}