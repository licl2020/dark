
// 声明菜单命名空间
Namespace.register('index.menu');
(function (menu) {
	// 初始化菜单
	menu.init = function (selector, data) {
		var html = [];
		for (var i = 0, len1 = data.length; i < len1; i++) {
			var p = data[i], color = i % 8;
			if (p) {
				html.push('<dt id="' + p.id + '">');
				html.push('<i class="icon c' + color + '"></i>');
				html.push('<span>' + p.display + '</span>');
				html.push('</dt>');
				html.push('<dd>');
				html.push('<ul>');
				if (p.child) {
					for (var j = 0, len2 = p.child.length; j < len2; j++) {
						var c = p.child[j];
						if (c) {
							html.push('<li id="' + c.id + '" data-parent="' + i + '" data-child="' + j + '"><span>' + c.display + '</span></li>');
						}
					}
				}
				html.push('</ul>');
				html.push('</dd>');
			}
		}

		this.data = data;

		this.container = jQuery(selector);
		this.container.append(html.join(''));

		this.addEvents();
	};
	// 添加菜单事件
	menu.addEvents = function () {
		var _this = this;

		_this.container
			// 父菜单点击事件
			.on('click.dt', 'dt', function () {
				var dt = jQuery(this), dd = dt.next(), cls = 'active', tab = dt.attr('data-tab');
				if (tab) {
					var tabJson = JSON.parse(tab);
					if (!tabJson.url) return
					dt.addClass(cls).siblings('dt').removeClass(cls);
					tabs.add(tabJson)
					return
				}
				if (dd && dd.is(':visible')) {
					dt.removeClass(cls);
					dd.removeClass(cls);
				} else if (dd) {
					dt.addClass(cls).siblings('dt').removeClass(cls);
					dd.addClass(cls).siblings('dd').removeClass(cls);
				}
			})
			// 子菜单点击事件
			.on('click.li', 'li', function () {
				var li = jQuery(this), cls = 'active';
				_this.container.find('li').removeClass(cls);
				li.addClass(cls);

				if (_this.data) {
					var i = li.attr('data-parent'), j = li.attr('data-child'), tab = li.attr('data-tab'), p = _this.data[i];
					// if(p && p.child && p.child[j] && p.child[j].tab){
					// 	tabs.add(p.child[j].tab);
					// }
					if (tab) {
						var tabJson = JSON.parse(tab);
						if (!tabJson.url) return
						tabs.add(tabJson)
					}
				}
			});
	};
})(index.menu);

// 声明页卡对象
var tabs = new Tabs();

// 页面加载完成时
$(function () {
	// 加载菜单
	index.menu.init('#menu', [
		// {
		// 	id: '_menuParent01',
		// 	display: '账号管理',
		// 	child: [
		// 		{ id: '_menuChild01', display: '医院联系人账号', tab: { id: '_tab01', pid: '', url: '../../账号管理/vm/医院联系人账号.html', display: '医院联系人账号' } },
		// 		{ id: '_menuChild02', display: '面试官账号', tab: { id: '_tab02', pid: '', url: '../../账号管理/vm/面试官账号.html', display: '面试官账号' } },
		// 	]
		// }
	]);

	$('#btnSideToggle').click(function () {
		var cls = 'toggle-on';
		if ($(this).hasClass(cls)) {
			$(this).removeClass(cls);
			$('#side').css('left', 0);
			$('#main').css('margin-left', 200);
		} else {
			$(this).addClass(cls);
			$('#side').css('left', -200);
			$('#main').css('margin-left', 0);
		}
	});

	// 加载工作台页卡
	tabs.add({ id: 'desk', pid: '', url: cuurrent_host+'/indexcontent', lock: true, reloadOnClick: true, display: '首页' });



	$('#nav').click(function (e) {
		$('#drop-menu').fadeIn()
		e.stopPropagation()
	});
	$(document).click(function (e) {
		if (!$('#drop-menu').is(e.target) && $('#drop-menu').has(e.target).length === 0) {
			$('#drop-menu').stop(true, true).fadeOut();
		}

	});
	$('#drop-menu').hover(function(){
		$('#drop-menu').fadeIn()
	},function(){
		$('#drop-menu').stop(true, true).fadeOut();
	})
	$('#drop-menu li').on('click',function(){
		var state=$(this).attr('data-state');//依次为 1，2，3，4
		if(state==1){
		$.showDialog({
			id: 'infoDialog',
			tabId: tabs.getSelfId(window),
			title: '个人信息',
			url: cuurrent_host+'/admin/personalinformation.action',
			width: 460,
			height: 300
		});
		}
		if(state==2){
			$.showDialog({
				id: 'infoDialog',
				tabId: tabs.getSelfId(window),
				title: '修改密码',
				url: cuurrent_host+'/admin/updatepassword.action',
				width: 460,
				height: 300
			});
			}
		if(state==3){
	        //此处请求后台程序，下方是成功后的前台处理……
	        location.href=cuurrent_host+"/landingpoint/selectsystem.action";
			}
		if(state==4){
	        //此处请求后台程序，下方是成功后的前台处理……
	        location.href=cuurrent_host+"/exit";
			}
	})



	if (Number(core.isIe) < 8) {
		$("body").off('mouseover.text').on('mouseover.text', '.tabs-head li', function () {
			$(this).addClass('hover');
		})
			.off('mouseout.text').on('mouseout.text', '.tabs-head li', function () {
				$(this).removeClass('hover');
			})
	}
	$("#nav .divider").click(function (e) {
		e.stopPropagation();
	});





	if (core.isIe <= 7) {
		$(window).resize(function (e) {
			var height = $(this).height();
			$("#body").height(height - 82);
			$("#main").height(height - 82);
			$("#menu").height(height - 174);
			$("#tabs-body").height(height - 123);
		}).resize();
	}

});

function cLose() {
	if (core.isIe >= 7) {//IE7以上  
		//alert('is ie 7+');  
		window.open('', '_top');
		window.top.close();
		return;
	}
	if (core.isIe == 6) {//IE6   
		//alert('is ie 6-');  
		window.opener = null;
		window.close();
		return;
	}

	if (core.isChrome) { //Chrome
		window.open(location, '_self');
		window.close();
	}
	if (core.isFirefox) {//Chrome
		if (!window.close()) {
			alert('您的‘火狐’浏览器未开通此权限')
		} else {
			window.open(location, '_self');
			window.close();
		}
	}
}


