package com.datareport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.datareport.bean.SystemLog;
import com.datareport.common.String.StringUtil;
import com.datareport.common.date.DateUtil;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.service.ISystemLogService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/systemlog")
@Log4j
public class SystemLogController {

         @Autowired
         private ISystemLogService iSystemLogService;
	  
	    @Log(detail = "日志列表",level = 1,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
		@RequiresPermissions(value={"systemlog:list"})
	    @RequestMapping("/list")
	    public String list(SystemLog bean, HttpServletRequest request,
	                       HttpServletResponse respone, Model model) {

	        try {
	        	Integer pageNo =1;
	        	String requstPage = request.getParameter("page.currentPage");
	        	if (!StringUtil.isEmpty(requstPage)) {
	        		pageNo=Integer.valueOf(requstPage);
	        	}
	             Integer pageSize = 10;
	             IPage<SystemLog> page = new Page<>(pageNo, pageSize);
	             QueryWrapper<SystemLog> wrapper = new QueryWrapper<>();
	               String start = request.getParameter("start");
					String end = request.getParameter("end");
					
					if (StringUtil.isEmpty(start)) {
						start = DateUtil.timeStampToDate4(DateUtil.getone(),"yyyy-MM-dd");
					}
					
					if (StringUtil.isEmpty(end)) {
						end = DateUtil.timeStampToDate4(DateUtil.getlast(),"yyyy-MM-dd");
					}
					
	            //创建时间起
	             wrapper.apply(start != null,"DATE_FORMAT( create_time, '%Y-%m-%d' ) " +
	                     " >= DATE_FORMAT( '"+start+"', '%Y-%m-%d' )");
	             // 创建时间止
	             wrapper.apply(end != null,"DATE_FORMAT( create_time, '%Y-%m-%d' ) " +
	                     " <= DATE_FORMAT( '"+end+"', '%Y-%m-%d' )");
	             wrapper.orderByDesc("create_time");
	             page=iSystemLogService.page(page,wrapper);
	             com.datareport.common.page.Page pages = new com.datareport.common.page.Page();
	             pages.setShowCount(pages.getShowCount());
	             pages.setCurrentPage((int) page.getCurrent());
	             pages.setTotalResult((int) page.getTotal());
	             pages.setTotalPage((int) page.getPages());
	            model.addAttribute("bean", bean);
	            model.addAttribute("page", pages);
	            model.addAttribute("list",page.getRecords());
				model.addAttribute("start", start);
				model.addAttribute("end", end);
	            model.addAttribute("bean", bean);
	        } catch (Exception e) {

	            log.error(e.getMessage());
	        }

	        return geturl("list");

	    }
	    
	    /**
	     * @param url
	     * @return
	     * @Title: 目录
	     * @Description: TODO
	     * @return: String
	     */
	    public String geturl(String url) {
	        return "logs/" + url;
	    }
	    
}
