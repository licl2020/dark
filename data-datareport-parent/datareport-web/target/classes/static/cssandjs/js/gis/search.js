  //地址搜索 a 查询参数  b下拉框id c 返回值id c 1 开始  2 结束
  function search_place(a,b,c){
      var addr = $("#"+a).val();
      show_addr(a,addr,b,c);
  }
  //自定义图标
    var myIcon = new BMap.Icon("http://www.yantiansf.cn/mapImage/1.gif", new BMap.Size(30,30),{
        anchor:new BMap.Size(13,15),
        imageOffset:new BMap.Size(0,0)
    });
    
    
    //地图搜索
      function show_addr(a,addr,re,c){
          var addr_res = $('#'+re);
          addr_res.html("");
          local.search(addr);
          local.setSearchCompleteCallback(function(rs){
              if(local.getStatus() == BMAP_STATUS_SUCCESS){

                  for(var i= 0 ; i < rs.getCurrentNumPois();i++){
                      var poi = rs.getPoi(i);
                      var div = "<div style='cursor:pointer;padding:10px;border-bottom:1px solid #f1f1f1' onclick=\"myFun('"+poi.title+"','"+poi.address+"','"+poi.point.lng+"','"+poi.point.lat+"','"+re+"','"+a+"','"+c+"')\">";
                      div += "<div>"+poi.title+"</div>"
                      div += "<div>"+poi.address+"</div>"
                      div += "</div>";
                      addr_res.append(div);
                  }
              }
          });
      }
    
      function myFun(title,myValue,lng,lat,re,a,c){           

                var point = {};               
                point.name = title;                  
           	    point.name = myValue; 
                point.lng = lng;                
                point.lat = lat;               
                //如果选中了，可以考虑将此地址以及坐标保存起来。            
                var point1=new BMap.Point(lng,lat)
	            var marker = new BMap.Marker(point1,{icon:myIcon});  //创建标注
                map.centerAndZoom(point1, 18);  
                var mk = new BMap.Marker(point1,{icon:myIcon});
                map.clearOverlays();
                map.addOverlay(mk);   
                //添加标注           
                //添加文本标注             
                var opts = {position:point1,offset:new BMap.Size(10,-30)};              
                var label = new BMap.Label(title, opts);                
                label.setStyle({color:"red",fontSize:"12px",height:"20px",lineheight:"20px",fontFamily:"微软雅黑"});
                map.addOverlay(label);   
                var addr_res = $("#"+re);
                addr_res.html("");     
                $("#"+a).val(myValue);
                if(c==1){
                	ks=lng;
                	kj=lat;
                }else{
                	js=lng;
                	jj=lat;
                }
                }      
      
      
      //根据地址查询经纬度
      function searchByStationName(address) {
    	　　local.setSearchCompleteCallback(function (searchResult) {
    	　　　　var poi = searchResult.getPoi(0);
    	　　　　return poi;
    	　　});
    	　　local.search(address);
    	}
     
      