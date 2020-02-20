package com.datareport.rbmq.tool;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 
/**
 * 功能概要：消息产生,提交到队列中去
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */
@Component
public class MessageExecuteProducer{
	
 
	@Autowired
	private MessageProducer messageProducer;
	
	private static MessageExecuteProducer messageExecuteProducer; 
	
	@PostConstruct 
	  public void init() { 
		messageExecuteProducer = this; 
		messageExecuteProducer.messageProducer = messageProducer; 
	}
 
	public void sendMessage(String pointName,Object message){
		
		messageExecuteProducer.messageProducer.sendMessage(pointName,message);
	 
	}
	public void sendMessage(String exchange_name,String key,Object message){
		
		 
		messageExecuteProducer.messageProducer.sendMessage(exchange_name, key, message);
	}
	
	
	
	
	
	
	
}
