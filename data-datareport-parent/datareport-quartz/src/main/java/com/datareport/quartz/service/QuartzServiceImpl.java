package com.datareport.quartz.service;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datareport.common.String.StringUtil;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.quartz.bean.JobDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @packageName: com.boot.quartz.service
 * @name: QuartzServiceImpl
 * @description: 任务调度服务接口实现类
 * @author: LICL
 * @dateTime: 2020/01/20 11:51
 */
@Service
public class QuartzServiceImpl implements QuartzService {

	@Autowired
	Scheduler scheduler;
	
	
	
	

	/**
	 * 创建job，可传参
	 *
	 * @param clazz     任务类
	 * @param name      任务名称
	 * @param groupName 任务所在组名称
	 * @param cronExp   cron表达式
	 * @param param     map形式参数
	 */
	@Override
	public ResultTools addJob(Class clazz, String name, String groupName, String cronExp, Map<String, Object> param) {
		try {
		
			// 启动调度器
			   scheduler.start();
			if (!scheduler.checkExists(JobKey.jobKey(name, groupName))) {
				
				// 构建job信息
				JobDetail jobDetail = JobBuilder.newJob(((Job) clazz.newInstance()).getClass()).withIdentity(name, groupName).build();
				// 表达式调度构建器(即任务执行的时间).withMisfireHandlingInstructionDoNothing();不立即执行
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp).withMisfireHandlingInstructionDoNothing();
				// 按新的cronExpression表达式构建一个新的trigger
				CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
						.withSchedule(scheduleBuilder).build();
				// 获得JobDataMap，写入数据
				if (param != null) {
					trigger.getJobDataMap().putAll(param);
				}
				scheduler.scheduleJob(jobDetail, trigger);
				pauseJob(name, groupName);
				return ResultTools.getInstance(Light.SUCCESS);
			} else {
				return ResultTools.getInstance(-100, "任务已经存在", "error");
			}
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	/**
	 * 暂停job
	 *
	 * @param name      任务名称
	 * @param groupName 任务所在组名称
	 */
	@Override
	public void pauseJob(String name, String groupName) {
		try {
			scheduler.pauseJob(JobKey.jobKey(name, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复job
	 *
	 * @param name      任务名称
	 * @param groupName 任务所在组名称
	 */
	@Override
	public void resumeJob(String name, String groupName) {
		try {
			scheduler.resumeJob(JobKey.jobKey(name, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * job 更新,更新频率和参数
	 *
	 * @param name      任务名称
	 * @param groupName 任务所在组名称
	 * @param cronExp   cron表达式
	 * @param param     参数
	 */
	@Override
	public ResultTools updateJob(String name, String groupName, String cronExp, Map<String, Object> param) {
		try {
			pauseJob(name,groupName);
			TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (cronExp != null) {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			}

			// 修改map
			if (param != null) {
				trigger.getJobDataMap().putAll(param);
			}
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	/**
	 * job 删除
	 *
	 * @param name      任务名称
	 * @param groupName 任务所在组名称
	 */
	@Override
	public void deleteJob(String name, String groupName) {
		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(name, groupName));
			scheduler.unscheduleJob(TriggerKey.triggerKey(name, groupName));
			scheduler.deleteJob(JobKey.jobKey(name, groupName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动所有定时任务
	 */
	@Override
	public void startAllJobs() {
		try {
			scheduler.resumeAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	@Override
	public void shutdownAllJobs() {
		try {
			scheduler.pauseAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<JobDetails> getJobList(String groupNames) {
		 List<JobDetails> List = new ArrayList<JobDetails>();
		  try {
	            //再获取Scheduler下的所有group
			  List<String> triggerGroupNames =new ArrayList<String>();
			    if(!StringUtil.isEmpty(groupNames)) {
			    		 triggerGroupNames.add(groupNames);
			    }else {
			    	triggerGroupNames=scheduler.getTriggerGroupNames();
			    }
	            for (String groupName : triggerGroupNames) {
	                //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
	                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
	                //获取所有的triggerKey
	                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
	                for (TriggerKey triggerKey : triggerKeySet) {
	                    //通过triggerKey在scheduler中获取trigger对象
	                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	                    //获取trigger拥有的Job
	                    JobKey jobKey = trigger.getJobKey();
	                    JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
	                    //组装页面需要显示的数据
	                    JobDetails quartzJobsVO = new JobDetails();
	                    quartzJobsVO.setGroupName(groupName);
	                    quartzJobsVO.setJobName(jobDetail.getName());
	                    quartzJobsVO.setCronExp(trigger.getCronExpression());
	                    quartzJobsVO.setStateCode(scheduler.getTriggerState(triggerKey).name());
	                    quartzJobsVO.setClassName(jobDetail.getJobClass().getName());
	                    List.add(quartzJobsVO);
	                }
	            }
	        } catch (Exception e) {
	        	System.out.println(e);
	        }
		return List;
	}

	@Override
	public JobDetails getJob(String groupNames, String jonname) {
		JobDetails quartzJobsVO = new JobDetails();
		try {
			 //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupNames);
            //获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                //通过triggerKey在scheduler中获取trigger对象
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //组装页面需要显示的数据
                quartzJobsVO.setGroupName(groupNames);
                quartzJobsVO.setJobName(jonname);
                quartzJobsVO.setCronExp(trigger.getCronExpression());
                quartzJobsVO.setStateCode(scheduler.getTriggerState(triggerKey).name());
                quartzJobsVO.setClassName(jobDetail.getJobClass().getName());
                if(jobDetail.getName().equals(jonname)) {
                	break;
                }
            }
		} catch (Exception e) {
		}
		return quartzJobsVO;
	}
}
