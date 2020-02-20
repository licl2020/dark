package com.datareport.rbmq.config;




import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;


@Configuration
@Profile("dev")
public class RabbitConfig {
	
	@Value("${spring.rabbitmq.host}")
	private String addresses;
 
	@Value("${spring.rabbitmq.port}")
	private String port;
 
	@Value("${spring.rabbitmq.username}")
	private String username;
 
	@Value("${spring.rabbitmq.password}")
	private String password;
 
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
 
	@Value("${spring.rabbitmq.publisher-confirms}")
	private boolean publisherConfirms;

     @Bean
     public ConnectionFactory connectionFactory() {
    	 
 		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
 		connectionFactory.setAddresses(addresses + ":" + port);
 		connectionFactory.setUsername(username);
 		connectionFactory.setPassword(password);
 		connectionFactory.setVirtualHost(virtualHost);
 		/** 如果要进行消息回调，则这里必须要设置为true */
 		//connectionFactory.setPublisherConfirms(publisherConfirms);

         Channel channel = connectionFactory.createConnection().createChannel(false);

         // 声明queue,exchange,以及绑定
         try {
//             channel.exchangeDeclare(queueExchange /* exchange名称 */, "topic"/* 类型 */);
//             // durable,exclusive,autodelete
//             channel.queueDeclare(queueName, true, false, false, null); // (如果没有就)创建Queue
//             channel.queueBind(queueName, queueExchange, routingkey);
         } catch (Exception e) {
         } finally {
             try {
                 channel.close();
             } catch (Exception e) {
             }

         }
         return connectionFactory;
     }

     // 配置接收端属性，
     @Bean
     public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
             ConnectionFactory connectionFactory) {
         SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
         factory.setConnectionFactory(connectionFactory);
         factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);// 确认模式：自动，默认
         factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
         return factory;
     }

     @Bean
     @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
     // 必须是prototype类型,不然每次回调都是最后一个内容
     public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

         RabbitTemplate template = new RabbitTemplate(connectionFactory);
         template.setMessageConverter(new Jackson2JsonMessageConverter());// 发送端类型转化pojo,需要序列化
         return template;
     }
}
