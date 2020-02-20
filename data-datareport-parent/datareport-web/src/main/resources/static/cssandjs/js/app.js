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
			url : cuurrent_host+'/model/addapp.action',
			data : {
                areaId : id,
				ary:authid_array.toString()
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				 if (data.code == 200) {
					alert(data.message);
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	                    parent.layer.closeAll(index);
	                    window.parent.location.reload(); //刷新父页面
				} else {
					alert(data.message);
					return false;
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
			url:cuurrent_host+"/model/app_auth.action",
			data:{areaId:id},
			success:function(data){
				
				 if (data.status == 0) {
				 var data2 = eval(data.data);
	              $.fn.zTree.init($("#myTree"), setting,data2);
	              var treeObj = $.fn.zTree.getZTreeObj("myTree");
	              var checklist=$(treeObj).find("[type=ckeckbox]");
	              treeObj.expandAll(true);
	          	  
	          } else {
	              alert(res.message);
	          }
				}

	});
	  }
	
