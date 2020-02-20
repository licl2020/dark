$(document).ready(function() {
$("#area1,#province,#city").change(function(){ 
	var province=$("#province").val();
	var city=$("#city").val();
	var qu=$("#area1").val();
	/*if(qu==""||qu=="null"){
		alert("你还未选择区镇");
		return false;
	}*/
	$.post(cuurrent_host+"/company/getcompany.action",{province:province,city:city,area:qu},function(data){
		 var data2 = eval(data);
		 var obj = document.getElementById("companyid");
		 $("#companyid").find("option:selected").text("");  
		 $("#companyid").empty();  
		 var jsonLength=0;
		 for(var i in data2)
		 {
		 obj.options[i]= new Option(data[i].dwmc,data[i].companyId);
		 jsonLength++;
		 }
	     if(jsonLength == 0){
	    	 $("#companyid").append("<option value=''>请选择网点</option>"); 
	     }
		
	});
	$(this).blur();
}).focus(function(){
    $(this)[0].selectedIndex = -1;
}); 
 });


function init(){
	 var a = $("#province").attr("data-province");
	 var b = $("#city").attr("data-city");
	 var c = $("#area1").attr("data-district");
	 var e = $("#companyid").attr("data-oid");
	 var d = true;
		if(a==""|| a=="null" || typeof(a) == "undefined"){
    		d = false;
    	}
	if(d){
		 $('#distpicker').distpicker({
			    province: '-省-',
			    city: '-市-',
			    district: '-区-'
			  });
		 $.post(cuurrent_host+"/company/getcompany.action",{province:a,city:b,area:c},function(data){
			 var data2 = eval(data);
			 var obj = document.getElementById("companyid");
			 $("#companyid").find("option:selected").text("");  
			 $("#companyid").empty();  
			 var jsonLength=0;
			 for(var i in data2)
			 {
			 obj.options[i]= new Option(data[i].dwmc,data[i].companyId);
			 jsonLength++;
			 }
		     if(jsonLength == 0){
		    	 $("#companyid").append("<option value=''>请选择网点</option>"); 
		     }else{
		         $("#companyid ").val(e); 
		     }
			
		})
	}else{
		$('#distpicker').distpicker('reset', true);
	}
}



