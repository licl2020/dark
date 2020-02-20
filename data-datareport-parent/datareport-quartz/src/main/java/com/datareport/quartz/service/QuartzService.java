package com.datareport.quartz.service;


import java.util.List;
import java.util.Map;

import com.datareport.common.msg.ResultTools;
import com.datareport.quartz.bean.JobDetails;

/**
 * @packageName: com.boot.quartz.service
 * @name: QuartzService
 * @description: 任务操作服务接口层
 * @author: LICL
 * @dateTime: 2020/01/20 11:51
 */
public interface QuartzService {
    /**
     * 添加任务可以传参数
     *
     * @param clazz
     * @param jobName
     * @param groupName
     * @param cronExp
     * @param param
     */
	ResultTools addJob(Class clazz, String jobName, String groupName, String cronExp, Map<String, Object> param);

    /**
     * 暂停任务
     *
     * @param name
     * @param groupName
     */
    void pauseJob(String name, String groupName);

    /**
     * 恢复任务
     *
     * @param name
     * @param groupName
     */
    void resumeJob(String name, String groupName);

    /**
     * 更新任务
     *
     * @param name
     * @param groupName
     * @param cronExp
     * @param param
     */
    ResultTools updateJob(String name, String groupName, String cronExp, Map<String, Object> param);

    /**
     * 删除任务
     *
     * @param name
     * @param groupName
     */
    void deleteJob(String name, String groupName);

    /**
     * 启动所有任务
     */
    void startAllJobs();

    /**
     * 关闭所有任务
     */
    void shutdownAllJobs();
    
    /***
     * 查询所有的job
     * @return
     */
    List<JobDetails> getJobList(String groupName);
    
    /***
     * 根据分组名和任务名查询job
     * @return
     */
    public JobDetails getJob(String groupNames,String jonname);
}
