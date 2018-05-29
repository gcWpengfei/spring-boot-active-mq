package com.wpf.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author: elvin
 */
@Component
public class TopicListener {

    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic" ,subscription="sub_01")
    public void receive(String text){
        System.out.println("TopicListener: consumer-a �յ�һ����Ϣ: " + text);
    }
}