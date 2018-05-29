package com.wpf.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author: elvin
 */
@Component
public class QueueListener {

    // ����/����ģʽ�¶�������߻��������Ѷ����е���Ϣ
    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
    @SendTo("out.queue")
    public String receive(String text){
        System.out.println("QueueListener: consumer-a �յ�һ����Ϣ: " + text);
        return "consumer-a received : " + text;
    }
}