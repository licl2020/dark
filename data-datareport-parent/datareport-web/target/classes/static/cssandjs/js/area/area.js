

// 省市区
function init(wrapper) {
    var province = $($("#" + wrapper + "").find(".m-province"));
    var city = $($("#" + wrapper + "").find(".m-city"));
    var county = $($("#" + wrapper + "").find(".m-county"));
    province.append("<option value='' >请选择省</option>").val("");
    city.append("<option value=''>请选择市</option>").val("");
    county.append("<option value=''>请选择区/县</option>").val("");

    selectprovince();


    
    
    
    
    province.change(function() {
        var provinceVal = province.val();
        city.empty().append("<option value=''>请选择市</option>").val("");
        county.empty().append("<option value=''>请选择区/县</option>").val("");
        selectcity(provinceVal);
        $(this).blur();
        if(province.val()==""){
            city.empty().append("<option value=''>请选择市</option>").val("");
            county.empty().append("<option value=''>请选择区/县</option>").val("");
        }
    }).focus(function(){
        var hasLength=$("option[value=]",$(this)).length;
        if(hasLength<=0){
            province.append("<option value=''>请选择省</option>").val("");}
    });
    province.blur(function(){
        if(province.val()==""){
            city.empty().append("<option value=''>请选择市</option>").val("");
            county.empty().append("<option value=''>请选择区/县</option>").val("");
        }
    });
    city.change(function() {
        var cityVal = city.val();
        county.empty().append("<option value=''>请选择区/县</option>").val("");
        selectcounty(cityVal);
        $(this).blur();
    }).focus(function(){
        var hasLength=$("option[value=]",$(this)).length;
        if(hasLength<=0){
            city.append("<option value='' style='display:none'>请选择市</option>").val("");
            //city.append("<option value='c'>请选择市</option>").val("c");
        }
    });
    // 查询省
    function selectprovince(x) {

        $.ajax({
            type : "get",
            async : true,
            url :  cuurrent_host+"/area/selectarea.action",
            data:{},
            success : function(data) {
                if (data) {
                    var data2 = eval(data);
                    province.find("option:selected").text("");
                    province.empty();
                    var jsonLength = 0;
                    for ( var i in data2) {
                        province[0].options[i] = new Option(data[i].name,
                            data[i].id);

                        jsonLength++;
                    }
                    province.append("<option value='' >请选择省</option>").val("");
                    if (jsonLength == 0) {
                        province[0].empty().append("<option value=''>请选择省</option>").val("");
                    }
                } else {
                    city[0].empty().append("<option value=''>请选择市</option>").val("");
                    county[0].empty().append("<option value=''>请选择区/县</option>").val("");
                }
            }
        });
    }

    // 查询市
    function selectcity(id) {
        $.ajax({
            type : "get",
            async : true,
            url : cuurrent_host+"/area/selectarea.action",
            data : {
                parentid : id
            },
            success : function(data) {
                if (data) {
                    city.empty();
                    var data2 = eval(data);
                    city.find("option:selected").text("");
                    var jsonLength = 0;
                    for ( var i in data2) {
                        city[0].options[i] = new Option(data[i].name,
                            data[i].id);

                        jsonLength++;
                    }
                    city.append("<option value=''>请选择市</option>").val("");
                    if (jsonLength == 0) {
                        city[0].empty().append("<option value=''>请选择市</option>").val("");
                    }
                } else {
                    city[0].empty().append("<option value=''>请选择市</option>").val("");
                    county[0].empty().append("<option value=''>请选择区/县</option>").val("");
                }
            }
        });
    }
    // 查询县区
    function selectcounty(id) {
        $.ajax({
            type : "get",
            async : true,
            url :  cuurrent_host+"/area/selectarea.action",
            data : {
                parentid : id
            },
            success : function(data) {
                var data2 = eval(data);;
                var jsonLength = 0;
                for ( var i in data2) {
                    county[0].options[i] = new Option(data[i].name, data[i].id);

                    jsonLength++;
                }
                county.append("<option value=''>请选择区/县</option>").val("");
                if (jsonLength == 0) {
                    county[0].empty().append("<option value=''>请选择区/县</option>").val("");
                }
            }

        });
    }
    ;
};
var disArr=$(".distpicker");
$.each(disArr,function(i,item){
    var disId=$(item).attr("id");
    init(disId);
});


$("#investor-list").delegate(".close-item","click",function(event){
    var target=event.target;
    $(target).parent().remove();
});


//查询省
function selectprovince1(x) {
	var returnData={result:"",parentid:""};
	        $.ajax({
	            type : "get",
	            async : false,
	            url :  cuurrent_host+"/area/selectarea.action",
	            data:{},
	            success : function(data) {
	               if(data){
	            	   $.each(data,function(i,n){
	            		    if(n.id===x){
	            		    	returnData.result=n.name;
	            		    	returnData.parentid=n.parentid;
	            		    }	            		
	            		 });
	               } 
       
	            }
	        });
	        return returnData;
	    }
// 查询市
 function selectcity1(id,x) {
	 var result;
     $.ajax({
         type : "get",
         async : false,
         url : cuurrent_host+"/area/selectarea.action",
         data : {
             parentid : id
         },
         success : function(data) {
         	console.log(data,x)
         	if(data){
	            	   $.each(data,function(i,n){
	            		    if(n.id===x){
	            		    	result=n.name;	            		    	
	            		    }
	            		});
	               }
         }
     });
     return result;
 }
// 查询县区
 function selectcounty1(id,x) {
	 var result;
     $.ajax({
         type : "get",
         async : false,
         url :  cuurrent_host+"/area/selectarea.action",
         data : {
             parentid : id
         },
         success : function(data) {
        	 console.log(data,id)
         	if(data){
	            	   $.each(data,function(i,n){
	            		    if(n.id===x){
	            		    	result=n.name;
	            		    }
	            		});
	               }
         }

     });
     console.log(result);
     return result;
 }