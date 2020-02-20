package com.datareport.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.datareport.bean.AuthorityJsonList;
import com.datareport.bean.CommonPermssion;
import com.datareport.bean.CommonRole;
import com.datareport.common.String.StringUtil;
import com.datareport.common.enumeration.ModularMtypeEnum;
import com.datareport.common.http.HttpServletUtil;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.config.eum.SystemConfigEnum;
import com.datareport.config.redis.RedisClient;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.service.CommonPermssionService;
import com.datareport.service.CommonRoleService;
import com.datareport.validation.CommonPermssionAddParmValidator;
import com.datareport.validation.CommonPermssionUpdateParmValidator;
import com.datareport.validator.VailUtils;

import lombok.extern.log4j.Log4j;

/**
 * <p>
 * 该表用来存储资源权限信息 前端控制器
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Controller
@RequestMapping("/modular")
@Log4j
public class CommonPermssionController {

	@Autowired
	private CommonPermssionService commonPermssionService;

	@Autowired
	private CommonRoleService commonRoleService;

	@Autowired
	private RedisClient redis;

	/**
	 * 查询权限模块列表
	 *
	 * @param model
	 * @return
	 * @Title: list
	 * @Description: TODO
	 * @return: String
	 */
	@RequestMapping("/list")
	@Log(detail = "权限列表", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "modular:list" })
	public String list(CommonPermssion bean, HttpServletRequest request, HttpServletResponse respone, Model model) {
		try {

			Integer pageNo = 1;
			String requstPage = request.getParameter("page.currentPage");
			if (!StringUtil.isEmpty(requstPage)) {
				pageNo = Integer.valueOf(requstPage);
			}
			Integer pageSize = 10;
			IPage<CommonPermssion> page = new Page<>(pageNo, pageSize);
			QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
			CommonPermssion commonPermssion = new CommonPermssion();
			if (StringUtil.isEmpty(bean.getParentId())) {
				commonPermssion.setType(0);
			} else {
				commonPermssion.setParentId(bean.getParentId());
			}
			wrapper.setEntity(commonPermssion);
			page = commonPermssionService.page(page, wrapper);
			com.datareport.common.page.Page pages = new com.datareport.common.page.Page();
			pages.setShowCount(pages.getShowCount());
			pages.setCurrentPage((int) page.getCurrent());
			pages.setTotalResult((int) page.getTotal());
			pages.setTotalPage((int) page.getPages());
			model.addAttribute("bean", bean);
			model.addAttribute("page", pages);
			model.addAttribute("list", page.getRecords());
			model.addAttribute("mtype", ModularMtypeEnum.getMapString());
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
	@Log(detail = "刷新权限缓存", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "modular:reset" })
	public void reset(HttpServletRequest request, HttpServletResponse respone, Model model) throws Exception {
		try {
			List<CommonRole> list = commonRoleService.list();
			list.stream().forEach(bean -> {
				List<CommonPermssion> authList = handle(commonPermssionService.selectAuthList(bean.getId()));
				redis.hset(SystemConfigEnum.SYSTEM_AUTH_MAP.getValue(),
						SystemConfigEnum.SYSTEM_FLAG + String.valueOf(bean.getId()), JsonUtils.objToJson(authList));
			});
			HttpServletUtil.writeJson(JsonUtils.objectToJson(ResultTools.getInstance(Light.SUCCESS)), respone);
		} catch (Exception e) {
			HttpServletUtil.writeJson(JsonUtils.objectToJson(ResultTools.getInstance(Light.ERROR_201)), respone);
		}

	}

	public List<CommonPermssion> handle(List<CommonPermssion> list) {

		int type = 1;
		List<CommonPermssion> menuList = list.stream().filter(c -> c.getType() == type).collect(Collectors.toList())
				.stream().sorted((a, b) -> a.getOrderNum() - b.getOrderNum()).collect(Collectors.toList());
		menuList.stream().forEach(bean -> {
			bean.setItmeList(list.stream().filter(c -> c.getParentId() == bean.getId()).collect(Collectors.toList())
					.stream().sorted((a, b) -> a.getOrderNum() - b.getOrderNum()).collect(Collectors.toList()));
		});
		return menuList;

	}

	@RequestMapping("/add")
	@Log(detail = "添加权限", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
	@RequiresPermissions(value = { "modular:add" })
	public String add(CommonPermssion bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {

			// 数据校验
			VailUtils.validate(bean, CommonPermssionAddParmValidator.class);
			HttpServletUtil.writeJson(JsonUtils.objectToJson(commonPermssionService.addCommonPermssion(bean)), respone);
		}
		model.addAttribute("bean", bean);
		return geturl("add");
	}

	/**
	 * 查询是否有下级
	 *
	 * @param star_id
	 * @param new_id
	 */
	public void updateParent(Integer star_id, Integer new_id, Integer index) {

		if (index == 1) {

			// 查询现在的上级id是否有下级
			QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
			wrapper.eq("PARENT_ID", new_id);
			int count = commonPermssionService.count(wrapper);

			if (count > 0) {
				CommonPermssion commonPermssion = new CommonPermssion();
				commonPermssion.setmParentc(1);
				commonPermssion.setId(new_id);
				commonPermssionService.updateById(commonPermssion);
			}

		}

		if (index == 2) {
			if (star_id != new_id) {

				QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
				wrapper.eq("PARENT_ID", star_id);
				int count = commonPermssionService.count(wrapper);
				if (count == 0) {
					CommonPermssion commonPermssion = new CommonPermssion();
					commonPermssion.setmParentc(2);
					commonPermssion.setId(star_id);
					commonPermssionService.updateById(commonPermssion);
				}

				// 查询现在的上级id是否有下级
				QueryWrapper<CommonPermssion> wrapper2 = new QueryWrapper<>();
				wrapper2.eq("PARENT_ID", new_id);
				int count2 = commonPermssionService.count(wrapper2);

				if (count2 > 0) {
					CommonPermssion commonPermssion = new CommonPermssion();
					commonPermssion.setmParentc(1);
					commonPermssion.setId(new_id);
					commonPermssionService.updateById(commonPermssion);
				}

			}
		}

		if (index == 3) {

			// 查询现在的上级id是否有下级
			QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
			wrapper.eq("PARENT_ID", new_id);
			int count = commonPermssionService.count(wrapper);
			if (count == 0) {
				CommonPermssion commonPermssion = new CommonPermssion();
				commonPermssion.setmParentc(2);
				commonPermssion.setId(new_id);
				commonPermssionService.updateById(commonPermssion);
			}

		}

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
	@ResponseBody
	@Log(detail = "删除权限", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
	@RequiresPermissions(value = { "modular:del" })
	public ResultTools del(CommonPermssion bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		return commonPermssionService.delCommonPermssion(bean);

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
	@RequiresPermissions(value = { "modular:update" })
	@Log(detail = "编辑权限", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	public String update(CommonPermssion bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {

			// 数据校验
			VailUtils.validate(bean, CommonPermssionUpdateParmValidator.class);
			HttpServletUtil.writeJson(JsonUtils.objectToJson(commonPermssionService.updateCommonPermssion(bean)),
					respone);
			model.addAttribute("bean", bean);
		} else {

			// 查询更新数据
			CommonPermssion commonPermssion = commonPermssionService.getById(bean.getId());
			// 查询当前更新数据的上级名称
			CommonPermssion parentCommonPermssion = commonPermssionService.getById(commonPermssion.getParentId());
			// 若查询为空，则默认为根目录
			model.addAttribute("parent_name",
					StringUtil.isEmpty(parentCommonPermssion.getName()) ? "根目录" : parentCommonPermssion.getName());

			model.addAttribute("bean", commonPermssion);

		}
		return geturl("add");
	}

	/**
	 * 树形
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/childs")
	@ResponseBody
	@Log(detail = "模块信息树形检索", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	public String childs(CommonPermssion bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
		if (StringUtil.isEmpty(bean.getParentId())) {
			wrapper.eq("TYPE", 0);
		} else {
			wrapper.eq("PARENT_ID", bean.getParentId());
		}
		List<CommonPermssion> list = commonPermssionService.list(wrapper);
		AuthorityJsonList t = new AuthorityJsonList();
		t.setMessage("ok");
		t.setStatus("0");
		t.setData(list);
		String json = JsonUtils.objToJson(t);
		return json;

	}

	/**
	 * @param url
	 * @return
	 * @Title: 目录
	 * @Description: TODO
	 * @return: String
	 */
	public String geturl(String url) {
		return "modular/" + url;
	}
}
