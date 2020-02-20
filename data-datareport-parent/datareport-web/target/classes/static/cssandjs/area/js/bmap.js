
var map = new BMap.Map("container", { minZoom: 4, maxZoom: 20 });    // 创建Map实例
map.centerAndZoom(new BMap.Point(120.15, 30.28), 11);  // 初始化地图,设置中心点坐标和地图级别当前为杭州的经纬度

var geoc = new BMap.Geocoder();   

map.addEventListener("click", function(e){   
    var pt = e.point;
    geoc.getLocation(pt, function(rs){
         var buildingPoints1 = [
                               { "lng": pt.lng, "lat": pt.lat, "name":addres, value: 10, liveValue: 100 }
                           ];
          lng=pt.lng;
          lat=pt.lat;
         map.clearOverlays()
         addMarker(buildingPoints1);
    });        
});

// 绘制房屋数标记点
function addMarker(points) {
    for (var i = 0, pointsLen = points.length; i < pointsLen; i++) {
        var point = new BMap.Point(points[i].lng, points[i].lat);
        var marker = new BMap.Marker(point);
        setLabelBuild(marker, points[i])
    }
}
// 定制每幢标记以及标记点文本
function setLabelBuild(marker, buildingPoints) {
    var pt = new BMap.Point(buildingPoints.lng, buildingPoints.lat);
    var myIcon = new BMap.Icon(cuurrent_host+"/core/images/map/singleLogo.png", new BMap.Size(28, 42));
    var marker = new BMap.Marker(pt, { icon: myIcon });
//    marker.enableDragging();
//    marker.addEventListener("dragend", function (e) {
//        var gc = new BMap.Geocoder();
//            gc.getLocation(e.point, function (rs) {
//                map.removeOverlay(marker.getLabel());
//                var addComp = rs.addressComponents;
//                var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;//获取地址 //画图 ---》显示地址信息
//                setLabelBuild(marker, { lng: e.point.lng, lat: e.point.lat,name:address })
//            });
//    });
    map.addOverlay(marker);
    

    
    label = new BMap.Label('<div class="fml-build">' + buildingPoints.name + '</div>' +
        '<div style="padding:0 8px">' + "经度：" + buildingPoints.lng + '</div><div style="padding:0 8px">' + "纬度：" + buildingPoints.lat + '</div>' +
        '<div class="dialog-foot" style="margin:10px 0;">' +
        '<p class="ui-button-center">' +
        '<a id="btnSave" class="ui-button ui-button-secondary ui-button-lg mr10" data-btn="save" onclick="btnSave()">' +
        '<em class="ui-button-text">保存</em>' +
        '</a>' +
     /*   '<a id="btnClose" class="ui-button ui-button-border-info ui-button-lg " data-btn="cancel" onclick="btnClose()">' +
        '<em class="ui-button-text">取消</em>' +*/
        '</a>' +

        '</p>' +
        '</div>', { offset: new BMap.Size(40, -30) });
    marker.setLabel(label);
    label.setStyle({
        color: "#2B2B2B", fontSize: "14px", textAlign: 'left', backgroundColor: '#fff', lineHeight: '28px', width: '285px', MozUserSelect: 'none',
        boxShadow: '0px 3px 2px 0px rgba(0, 0, 0, 0.16)', opacity: '0.9', border: 'none', borderRadius: '4px'
    });

    // label.addEventListener("click", function(e){
    //     console.log(e.target)
    //     console.log($(this).attr('class'))
    // })
}
map.enableScrollWheelZoom(true);

// 每幢标记点120.245443,30.164494
var lng=$("#lng").val();
var lat=$("#lat").val();
var addres=$("#addres").val();
var buildingPoints = [
    { "lng":lng, "lat": lat, "name": addres, value: 10, liveValue: 100 }
];


// 用经纬度设置地图中心点
function theLocation() {
    map.clearOverlays();
    var new_point = new BMap.Point(buildingPoints[0].lng, buildingPoints[0].lat);
    var marker = new BMap.Marker(new_point);  // 创建标注
    map.addOverlay(marker);              // 将标注添加到地图中
    map.panTo(new_point);
    map.getZoom(18)//此级别可显示区县 市级坐标

}
$(function () {
    setTimeout(function () {
        // map.getZoom(18)//此级别可显示区县 市级坐标
        theLocation()
        addMarker(buildingPoints);
    }, 100);

})
function btnSave(e) {
	
			var id=$("#id").val();
		     $.ajax({
	               url: cuurrent_host+"/area/updatelal.action",
	               type: 'POST',
	               dataType: 'json',
	               data : {
	            	'lng' : lng,
	       			'lat' : lat,
	       			'id':id
	   			   },
	               async: false,
	               success: function (response) {
	            		if (response.code == 200) {
	            			$.showConfirm({
	            				icon: 'succeed',
	            				content: response.message
	            			})
	            			  win.window.location.reload();
        				} else {
        					$.showConfirm({
        						icon: 'warning',
        						content: response.message
        					});
        				}
	               },
	               error: function (response) {
	                   console.log(response);
	               }
	           });

}
function btnClose(){
    map.clearOverlays()
    addMarker(buildingPoints);
}