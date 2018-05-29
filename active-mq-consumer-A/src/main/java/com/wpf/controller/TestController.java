package com.wpf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private JmsMessagingTemplate jms;

    @Autowired
    private JmsMessagingTemplate jmsTopicTemplate;

    @GetMapping("/deleteTopicSub")
    public void testUnSub() throws JMSException {

        jms.getConnectionFactory().createConnection().createSession(false, 1).unsubscribe("sub_01");

    }

}
