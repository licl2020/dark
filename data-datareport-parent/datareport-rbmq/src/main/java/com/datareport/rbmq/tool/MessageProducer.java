package com.datareport.rbmq.tool;




import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
/**
 * 功能概要：消息产生,提交到队列中去
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */
@Service
public class MessageProducer{
	
 
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
 
	public void sendMessage(String pointName,Object message){
		
		this.rabbitTemplate.convertAndSend(pointName,message);
	 
	}
	public void sendMessage(String exchange_name,String key,Object message){
		
		 
		this.rabbitTemplate.convertAndSend(exchange_name, key, message);
	}
	
	
	
	
	
	
	
}
