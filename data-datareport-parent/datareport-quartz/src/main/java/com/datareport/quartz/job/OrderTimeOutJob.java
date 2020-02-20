package com.datareport.quartz.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @packageName: com.boot.quartz.job
 * @name: OrderTimeOutJob
 * @description:
 * @author: 董定卓
 * @dateTime: 2019/7/24 12:05
 */
public class OrderTimeOutJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        //获取任务名
        String taskName = context.getJobDetail().getKey().getName();
        System.out.println("taskName->" + taskName);
        //处理执行任务之后的业务
    }
}
