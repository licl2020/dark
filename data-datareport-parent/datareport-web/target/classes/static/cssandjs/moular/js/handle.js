var loadUi = {
	initialize: function () {
		//this.btnSearch = $("#btnSearch");//搜索
		this.grid = $('#grid');//列表
		this.btnAdd = $('#btnAdd');
		this.btnDel = $('.btnDel');
		this.refResh = $('#refResh');
		this.btnEdit=$('.fml-edit-btn');
		this.gxdwTree=$('#gxdwTree');
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		this.btnAdd.on('click', $.proxy(this.btnAddClick, this));
		this.refResh.on('click', $.proxy(this.refReshClick, this));
		this.gxdwTree.treeSelecter(this.gxdwTreeOption)
		this.btnEdit.on('click',function(){
			var id=$(this).attr('data-id');
			$.showDialog({
				id:"perDialog",
				tabId: tabs.getSelfId(window),
				title: '修改权限',
				url: cuurrent_host+'/modular/update?id='+id,
				width: 660,
				height: 360
			});
		});
		
		this.btnDel.on('click',function(){
			var id=$(this).attr('data-id');
			var mParentId=$(this).attr('data-mParentId');
			$.showConfirm({
				icon: 'confirm',
				content: '确定要删除该权限吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/modular/del",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id,
			   				'mParentId':mParentId
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
			});
		});
	},
	btnSearchClick: function () {
		var Json = getFormJson("#form");
		$('#grid').gridSearch(Json[0])
	},
	btnDelClick: function () {
		var result = $('#grid').getSelectedRows();//当前选中行的所有数据   
		if (!result || result.length == 0) {
			$.showConfirm({
				icon: 'warning',
				content: '请选择要删除的数据'
			});
			return
		}
		$.showConfirm({
			icon: 'confirm',
			content: '确定要删除所选数据吗？',
			cancel: true,
			ok: function () {
				alert('你点击了确定')
				$('#grid').gridRefresh()

			}
		});


	},refReshClick: function () {
		$.showConfirm({
			icon: 'confirm',
			content: '确定要应用吗？',
			cancel: true,
			ok: function () {
			    $.ajax({
		               url: cuurrent_host+"/modular/reset",
		               type: 'GET',
		               dataType: 'json',
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
		});


	},
	btnAddClick: function () {
		$.showDialog({
			id: 'perDialog',
			title: '新增权限',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+"/modular/add",
			width: 660,
			height: 450
		});
	},
	gridRefresh: function(text) {
		$.showConfirm({
			icon: 'succeed',
			content: text
		})
		 win.window.location.reload();
	},
	perOption: {
		treeId: 'permissionTree',
		nodes: [
			{ id: 1, pId: 0, name: '1333', open: true },
			{ id: 11, pId: 1, name: '11' },
			{ id: 12, pId: 1, name: '12' },
			{ id: 2, pId: 0, name: '2', open: true },
			{ id: 21, pId: 2, name: '21' },
			{ id: 23, pId: 21, name: '211' },
			{ id: 22, pId: 2, name: '22' }
		],
		onClick: function (e, treeId, node, flag) {
			$('#perTree').val(node.name).focus();
			$('#readyPer').val(node.id)
		}
	},
	gxdwTreeOption: {
		treeId: 'treeClassification'
	},
	gridOption: {
		multiSelect: true,
		columns: [
			{ field: 'name', width: 150, display: '权限名称', style: "asd" },
			{ field: 'sex', width: 150, display: '控制器', style: "asd" },
			{ field: 'sex', width: 150, display: '方法', style: "asd" },
			{ field: 'sex', width: 150, display: '排序', style: "asd" },
			{ field: 'sex', width: 150, display: '验证', style: "asd" },
			{ field: 'sex', width: 150, display: '参数', style: "asd" },
			{ field: 'sex', width: 150, display: '功能类型', style: "asd" },
			{ field: 'sex', width: 150, display: '是否可分配', style: "asd" }
		],
		action: {
			width: 100,
			align: 'center',
			display: '操作',
			buttons: [
				{
					display: '修改',
					btnClass: 'ui-button fml-edit-btn',
					onClick: function (data) {
						$.showDialog({
							id: 'perDialog',
							tabId: tabs.getSelfId(window),
							title: '修改权限',
							url: '/单点登录管理平台/权限管理/vm/修改权限.html',
							width: 660,
							height: 450
						});
					}
				}
			]
		},
		url: 'data/datagrid.txt',
		dataType: 'json',
		usePage: true,
		pageCross: true,
		extendCell: {
			// 列操作
			sex: function (div, val, data) {
				val = val == 0 ? '男' : '女';
				div.innerHTML = val;
			}
		}
	}

};
$(function () {
	loadUi.initialize();
});
