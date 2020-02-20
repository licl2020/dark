var loadUi = {
	initialize: function () {
		//this.btnSearch = $("#btnSearch");//搜索
		this.grid = $('#grid');//列表
		this.btnAdd = $('#btnAdd');
		this.btnDel = $('.btnDel');
		this.gxdwTree = $('#gxdwTree');
		this.refResh = $('#refResh');
		this.btnEdit=$('.fml-edit-btn');//列表修改按钮
		this.see=$('.see');//
		this.open=$('.open');//
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
				id:"areaInfo",
				tabId: tabs.getSelfId(window),
				title: '修改区域信息',
				url: cuurrent_host+'/area/update.action?id='+id,
				width: 660,
				height: 360
			});
		});
		this.see.on('click',function(){
			var id=$(this).attr('data-id');
			var width=document.body.clientWidth;
			var height=document.body.clientHeight-80;
			$.showDialog({
				id:"areaInfo",
				tabId: tabs.getSelfId(window),
				title: '查看',
				url: cuurrent_host+'/area/see.action?id='+id,
				width: width,
				height: height
			});
		});
		this.btnDel.on('click',function(){
			var id=$(this).attr('data-id');
			var parentid=$(this).attr('data-mParentId');
			$.showConfirm({
				icon: 'confirm',
				content: '确定要删除该区域吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/area/del.action",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id,
			   				'parentid':parentid
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
		
		this.open.on('click',function(){
			var id=$(this).attr('data-id');
			var open_state=$(this).attr('data-open-state');
			$.showConfirm({
				icon: 'confirm',
				content: '确认当前操作吗？',
				cancel: true,
				ok: function () {
					that=this;
				     $.ajax({
			               url: cuurrent_host+"/area/open.action",
			               type: 'POST',
			               dataType: 'json',
			               data : {
			   				'id' : id,
			   				'isitopen':open_state
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
	},refReshClick: function () {
		$.showConfirm({
			icon: 'confirm',
			content: '确定要应用吗？',
			cancel: true,
			ok: function () {
			    $.ajax({
		               url: cuurrent_host+"/area/reset.action",
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
				alert('你点击了确定')
				$('#grid').gridRefresh()

			}
		});


	},
	btnAddClick: function () {
		$.showDialog({
			id: 'areaInfo',
			title: '新增区域',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+'/area/add.action',
			width: 660,
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
	gxdwTreeOption: {
		treeId: 'treeClassification'
	},
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
							id: 'areaInfo',
							tabId: tabs.getSelfId(window),
							title: '修改用户',
							url: cuurrent_host+'/area/update.action',
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
		url: cuurrent_host+'/area/list.action',
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
