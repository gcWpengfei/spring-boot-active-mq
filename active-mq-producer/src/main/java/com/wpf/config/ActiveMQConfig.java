package com.wpf.config;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * @author: elvin
 */
@Configuration
public class ActiveMQConfig {
    @Value("${queueName}")
    private String queueName;

    @Value("${topicName}")
    private String topicName;

    @Value("${spring.activemq.user}")
    private String usrName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    @Bean(name = "myJmsTemplate")
    public JmsTemplate getJmsTemplate(ActiveMQConnectionFactory nonXaJmsConnectionFactory){
        //使用CachingConnectionFactory可以提高部分性能。
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(100);
        cachingConnectionFactory.setTargetConnectionFactory(nonXaJmsConnectionFactory);
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        //设置deliveryMode（持久化）, priority, timeToLive必须开启
        jmsTemplate.setExplicitQosEnabled(true);
        // 设置消息是否持久化
        jmsTemplate.setDeliveryPersistent(true);

        // 设置消息是否以事务
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}