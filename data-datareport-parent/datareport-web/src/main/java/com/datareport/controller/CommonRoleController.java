package com.datareport.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.datareport.bean.CommonRolePermssion;
import com.datareport.bean.ModularDto;
import com.datareport.bean.ROLEJsonList;
import com.datareport.common.String.StringUtil;
import com.datareport.common.http.HttpServletUtil;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.msg.ResultTools;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.service.CommonPermssionService;
import com.datareport.service.CommonRolePermssionService;
import com.datareport.service.CommonRoleService;
import com.datareport.util.UserUtil;
import com.datareport.validation.RoleAddParmValidator;
import com.datareport.validation.RoleUpdateParmValidator;
import com.datareport.validator.VailUtils;

import lombok.extern.log4j.Log4j;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Controller
@RequestMapping("/role")
@Log4j
public class CommonRoleController {


	@Autowired
	private UserUtil userUtil;



	@Autowired
	private CommonRoleService commonRoleService;

	@Autowired
	private CommonRolePermssionService commonRolePermssionService;

	@Autowired
	private CommonPermssionService commonPermssionService;

	/**
	 * 查询角色列表
	 * 
	 * @Title: list
	 * @Description: TODO
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = { "role:list" })
	@Log(detail = "角色列表", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	public String list(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model) {
		try {
			// 获取分页数据
			Integer pageNo =1;
        	String requstPage = request.getParameter("page.currentPage");
        	if (!StringUtil.isEmpty(requstPage)) {
        		pageNo=Integer.valueOf(requstPage);
        	}
			Integer pageSize = 10;
			IPage<CommonRole> page = new Page<>(pageNo, pageSize);
			QueryWrapper<CommonRole> wrapper = new QueryWrapper<>();
			wrapper.eq("IS_HANDLER", "2").setEntity(bean);
			page = commonRoleService.page(page, wrapper);
			com.datareport.common.page.Page pages = new com.datareport.common.page.Page();
			pages.setShowCount(pages.getShowCount());
			pages.setCurrentPage((int) page.getCurrent());
			pages.setTotalResult((int) page.getTotal());
			pages.setTotalPage((int) page.getPages());
			model.addAttribute("bean", bean);
			model.addAttribute("page", pages);
			model.addAttribute("list", page.getRecords());
		} catch (Exception e) {

			log.error(e.getMessage());
		}

		return geturl("list");

	}

	/**
	 * 重置缓存
	 * 
	 * @param request
	 * @param respone
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/reset")
	@RequiresPermissions(value = { "role:reset" })
	@Log(detail = "刷新角色缓存数据", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	public void reset(HttpServletRequest request, HttpServletResponse respone, Model model) throws Exception {
		// service.reset();
		// 刷新角色权限缓存数据
		// HttpServletUtil.writeJson(News.getReturnMsg(ReturnValueEnum.SUCCESS),respone);
	}

	/**
	 * 添加权限
	 * 
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addauth")
	@ResponseBody
	@Log(detail = "保存角色权限信息", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
	public ResultTools addauth(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		// 获取数组
		String items = request.getParameter("ary");
		return commonRoleService.saveRoleAuth(items, bean.getId());
	}

	@RequestMapping(value = "/role_auth", produces = "application/json; charset=utf-8")
	@ResponseBody
	@Log(detail = "查询权限树形数据", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	public ROLEJsonList role_auth(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取用户数据
		Admin user = userUtil.getUser();
		List<ModularDto> list = new ArrayList<ModularDto>();
		ModularDto mto = new ModularDto();
		mto.setRoleid(user.getrId());
		list.addAll(AuthList(commonPermssionService.findMenuAll(mto), bean.getId()));

		ROLEJsonList tree = new ROLEJsonList();
		tree.setMessage("ok");
		tree.setStatus("0");
		tree.setData(list);
		return tree;
	}

	/**
	 * 校验权限
	 * 
	 * @param list
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<ModularDto> AuthList(List<ModularDto> list, Integer role_id) throws SQLException {
		QueryWrapper<CommonRolePermssion> wrapper = new QueryWrapper<>();
		wrapper.eq("ROLE_ID", role_id);
		List<CommonRolePermssion> list2 = commonRolePermssionService.list(wrapper);
		for (int i = 0; i < list.size(); i++) {

			ModularDto auth = list.get(i);
			if (StringUtil.isEmpty(auth)) {
				continue;
			}

			for (int j = 0; j < list2.size(); j++) {

				CommonRolePermssion role = list2.get(j);
				if (StringUtil.isEmpty(role)) {
					continue;
				}

				if (auth.getId() == role.getResourceId()) {
					list.get(i).setAuth_id(role.getResourceId());
				}

			}

		}

		return list;

	}

	/**
	 * 新增角色
	 * 
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@RequiresPermissions(value = { "role:add" })
	@Log(detail = "新增角色", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
	public String add(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
			         //数据校验
			         VailUtils.validate(bean, RoleAddParmValidator.class);
			         HttpServletUtil.writeJson(JsonUtils.objectToJson(commonRoleService.addRole(bean)),
								respone);
		}
		model.addAttribute("bean", bean);

		return geturl("add");

	}

	/**
	 * 删除角色
	 * 
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del")
	@ResponseBody
	@RequiresPermissions(value = { "role:del" })
	@Log(detail = "删除角色", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
	public ResultTools del(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		return	commonRoleService.delRole(bean);
	}

	/**
	 * 跳转分配角色权限页面
	 * 
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rolelist")
	@Log(detail = "分配角色权限", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "role:rolelist" })
	public String rolelist(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		model.addAttribute("bean", bean);
		return geturl("role_list");
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value = { "role:update" })
	@Log(detail = "编辑角色", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
	public String update(CommonRole bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();

		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
			
	         //数据校验
	         VailUtils.validate(bean, RoleUpdateParmValidator.class);
	         HttpServletUtil.writeJson(JsonUtils.objectToJson(commonRoleService.updateRole(bean)),respone);
			 model.addAttribute("bean", bean);
		} else {
			model.addAttribute("bean", commonRoleService.getById(bean.getId()));
		}
		return geturl("add");

	}

	/**
	 * @Title: 目录
	 * @Description: TODO
	 * @param url
	 * @return
	 * @return: String
	 */
	public String geturl(String url) {
		return "role/" + url;
	}
}
