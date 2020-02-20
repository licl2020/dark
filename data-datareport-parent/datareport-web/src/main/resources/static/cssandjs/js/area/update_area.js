$(function(){
   
	   var type=$("#type").val();
	  
	   selectprovince(type);
	   if(type==1){
		selectcity('330000');
	   }else{
			var id=$("#province").attr("data-province");
		   selectcity(id);  
	   }
		var cityid= $("#city").attr("data-city");
		selectcounty(cityid)
		
		$("#province").change(function(){ 
			var province=$("#province").val();
			$("#city").find("option:selected").text("请选择");
			$("#county").find("option:selected").text("请选择");  
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
		data:{
			type:x
			},
		success:function(data){
			 var data2 = eval(data);
			 var id=$("#province").attr("data-province");
			 var obj = document.getElementById("province");
			 $("#province").find("option:selected").text("");  
			 $("#province").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].name,data[i].id);
			 if(id !="" &&  id !="null" &&  id !=null){
				 if(id == data[i].id){
				 obj[i].selected = true;
				 }
			 }
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
	var cityid= $("#city").attr("data-city");
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
			 if(cityid !="" &&  cityid !="null" &&  cityid !=null){
				 if(cityid == data[i].id){
				 obj[i].selected = true;
				 }
			 }
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
	
	 
	 var countyid = $("#county").attr("data-county");
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
			 if(countyid !="" &&  countyid !="null" &&  countyid !=null){
				 if(countyid == data[i].id){
				 obj[i].selected = true;
				 }
			 }
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#county").append("<option value=''>请选择区/县</option>"); 
		     }
		}
		
	});
}