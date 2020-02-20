var loadUi = {
	initialize: function () {
		//this.btnSearch = $("#btnSearch");//搜索
		this.grid = $('#grid');//列表
		this.btnAdd = $('#btnAdd');
		this.btnDel = $('.btnDel');
		this.refResh = $('#refResh');
		this.btnEdit=$('.fml-edit-btn');//列表修改按钮
		this.fenpeiAuth = $('.fenpeiauth');
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		//this.btnSearch.on('click', $.proxy(this.btnSearchClick, this));
		this.btnAdd.on('click', $.proxy(this.btnAddClick, this));
		//this.btnDel.on('click', $.proxy(this.btnDelClick, this));
		this.refResh.on('click', $.proxy(this.refReshClick, this));
		this.fenpeiAuth.on('click', $.proxy(this.fenpeiAuthClick, this));
		//this.grid.datagrid(this.gridOption)
		
		this.btnEdit.on('click',function(){
			var id=$(this).attr('data-id');
			$.showDialog({
				id:"roleDialog",
				tabId: tabs.getSelfId(window),
				title: '修改角色信息',
				url: cuurrent_host+'/role/update?id='+id,
				width: 660,
				height: 360
			});
		});
		
		this.fenpeiAuth.on('click',function(){
			var id=$(this).attr('data-id');
			$.showDialog({
				id:"roleDialog",
				tabId: tabs.getSelfId(window),
				title: '分配角色权限',
				url: cuurrent_host+'/role/rolelist?id='+id,
				width: 660,
				height: 360
			});
		});
		
		
		this.btnDel.on('click',function(){
			var id=$(this).attr('data-id');
			$.showConfirm({
				icon: 'confirm',
				content: '确定要删除该角色吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/role/del",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id
			   			   },
			               async: false,
			               success: function (resp) {
			            		if (resp.code == 200) {
			            			$.showConfirm({
			            				icon: 'succeed',
			            				content: resp.message
			            			})
			            			  win.window.location.reload();
                				} else {
                					$.showConfirm({
                						icon: 'warning',
                						content: resp.message
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
	},refReshClick: function () {
		$.showConfirm({
			icon: 'confirm',
			content: '确定要应用吗？',
			cancel: true,
			ok: function () {
			    $.ajax({
		               url: cuurrent_host+"/role/reset",
		               type: 'POST',
		               dataType: 'json',
		               async: false,
		               success: function (resp) {
		            		if (resp.code == 200) {
		            			$.showConfirm({
		            				icon: 'succeed',
		            				content: resp.message
		            			})
		            			  win.window.location.reload();
            				} else {
            					$.showConfirm({
            						icon: 'warning',
            						content: resp.message
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
			id: 'roleDialog',
			title: '新增角色',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+'/role/add',
			width: 430,
			height: 300
		});
	},
	gridRefresh: function(text) {
		$.showConfirm({
			icon: 'succeed',
			content: text
		})
		  win.window.location.reload();
	},
	gridOption: {
		multiSelect: true,
		columns: [
			{ field: 'name', width: 150, display: '角色名称', style: "asd" }
		],
		action: {
			width: 180,
			align: 'left',
			display: '操作',
			buttons: [
				{
					display: '修改',
					btnClass: 'ui-button fml-edit-btn',
					onClick: function (data) {
						$.showDialog({
							id: 'roleDialog',
							tabId: tabs.getSelfId(window),
							title: '修改角色',
							url: '/单点登录管理平台/角色管理/vm/修改角色.html',
							width: 430,
							height: 300
						});
					}
				},
				{
					display: '分配权限',
					btnClass: 'ui-button fml-show-btn',
					isDisabled: function (row) {
						if (row.id == 1) {
							return true
						}
						return false;
					},
					onClick: function (data) {
						$.showDialog({
							id: 'perDialog',
							tabId: tabs.getSelfId(window),
							title: '分配权限',
							url: '/单点登录管理平台/角色管理/vm/分配权限.html',
							width: 430,
							height: 300
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
