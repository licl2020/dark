package com.datareport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.datareport.bean.Admin;
import com.datareport.bean.CommonRole;
import com.datareport.common.String.StringUtil;
import com.datareport.common.http.HttpServletUtil;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.msg.ResultTools;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.service.AdminService;
import com.datareport.service.CommonRoleService;
import com.datareport.validation.AdminAddParmValidator;
import com.datareport.validation.AdminUpdateParmValidator;
import com.datareport.validator.VailUtils;

import lombok.extern.log4j.Log4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Controller
@RequestMapping("/admin")
@Log4j
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CommonRoleService commonRoleService;


	/**
	 * 用户列表
	 *
	 * @param ReportBean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Title: list
	 * @Description: TODO
	 * @return: String
	 */
	@Log(detail = "用户列表", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "admin:list" })
	@RequestMapping("/list")
	public String list(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model) {

		try {

			Integer pageNo =1;
        	String requstPage = request.getParameter("page.currentPage");
        	if (!StringUtil.isEmpty(requstPage)) {
        		pageNo=Integer.valueOf(requstPage);
        	}
			Integer pageSize = 10;
			IPage<Admin> page = new Page<>(pageNo, pageSize);
			QueryWrapper<Admin> wrapper = new QueryWrapper<>();
			wrapper.eq("a_max", "2").ne("state", 3);
			page = adminService.page(page, wrapper);
			com.datareport.common.page.Page pages = new com.datareport.common.page.Page();
			pages.setShowCount(pages.getShowCount());
			pages.setCurrentPage((int) page.getCurrent());
			pages.setTotalResult((int) page.getTotal());
			pages.setTotalPage((int) page.getPages());
			model.addAttribute("bean", bean);
			model.addAttribute("page", pages);
			model.addAttribute("list", page.getRecords());
		} catch (Exception e) {
			log.info(e);
		}

		return geturl("list");

	}

	/**
	 * 修改账户状态
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatestate")
	@ResponseBody
	@Log(detail = "修改账户状态", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "admin:updatestate" })
	public ResultTools updatestate(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		
			return adminService.updateAdminState(bean);

	}

	/**
	 * 添加管理员
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@Log(detail = "添加用户", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
	@RequiresPermissions(value = { "admin:add" })
	public String add(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
			//数据校验
			VailUtils.validate(bean, AdminAddParmValidator.class);
			HttpServletUtil.writeJson(JsonUtils.objectToJson(adminService.addAdmin(bean)), respone);
		}
		QueryWrapper<CommonRole> wrapper = new QueryWrapper<>();
		wrapper.eq("IS_HANDLER", 2);
		model.addAttribute("roleList", commonRoleService.list(wrapper));
		model.addAttribute("bean", bean);
		return geturl("add");
	}

	/**
	 * 重置密码
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resetpassword")
	@Log(detail = "重置密码", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "admin:resetpassword" })
	@ResponseBody
	public ResultTools resetpassword(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		
		
			return adminService.resetpassword(bean);

	}

	@RequestMapping("/update")
	@Log(detail = "编辑用户", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "admin:update" })
	public String update(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
             //数据集校验
			VailUtils.validate(bean, AdminUpdateParmValidator.class);
			HttpServletUtil.writeJson(JsonUtils.objectToJson(adminService.updateAdmin(bean)), respone);
			model.addAttribute("bean", bean);
		} else {
			QueryWrapper<CommonRole> wrapper = new QueryWrapper<>();
			wrapper.eq("IS_HANDLER", 2);
			model.addAttribute("roleList", commonRoleService.list(wrapper));
			model.addAttribute("bean", adminService.getById(bean.getId()));

		}

		return geturl("add");
	}

	/**
	 * 删除
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del")
	@Log(detail = "删除用户", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "admin:del" })
	@ResponseBody
	public ResultTools del(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		return adminService.delAdmin(bean);

	}

	/**
	 * @Title: geturl
	 * @Description: TODO
	 * @param url
	 * @return
	 * @return: String
	 */
	public String geturl(String url) {
		return "user/" + url;
	}

}
