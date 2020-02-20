var loadUi = {
	initialize: function () {
		this.startDate = $('#startDate');//开始日期
        this.endDate = $('#endDate');//结束日期
		this.grid = $('#grid');//列表
		this.uploadExcel = $('#uploadExcel');
		this.zdrkDetails = $('.zdrkDetails');
		this.see = $('.see');
		this.cxfs = $('.cxfs');
		this.ANIMATE_TIME = 5000;
		this.initEvent();
	},
	initEvent: function () {
		this.uploadExcel.on('click', $.proxy(this.uploadExcelClick, this));
		this.startDate.datepicker({
			maxDate: '#F{$dp.$D(\'endDate\')}'
		});
		this.endDate.datepicker({
			minDate: '#F{$dp.$D(\'startDate\')}'
		});
		
		this.zdrkDetails.on('click',function(){
	            var reportId = $(this).attr("data-id");
			$.showDialog({
				id:"hotelguestinfo",
				tabId: tabs.getSelfId(window),
				title: '上报详情',
				url: cuurrent_host+'/zdrk/zdrkDataList?reportId='+reportId,
				width: 1350,
				height: 520
			});
		});
		
		this.see.on('click',function(){
            var id = $(this).attr("data-id");
		$.showDialog({
			id:"see",
			tabId: tabs.getSelfId(window),
			title: '查看发送详情',
			url: cuurrent_host+'/zdrk/seeSendRecordErrContent?id='+id,
			width: 800,
			height: 400
		});
	});
		

		this.cxfs.on('click',function(){
			var id=$(this).attr('data-id');
			$.showConfirm({
				icon: 'confirm',
				content: '确定要重新发送吗？',
				cancel: true,
				ok: function () {
				     $.ajax({
			               url: cuurrent_host+"/zdrk/resend",
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
			            				content: resp.describe
			            			})
			            			  win.window.location.reload();
                				} else {
                					$.showConfirm({
                						icon: 'warning',
                						content: resp.describe
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
	uploadExcelClick: function () {
		$.showDialog({
			id: 'zdrksc',
			title: '重点人口数据上传',
			tabId: tabs.getSelfId(window),
			url: cuurrent_host+'/zdrk/uploadExceladd',
			width: 660,
			height: 300
		});
	},
	btnSearchClick: function () {
		var Json = getFormJson("#form");
		$('#grid').gridSearch(Json[0])
	},
	gxdwTreeOption:{
		treeId: 'treeClassification'
	},
	gridRefresh: function (text) {
		$.showConfirm({
			icon: 'succeed',
			content: text
		})
		 win.window.location.reload();
	}
	,
	
	gridOption: {
		columns: [
			{ field: 'name', width: 150, display: '房间号' },
			{ field: 'sex', width: 150, display: '所属网约房' },
			{ field: 'name5', width: 200, display: '管辖单位名称', },
			{ field: 'sex5', width: 200, display: '省' },
			{ field: 'name6', width: 150, display: '市' },
			{ field: 'name6', width: 150, display: '县区' },
			{ field: 'name6', width: 150, display: '街道' },
			{ field: 'name6', width: 150, display: '地址' },
			{ field: 'name6', width: 150, display: '床位数' },
			{ field: 'name6', width: 150, display: '房间数' },
			{ field: 'name6', width: 150, display: '是否安装门锁' },
			{ field: 'name6', width: 150, display: '门锁编号' }
		],
		action: {
			width: 180,
			align: 'center',
			display: '操作',
			buttons: [
				{
					display: '门锁信息',
					btnClass: 'ui-button fml-edit-btn',
					isDisabled: function(row){
						if(row.id == 1){
							return true;
						}
						return false;
					},
					onClick: function (data) {
						$.showDialog({
							id: 'doorInfo',
							title: '门锁信息',
							url: '/网约房数据统计管理平台/房屋信息库/vm/门锁信息.html',
							width: 860,
							height: 360
						});
					}
				},
				{
					display: '定位校准',
					btnClass: 'ui-button fml-show-btn',
					onClick: function (data) {
						$.showDialog({
							id: 'locationCheck',
							title: '定位校准',
							url: '/网约房数据统计管理平台/房屋信息库/vm/定位校准.html',
							width: '100%',
							height: '100%'
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
		},
		onRowDblclick: function (data) {
			console.log(data);
		}
	}

};
$(function () {
	loadUi.initialize();
});
