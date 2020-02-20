var initAuth = function (click, expandAll) {
        var setting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: false
            },
            async: {
                enable: true,
                type: "get",
                url: cuurrent_host+"/modular/childs",
                autoParam: ["parentId"],
                dataType: "json",
                dataFilter: ajaxDataFilter
            },
            callback: {
                beforeClick: beforeClick
            }
        };

        function ajaxDataFilter(treeId, parentNode, responseData) {
            if (responseData.status == 0) {
                childNodes = responseData.data;
                if (!childNodes) return null;
                for (var i = 0, l = childNodes.length; i < l; i++) {
                	childNodes[i].parentId =  childNodes[i].id;
                    childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                }
                return childNodes;
            }
        }
        function beforeClick(treeId, treeNode) {
            click(treeNode);
        }

        $(document).ready(function () {
        	event.preventDefault();
            $.fn.zTree.init($("#myTree"), setting).expandAll(expandAll);;
            //var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //treeObj.expandAll(true);
            $('.ui-treeselecter-options').on('click',function(e){
            	console.log(9)
            	e.stopPropagation() 
            	
            })
        });
    }



var inittype = function (click, expandAll) {
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: false
        },
        async: {
            enable: true,
            type: "get",
            url: cuurrent_host+"/app/childs.action",
            autoParam: ["mParentId"],
            dataType: "json",
            dataFilter: ajaxDataFilter
        },
        callback: {
            beforeClick: beforeClick
        }
    };

    function ajaxDataFilter(treeId, parentNode, responseData) {
        if (responseData.status == 0) {
            childNodes = responseData.data;
            if (!childNodes) return null;
            for (var i = 0, l = childNodes.length; i < l; i++) {
            	childNodes[i].mParentId =  childNodes[i].id;
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }
    }
    function beforeClick(treeId, treeNode) {
        click(treeNode);
    }

    $(document).ready(function () {
        $.fn.zTree.init($("#myTree"), setting).expandAll(expandAll);;
        //var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //treeObj.expandAll(true);
        $('.ui-treeselecter-options').on('click',function(e){
        	console.log(9)
        	e.stopPropagation() 
        	
        })
    });
}


var initArea = function (click, expandAll) {
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: false
        },
        async: {
            enable: true,
            type: "get",
            url: cuurrent_host+"/area/childs.action",
            autoParam: ["parentid"],
            dataType: "json",
            dataFilter: ajaxDataFilter
        },
        callback: {
            beforeClick: beforeClick
        }
    };

    function ajaxDataFilter(treeId, parentNode, responseData) {
        if (responseData.status == 0) {
            childNodes = responseData.data;
            if (!childNodes) return null;
            for (var i = 0, l = childNodes.length; i < l; i++) {
            	childNodes[i].parentid =  childNodes[i].id;
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                childNodes[i].jibie = childNodes[i].level;
            }
            return childNodes;
        }
    }
    function beforeClick(treeId, treeNode) {
        if(treeNode.parentc == 1){
            click(treeNode);
        }else{
        	alert("已经是最后一级了");
        }
    }

    $(document).ready(function () {
        $.fn.zTree.init($("#myTree"), setting).expandAll(expandAll);;
        //var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //treeObj.expandAll(true);
        $('.ui-treeselecter-options').on('click',function(e){
        	console.log(9)
        	e.stopPropagation() 
        	
        })
        
    });
}
     
    
    
    var initTOrgans = function (click, expandAll) {
    	
    	
        var setting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: false
            },
            async: {
                enable: true,
                type: "get",
                url: cuurrent_host+"/torgan/childs.action",
                autoParam: ["uporgid"],
                dataType: "json",
                dataFilter: ajaxDataFilter
            },
            callback: {
                beforeClick: beforeClick
            }
        };

        function ajaxDataFilter(treeId, parentNode, responseData) {
            if (responseData.status == 0) {
                childNodes = responseData.data;
                if (!childNodes) return null;
                for (var i = 0, l = childNodes.length; i < l; i++) {
                	childNodes[i].id =  childNodes[i].orgid;
                	childNodes[i].uporgid =  childNodes[i].id;
                	childNodes[i].type =  childNodes[i].type;
                    childNodes[i].name = childNodes[i].orgname.replace(/\.n/g, '.');
                }
                return childNodes;
            }
        }
        function beforeClick(treeId, treeNode) {
        	 click(treeNode);
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#myTree"), setting).expandAll(expandAll);;
            //var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //treeObj.expandAll(true);

            $('.ui-treeselecter-options').on('click',function(e){
            	console.log(9)
            	e.stopPropagation() 
            	
            })
        });
    }
    
    
 

    
