$(document).ready(function(){
	
	//righHandButton.init({preventDoublerighHandButton: false});
	
	righHandButton.attach('.inline-menu', [
		{header: 'Options'},
		{text: 'Open', href: '#'},
		{text: 'Open in new Window', href: '#'},
		{divider: true},
		{text: 'Copy', href: '#'},
		{text: 'Dafuq!?', href: '#'}
	]);
	
	righHandButton.attach('#download', [
		
		{header: 'Download'},
		{text: 'The Script', subMenu: [
			{header: 'Requires jQuery'},
			{text: 'righHandButton.js', href: 'http://righHandButtonjs.com/righHandButton.js', target:'_blank', action: function(e){
				_gaq.push(['_trackEvent', 'righHandButtonJS Download', this.pathname, this.innerHTML]);
			}}
		]},
		{text: 'The Styles', subMenu: [
		
			{text: 'righHandButton.bootstrap.css', href: 'http://righHandButtonjs.com/righHandButton.bootstrap.css', target:'_blank', action: function(e){
				_gaq.push(['_trackEvent', 'righHandButtonJS Bootstrap CSS Download', this.pathname, this.innerHTML]);
			}},
			
			{text: 'righHandButton.standalone.css', href: 'http://righHandButtonjs.com/righHandButton.standalone.css', target:'_blank', action: function(e){
				_gaq.push(['_trackEvent', 'righHandButtonJS Standalone CSS Download', this.pathname, this.innerHTML]);
			}}
		]},
		{divider: true},
		{header: 'Meta'},
		{text: 'The Author', subMenu: [
			{header: '@jakiestfu'},
			{text: 'Website', href: 'http://jakiestfu.com/', target: '_blank'},
			{text: 'Forrst', href: 'http://forrst.com/people/jakiestfu', target: '_blank'},
			{text: 'Twitter', href: 'http://twitter.com/jakiestfu', target: '_blank'},
			{text: 'Donate?', action: function(e){
				e.preventDefault();
				$('#donate').submit();
			}}
		]},
		{text: 'Hmm?', subMenu: [
			{header: 'Well, thats lovely.'},
			{text: '2nd Level', subMenu: [
				{header: 'You like?'},
				{text: '3rd Level!?', subMenu: [
					{header: 'Of course you do'},
					{text: 'MENUCEPTION', subMenu: [
						{header:'FUCK'},
						{text: 'MAKE IT STOP!', subMenu: [
							{header: 'NEVAH!'},
							{text: 'Shieeet', subMenu: [
								{header: 'WIN'},
								{text: 'Dont Click Me', href: 'http://omglilwayne.com/', target:'_blank', action: function(){
									_gaq.push(['_trackEvent', 'righHandButtonJS Weezy Click', this.pathname, this.innerHTML]);
								}}
							]}
						]}
					]}
				]}
			]}
		]}
	]);
	
	righHandButton.settings({compress: true});
	
	righHandButton.attach('html', [
		{header: 'Compressed Menu'},
		{text: 'Back', href: '#'},
		{text: 'Reload', href: '#'},
		{divider: true},
		{text: 'Save As', href: '#'},
		{text: 'Print', href: '#'},
		{text: 'View Page Source', href: '#'},
		{text: 'View Page Info', href: '#'},
		{divider: true},
		{text: 'Inspect Element', href: '#'},
		{divider: true},
		{text: 'Disable This Menu', action: function(e){
			e.preventDefault();
			righHandButton.destroy('html');
			alert('html righHandButtonual menu destroyed!');
		}},
		{text: 'Donate?', action: function(e){
			e.preventDefault();
			$('#donate').submit();
		}}
	]);
	
	
	$(document).on('mouseover', '.me-codesta', function(){
		$('.finale h1:first').css({opacity:0});
		$('.finale h1:last').css({opacity:1});
	});
	
	$(document).on('mouseout', '.me-codesta', function(){
		$('.finale h1:last').css({opacity:0});
		$('.finale h1:first').css({opacity:1});
	});
	
});