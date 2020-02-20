var setting = {
 		        view: {
 		            selectedMulti: false
 		        },
 		        check: {
 		            enable: true
 		        },
 		        data: {
 		            simpleData: {
 		                enable: true
 		            }
 		        }
 		    };

function createTree() {
     $.fn.zTree.init($("#myTree"), setting, zNodes);
     clearFlag = $("#last").attr("checked");
 }


$(function(){                 
    showTree();  
  //  setDisabledNode();  

});  

$(document).ready(function() {
	
$('#jurisdictionid').treeSelecter({treeId:'roleOptions'})
    
$("#save").click(function () {

     var authid_array=new Array();  
     var treeObj = $.fn.zTree.getZTreeObj("myTree");
     var nodes = treeObj.getCheckedNodes(true);
     var id = $("#id").val();
     for (var i = 0; i < nodes.length; i++) {
         var checked = nodes[i].checked;
         if (checked) {
            authid_array.push(nodes[i].id);//向数组中添加元素
         }
     }
    //保存权限 
 	  $.ajax({
			async : false,
			url : cuurrent_host+'/role/addauth',
			data : {
				id : id,
				ary:authid_array.toString()
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.code == 200) {
        	    var dialog = jQuery.getDialog("roleDialog");
                var win = tabs.getIframeWindow(dialog.config.tabId);
                if (win && win.window.loadUi) {
                    win.window.loadUi.gridRefresh(data.message)
                }
                if (dialog) {
                    dialog.close();
                }
				} else {
					$.showConfirm({
						icon: 'warning',
						content: data.message
					});
				}
			},
			error : function() {
				alert("异常！");
				return false;
			}
		});
 });
});

function showTree(){  
	var id= $("#id").val();
	$.ajax({
			type:"get",
			async:true,
			url:cuurrent_host+"/role/role_auth?new_time="+new Date().getTime(),
			data:{id:id},
			success:function(data){
				
				var zNodes = data.data;  
                $.each(zNodes,function(key,val){  
                	
                	 if(val.isdistribution == 2){
                		 val.open = false;  
                     }else{
                    	 val.open = true;  
                     }
                });  
                //初始化栏目树对象  
                var zTreeObj = $.fn.zTree.init($("#myTree"), setting, zNodes);  
                var disabledNodes = zTreeObj.getNodesByParam("level",0);
                $.each(disabledNodes,function(i,item){
                	if(item.isdistribution == 2){
                	zTreeObj.setChkDisabled(item, true);
                	}
                })
                   var disabledNodes = zTreeObj.getNodesByParam("level",1);
                $.each(disabledNodes,function(i,item){
                	if(item.isdistribution == 2){
                	zTreeObj.setChkDisabled(item, true);
                	}
                })
                   var disabledNodes = zTreeObj.getNodesByParam("level",2);
                $.each(disabledNodes,function(i,item){
                	if(item.isdistribution == 2){
                	zTreeObj.setChkDisabled(item, true);
                	}
                })
                
                   var disabledNodes = zTreeObj.getNodesByParam("level",3);
                $.each(disabledNodes,function(i,item){
                	if(item.isdistribution == 2){
                	zTreeObj.setChkDisabled(item, true);
                	}
                })
                  
            }  
			
//		 if (data.status == 0) {
//			 var data2 = eval(data.data);
//              $.fn.zTree.init($("#myTree"), setting,data2);
//              var treeObj = $.fn.zTree.getZTreeObj("myTree");
//              var checklist=$(treeObj).find("[type=ckeckbox]");
//              alert(checklist.length)
//              setDisabledNode();
//              treeObj.expandAll(true);
//          	  
//          } else {
//              alert(res.message);
//          }
//			}
	});
	  }
	
	 //设置禁用的复选框节点  
    function setDisabledNode(){  
          var treeObj = $.fn.zTree.getZTreeObj("#myTree");
          debugger
          var disabledNode = treeObj.getNodeByParam("level", 0);  
          treeObj.setChkDisabled(disabledNode, true);     
    } 