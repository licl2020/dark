package com.datareport.controller;


import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datareport.common.String.StringUtil;
import com.datareport.common.http.HttpServletUtil;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.log.annotation.Log;
import com.datareport.log.enums.OperationType;
import com.datareport.log.enums.OperationUnit;
import com.datareport.quartz.bean.JobDetails;
import com.datareport.quartz.service.QuartzService;
import com.datareport.quartz.validation.JobAddParmValidator;
import com.datareport.validator.VailUtils;

import io.lettuce.core.models.command.CommandDetail.Flag;
import lombok.extern.log4j.Log4j;


/**
 * @name: JobController
 * @description:
 * @author: LICL
 * @dateTime: 2020/01/20 12:06
 */
@Controller
@RequestMapping("/job")
@Log4j
public class JobController {

    @Autowired
    QuartzService service;
    
    
    /**
	 * 定时任务列表
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
	@Log(detail = "定时任务列表", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.SELECT)
	@RequiresPermissions(value = { "job:list" })
	@RequestMapping("/list")
	public String list(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model) {
       model.addAttribute("list",service.getJobList(bean.getGroupName()));
       model.addAttribute("bean",bean);
		return geturl("list");

	}

	/**
	 * 修改任务状态
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
	@Log(detail = "修改任务状态", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "job:updatestate" })
	public ResultTools updatestate(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		try {
			if(bean.getState()==1) {
				service.resumeJob(bean.getJobName(), bean.getGroupName());
		      }else {
		    	  service.pauseJob(bean.getJobName(), bean.getGroupName());
		      }
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			 return ResultTools.getInstance(Light.ERROR_201);
		}

	}
	
	
	/**
	 * 修改所有任务状态
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateallstate")
	@ResponseBody
	@Log(detail = "修改任务状态", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "job:updateallstate" })
	public ResultTools updateAllstate(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		try {
			if(bean.getState()==1) {
				service.startAllJobs();
		      }else {
		        service.shutdownAllJobs();
		      }
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			 return ResultTools.getInstance(Light.ERROR_201);
		}

	}

	/**
	 * 添加任务
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@Log(detail = "添加任务", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
	@RequiresPermissions(value = { "job:add" })
	public String add(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
			 //数据集校验
			 VailUtils.validate(bean, JobAddParmValidator.class);
			 Class<?> cs = null;
			 boolean f =true;
		  try {
			     cs=Class.forName(bean.getClassName());
			} catch (Exception e) {
				f =false;
				HttpServletUtil.writeJson(JsonUtils.objectToJson(ResultTools.getInstance(-100, "执行类路径不正确", "error")), respone);
			}
		  if(f) {
				HttpServletUtil.writeJson(JsonUtils.objectToJson(service.addJob(cs, bean.getJobName(), bean.getGroupName(), bean.getCronExp(), null)), respone);
		  }

		}
		model.addAttribute("bean", bean);
		return geturl("add");
	}


	@RequestMapping("/update")
	@Log(detail = "编辑任务", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
	@RequiresPermissions(value = { "job:update" })
	public String update(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {

		// 获取提交方式，如果是get，则跳转页面；如果是post，则进行数据校验
		String mode = request.getMethod();
		// 判断当前bean是否有数据,是get还是post提交方式，如果是get跳转登录页面，如果是post进行数据校验
		if (!StringUtil.isEmpty(mode) && mode.toUpperCase().equals("POST") && !StringUtil.isEmpty(bean)) {
			 //数据集校验
			VailUtils.validate(bean, JobAddParmValidator.class);
		   HttpServletUtil.writeJson(JsonUtils.objectToJson(service.updateJob(bean.getJobName(), bean.getGroupName(), bean.getCronExp(), null)), respone);
			model.addAttribute("bean", bean);
		} else {
			model.addAttribute("bean", service.getJob(bean.getGroupName(), bean.getJobName()));
		}

		return geturl("add");
	}

	/**
	 * 删除任务
	 *
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del")
	@Log(detail = "删除任务", level = 2, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
	@RequiresPermissions(value = { "job:del" })
	@ResponseBody
	public ResultTools del(JobDetails bean, HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception {
		try {
			service.deleteJob(bean.getJobName(), bean.getGroupName());
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			 return ResultTools.getInstance(Light.ERROR_201);
		}
		      

	}

	/**
	 * @Title: geturl
	 * @Description: TODO
	 * @param url
	 * @return
	 * @return: String
	 */
	public String geturl(String url) {
		return "job/" + url;
	}


}
