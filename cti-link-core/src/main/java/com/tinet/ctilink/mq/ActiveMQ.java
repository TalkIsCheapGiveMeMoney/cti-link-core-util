package com.tinet.ctilink.mq;

import com.tinet.ctilink.json.JSONObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwei //
 * @date 16/4/18 17:04
 */
public class ActiveMQ implements MessageQueue {

    private JmsTemplate jmsTemplate;

    private String destination;

    @Override
    public <T> void sendMessage(T t) {
        String json = JSONObject.getJSONString(t);
        if (json != null) {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(json);
                }
            });
        }

    }

    @Override
    public <T> List<T> receiveMessage(Class<T> clazz) {
        Message message = jmsTemplate.receive(destination);
        if (message != null) {
            List<T> list = new ArrayList<>();
            list.add((T) message);
            return list;
        }
        return null;
    }

    @Override
    public Integer getWaitCount() {
        return null;
    }

    @Override
    public Integer getDealingCount() {
        return null;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
