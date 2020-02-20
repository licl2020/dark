package com.datareport.rbmq.sl;
//package com.report.rbmq.sl;
//
//
//
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * 示例
// * @author LICL
// *
// */
//@Component
//@RabbitListener(queues = "sl")
//public class DoorLockReceiver {
//	
//	
//  //  @Autowired
//	
//	    @RabbitHandler
//	    public void process(String msg,Message message, com.rabbitmq.client.Channel channel) throws Exception  {
//		  
//	        try {
//	        	
//	        	//确认消费
//	        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//	        	//未消费 放回到队列
//	        	channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//			} catch (Exception e) {
//				e.printStackTrace();
//				//放回到队列
//				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//				//拒绝消息
//				//channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//			}
//	    }
//	    
//	    
//}
