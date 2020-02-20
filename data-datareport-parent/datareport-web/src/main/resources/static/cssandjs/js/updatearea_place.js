// 省市区
function init(wrapper) {
	var province = $($("#" + wrapper + "").find(".m-province"));
	var city = $($("#" + wrapper + "").find(".m-city"));
	var county = $($("#" + wrapper + "").find(".m-county"));
	var type = $("#type").val();
	province.append("<option value='p'>请选择省</option>").val("p");
	city.append("<option value='c'>请选择市</option>").val("c");
	county.append("<option value='d'>请选择区/县</option>").val("d");
	selectprovince(type);
	if (type == 1) {
		selectcity('330000');
	} else {
		var id = province.attr("data-province");
		selectcity(id);
	}
	var cityid = city.attr("data-city");
	selectcounty(cityid);
	province.change(function() {
		var provinceVal = province.val();
		city.empty().append("<option value='c'>请选择市</option>").val("c");
		county.empty().append("<option value='d'>请选择区/县</option>").val("d");
		selectcity(provinceVal);
		$(this).blur();
		if(province.val()=="p"){
			city.empty().append("<option value='c'>请选择市</option>").val("c");
			county.empty().append("<option value='d'>请选择区/县</option>").val("d");
		}
	}).focus(function(){
		var hasLength=$("option[value=p]",$(this)).length;
        if(hasLength<=0){
        	province.append("<option value='p' style='display:none'>请选择省</option>").val("p");}
    });
    province.blur(function(){
		if(province.val()=="p"){
			city.empty().append("<option value='c'>请选择市</option>").val("c");
			county.empty().append("<option value='d'>请选择区/县</option>").val("d");
		}
    });
	city.change(function() {
		var cityVal = city.val();
		county.empty().append("<option value='d'>请选择区/县</option>").val("d");
		selectcounty(cityVal);
		$(this).blur();
	}).focus(function(){
		var hasLength=$("option[value=c]",$(this)).length;
        if(hasLength<=0){
		city.append("<option value='c' style='display:none'>请选择市</option>").val("c");
		}
    });
	// 查询省
	function selectprovince(x) {
		$.ajax({
			type : "get",
			async : true,
			url : cuurrent_host + "/area/selectarea.action",
			data : {
				type : x
			},
			success : function(data) {
				if (data) {
					var data2 = eval(data);
					var id = province.attr("data-province");
					province.find("option:selected").text("");
					province.empty();
					var jsonLength = 0;
					for ( var i in data2) {
						province[0].options[i] = new Option(data[i].name,
								data[i].id);
						if (id != "" && id != "null" && id != null) {
							if (id == data[i].id) {
								province[0][i].selected = true;
							}
						}
						jsonLength++;
					}
					if (jsonLength == 0) {
						province[0].empty().append("<option value='p'>请选择省</option>").val("p");
					}
				} else {
					city[0].empty().append("<option value='c'>请选择市</option>").val("c");
					county[0].empty().append("<option value='d'>请选择区/县</option>").val("d");
				}
			}
		});
	}

	// 查询市
	function selectcity(id) {
		var cityid = city.attr("data-city");
		$.ajax({
					type : "get",
					async : true,
					url : cuurrent_host + "/area/selectarea.action",
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
								if (cityid != "" && cityid != "null"
										&& cityid != null) {
									if (cityid == data[i].id) {
										city[0][i].selected = true;
									}
								}
								jsonLength++;
							}
							if (jsonLength == 0) {
								city[0].empty().append("<option value='c'>请选择市</option>").val("c");
							}
						} else {
							city[0].empty().append("<option value='c'>请选择市</option>").val("c");
							county[0].empty().append("<option value='d'>请选择区/县</option>").val("d");
						}
					}
				});
	}
	// 查询县区
	function selectcounty(id) {
		var countyid = county.attr("data-county");
		$.ajax({
			type : "get",
			async : true,
			url : cuurrent_host + "/area/selectarea.action",
			data : {
				parentid : id
			},
			success : function(data) {
				var data2 = eval(data);;
				var jsonLength = 0;
				for ( var i in data2) {
					county[0].options[i] = new Option(data[i].name, data[i].id);
					if (countyid != "" && countyid != "null"
							&& countyid != null) {
						if (countyid == data[i].id) {
							county[0][i].selected = true;
						}
					}
					jsonLength++;
				}
				if (jsonLength == 0) {
					county[0].empty().append("<option value='d'>请选择区/县</option>").val("d");
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