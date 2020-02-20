var loadUi = {
	initialize: function () {
		this.grid = $('#grid');//列表
		this.btnAdd = $('#btnAdd');
		this.btnDel = $('.btnDel');
		this.btnReset = $('.btnReset');
		this.updatestate = $('.updatestate');
		this.btnEdit=$('.fml-edit-btn');//列表修改按钮
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		this.btnAdd.on('click', $.proxy(this.btnAddClick, this));
		this.btnEdit.on('click',function(){
			var id=$(this).attr('data-id');
			$.showDialog({
				id:"editInfo",
				tabId: tabs.getSelfId(window),
				title: '修改用户',
				url: cuurrent_host+'/admin/update?id='+id,
				width: 660,
				height: 360
			});
		});
		this.btnReset.on('click',function(){
			var id=$(this).attr('data-id');
			$.showConfirm({
				icon: 'confirm',
				content: '确认重置该用户密码吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/admin/resetpassword",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id
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
		this.btnDel.on('click',function(){
			var id=$(this).attr('data-id');
			$.showConfirm({
				icon: 'confirm',
				content: '确定要删除该用户吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/admin/del",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id
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
		
		this.updatestate.on('click',function(){
			var id=$(this).attr('data-id');
			var state=$(this).attr('data-state');
			var msg=null;
			if(state==1){
				msg="确定要启用该账号么？";
			}else{
				msg="确定要禁用用该账号么？";
			}
			$.showConfirm({
				icon: 'confirm',
				content: msg,
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/admin/updatestate.action",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id,
			   				'state':state
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
		var result = $('#asd').getSelectedRows();//当前选中行的所有数据   
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
				$('#grid').gridRefresh()

			}
		});


	},
	btnAddClick: function () {
		$.showDialog({
			id: 'editInfo',
			title: '新增用户',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+'/admin/add',
			width: 660,
			height: 300
		});
	},
	gridRefresh: function (text) {
		$.showConfirm({
			icon: 'succeed',
			content: text
		})
		 win.window.location.reload();
	},
	gxdwTreeOption: {
		treeId: 'treeClassification'},
	gridOption: {
		multiSelect: true,
		columns: [
			{ field: 'name', width: 150, display: '用户名', style: "asd" },
			{ field: 'province_name', width: 100, display: '省', style: "asd" },
			{ field: 'city_name', width: 100, display: '市', style: "asd" },
			{ field: 'district_name', width: 150, display: '县区', style: "asd" },
			{ field: 'road_name', width: 150, display: '街道', style: "asd" },
			{ field: 'torgan.orgname', width: 150, display: '所属管辖单位', style: "asd" },
			{ field: 'role.name', width: 150, display: '角色', style: "asd" }
		],
		action: {
			width: 320,
			align: 'left',
			display: '操作',
			buttons: [
				{
					display: '修改',
					btnClass: 'ui-button fml-edit-btn',
					onClick: function (data) {
						$.showDialog({
							id: 'editInfo',
							tabId: tabs.getSelfId(window),
							title: '修改用户',
							url: cuurrent_host+'/admin/update.action',
							width: 660,
							height: 360
						});
					}
				},
				{
					display: '启用',
					btnClass: 'ui-button fml-show-btn',
					isHidden: function (row) {
						if (row.id == 1) {
							return false;
						}
						return true;
					},
					onClick: function (data) {
						$.showConfirm({
							icon: 'confirm',
							cancel: true,
							content: '确认启用该用户？',
							ok: function () {
								alert('确定');
								$('#grid').gridRefresh()

							}
						});
					}
				},
				{
					display: '禁用',
					btnClass: 'ui-button fml-show-btn',
					isHidden: function (row) {
						if (row.id == 2) {
							return false;
						}
						return true;
					},
					onClick: function (data) {
						$.showConfirm({
							icon: 'confirm',
							cancel: true,
							content: '确认禁用该用户？',
							ok: function () {
								alert('确定');
								$('#grid').gridRefresh()

							}
						});
					}
				},
				{
					display: '重置密码',
					btnClass: 'ui-button fml-show-btn',
					onClick: function (data) {
						$.showConfirm({
							cancel: true,
							icon: 'confirm',
							content: '确认重置该用户密码吗？',
							ok: function () {
								alert('确定');
								$('#grid').gridRefresh()

							}
						});
					}
				},
				{
					display: '分配角色',
					btnClass: 'ui-button fml-show-btn',
					onClick: function (data) {
						$.showDialog({
							tabId: tabs.getSelfId(window),
							id: 'roleDialog',
							title: '分配角色',
							url: '/单点登录管理平台/用户管理/vm/分配角色.html',
							width: 460,
							height: 200
						});
					}
				}
			]
		},
		url: cuurrent_host+'/admin/list.action',
		dataType: 'json',
		usePage: true,
		pageCross: true,
		type : "POST",  
		extendCell: {
			// 列操作
//			sex: function (div, val, data) {
//				val = val == 0 ? '男' : '女';
//				div.innerHTML = val;
//			}
		},
		onRowDblclick: function (data) {
			console.log(data);
		}
	}

};
$(function () {
	$('select').selecter();
	loadUi.initialize();
});
