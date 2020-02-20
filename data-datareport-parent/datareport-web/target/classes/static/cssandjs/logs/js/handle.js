var loadUi = {
	initialize: function () {
		this.startDate = $('#startDate');//开始日期
        this.endDate = $('#endDate');//结束日期
		this.grid = $('#grid');//列表
		this.see = $('.see');
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		this.startDate.datepicker({
			maxDate: '#F{$dp.$D(\'endDate\')}'
		});
		this.endDate.datepicker({
			minDate: '#F{$dp.$D(\'startDate\')}'
		});
		this.see.on('click',function(){
			var id=$(this).attr('data-id');
			$.showDialog({
				id:"operationlogDialog",
				tabId: tabs.getSelfId(window),
				title: '查看详细详细',
				url: cuurrent_host+'/operationlog/see.action?id='+id,
				width: 660,
				height: 360
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
		               url: cuurrent_host+"/role/reset.action",
		               type: 'POST',
		               dataType: 'json',
		               async: false,
		               success: function (response) {
		            	   if (response.code == 200) {
		                	   alert(response.message);
		                	   window.location.reload();
		                   }
		                   else {
		                       alert(response.message);
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
			id: 'operationlogDialog',
			title: '新增角色',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+'/role/add.action',
			width: 430,
			height: 300
		});
	},
	gridRefresh: function(text) {
		$.showConfirm({
			icon: 'succeed',
			content: text
		})
		$('#grid').gridRefresh()
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
							id: 'operationlogDialog',
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
