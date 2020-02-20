package com.datareport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datareport.bean.Admin;
import com.datareport.bean.CommonPermssion;
import com.datareport.common.json.JsonUtils;
import com.datareport.config.eum.SystemConfigEnum;
import com.datareport.config.redis.RedisClient;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.service.CommonPermssionService;
import com.datareport.util.UserUtil;

@Controller
@Transactional
public class IndexController {

	@Autowired
	private RedisClient redis;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private CommonPermssionService commonPermssionService;
	
    

	/**
	 * 登录成功，初始化
	 * 
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 */
	@Log(detail = "首页", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequestMapping("/index")
	public String excel(HttpServletRequest request, HttpServletResponse respone, Model model) {

		try {
			Admin admin = userUtil.getUser();
			model.addAttribute("name", admin.getuName());
			model.addAttribute("userType", admin.getaMax());
			List<CommonPermssion> list =new ArrayList<CommonPermssion>();
			if(redis.hHasKey(SystemConfigEnum.SYSTEM_AUTH_MAP.getValue(),SystemConfigEnum.SYSTEM_FLAG+String.valueOf(admin.getrId()))) {
				
				Object josonMsg = redis.hget(SystemConfigEnum.SYSTEM_AUTH_MAP.getValue(), SystemConfigEnum.SYSTEM_FLAG+String.valueOf(admin.getrId()));
				list=JsonUtils.jsonToList(josonMsg.toString(), CommonPermssion.class);
			}else {
				 list = handle(commonPermssionService.selectAuthList(admin.getrId()));
				 redis.hset(SystemConfigEnum.SYSTEM_AUTH_MAP.getValue(),SystemConfigEnum.SYSTEM_FLAG+String.valueOf(admin.getrId()),JsonUtils.objToJson(list));
			}
			model.addAttribute("list", list);
		} catch (Exception e) {
		}

		return geturl("index");
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

	/**
	 * 首页内容
	 * 
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 */
	@RequestMapping("/indexcontent")
	public String indexcontent(HttpServletRequest request, HttpServletResponse respone, Model model) {
		// 获取管理员数据
		return geturl("indexcontent");
	}

//	/**
//	 * @Title: 提示
//	 * @Description: TODO
//	 * @param bean
//	 * @param request
//	 * @param respone
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 * @return: String
//	 */
//	@RequestMapping("/prompt")
//	public boolean prompt(HttpServletRequest request, HttpServletResponse respone, Model model)
//			throws Exception
//	{
//		PrintWriter out = respone.getWriter();
//		out.println("<html>");
//		out.println("<script>");
//		out.println("window.open ('/Login','_top')");
//		out.println("</script>");
//		out.println("</html>");
//		return false;
//	}

	/**
	 * @Title: geturl
	 * @Description: TODO
	 * @param url
	 * @return
	 * @return: String
	 */
	public String geturl(String url) {
		return "main/" + url;
	}

}
