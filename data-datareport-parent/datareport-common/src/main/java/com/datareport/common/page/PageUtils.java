package com.datareport.common.page;






import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;


/**
 * 分页参数
 * @ClassName: PageUtils
 * @Description: TODO
 * @author: Licl
 * @date: 2017年7月4日 下午8:28:10
 */
public class PageUtils {
	private static int pageShowNum = 10;

	/**
	 * 获取分页参数
	 * @param o
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 */
	public static Page getPage(HttpServletRequest request) throws Exception {
		String requstPage = request.getParameter("page.currentPage");
		int pageCurrent = 1;
		if (!StringUtils.isEmpty(requstPage)) {
			pageCurrent = Integer.parseInt(requstPage);
			if (pageCurrent == 0) {
				pageCurrent = 1;
			}
		}
		Page page = new Page();
		page.setShowCount(pageShowNum);
		page.setCurrentPage(pageCurrent);
		return page;
	}

	public static int getPageShowNum() {
		return pageShowNum;
	}

	public static void setPageShowNum(int pageShowNum) {
		PageUtils.pageShowNum = pageShowNum;
	}
	
	
	
	
}
