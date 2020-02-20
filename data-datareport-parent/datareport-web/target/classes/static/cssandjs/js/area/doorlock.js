
function selectcity(type,parentid,id,area_id){
	
	$.post(cuurrent_host+"/area/getarea.action",{type:type,parentid:parentid},function(data){
		 var data2 = eval(data);
		 var obj = document.getElementById(id);
		 var objInput = document.getElementById(id+'-selecter');
		  $("#"+id).find("option:selected").text("");  
		  $("#"+id).empty();  
		 var jsonLength=0;
		 var getssq=$("#getssq").val();
         var count=data2.length;
         var k=0;
		 for(var i in data2)
		 {
			 var d_id=data[i].id;
			 if(d_id=='no'){
				 d_id="";
			 }
		 obj.options[k]= new Option(data[i].name,d_id);
		 if(area_id !="" &&  area_id !="null" &&  area_id !=null){
			 if(area_id == data[i].id){
				 $(obj.options[k]).attr('selected',true)
				 $(objInput).val(data[i].name)
					 if(getssq==1 || getssq==5){
						 if(type ==1){
							 $("#"+id).attr("disabled","disabled"); 
							 }
					 }else if(getssq==2){
						  if(type !=4){
							  $("#"+id).attr("disabled","disabled"); 
						  }
					 }else if(getssq==3){
						  if(type !=4 && type !=3){
							  $("#"+id).attr("disabled","disabled"); 
						  }
					 }else if(getssq==4){
						  if(type ==1){
							  $("#"+id).attr("disabled","disabled"); 
						  }
					 }
			 }
		 }
		 k=k+1;
		 }
	})
	
}


$(document).ready(function() {
	
	var pce=null;
	var ct=null;
	var dct=null;
		$("#province").mouseover(function(){
			pce=$('#province option:selected').val();
      });
	$("#province").mouseout(function(){
	  var a=$('#province option:selected').val();
	    if(a==null){
	    	$("#province").val(pce); 	
	    }
		  
		});
	
	
	$("#city").mouseover(function(){
		ct=$('#city option:selected').val();
		      });
	$("#city").mouseout(function(){
		var b=$('#city option:selected').val();
	    if(b==null){
	    	$("#city").val(ct); 	
	    }
		});
	$("#district").mouseover(function(){
		dct=$('#district option:selected').val();
		      });
	$("#district").mouseout(function(){
		var c=$('#district option:selected').val();
	    if(c==null){
	    	$("#district").val(dct); 	
	    }
		});
	
	
	$('#province').on('change',function(){
		 var province=$("#province").val();
		 if(province){
		 var area_id=$("#city").attr("data-city");
		 selectcity(2,province,"city",area_id);
		 }
		 $("#district").find("option:selected").text("");  
		 $("#district").empty();
		 $("#district").append("<option value=''>请选择</option>"); 
		 $("#road").find("option:selected").text("");  
		 $("#road").empty();
		 $("#road").append("<option value=''>请选择</option>"); 
		 $('#city-selecter').val('');
		 $('#district-selecter').val('');
		 $('#road-selecter').val('');
		 $(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	}); 
	
	$('#city').on('change',function(){
		 var city=$("#city").val();
		 if(city){
		 var area_id=$("#district").attr("data-district");
		 selectcity(3,city,"district",area_id);
		 }
		 $("#road").find("option:selected").text("");  
		 $("#road").empty();
		 $("#road").append("<option value=''>请选择</option>"); 
		 $('#district-selecter').val('');
		 $('#road-selecter').val('');
		 $(this).blur();
	}).focus(function(){
	    $(this)[0].selectedIndex = -1;
	}); 
	
//	$("#district").on('change',function(){ 
//		var district=$("#district").val();
//		if(district){
//		var area_id=$("#road").attr("data-road");
//		 selectcity(4,district,"road",area_id);
//		}
//		 $("#road").find("option:selected").text("");  
//		 $("#road").empty();
//		 $("#road").append("<option value=''>请选择</option>"); 
//		 $('#road-selecter').val('');
//		$(this).blur();
//	}).focus(function(){
//	    $(this)[0].selectedIndex = -1;
//	});
	
	
	 });





