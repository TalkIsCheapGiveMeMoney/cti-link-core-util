package com.tinet.ctilink.mq;

import com.amazonaws.services.sqs.model.Message;
import com.tinet.ctilink.aws.AwsSQSService;
import com.tinet.ctilink.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fengwei //
 * @date 16/4/18 16:49
 */
public class AmazonSQS implements MessageQueue {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String sqsName;
    private String sqsUrl;
    private Integer maxSize = 100 * 1024;  //100k
    private Integer retentionPeriod = 3600 * 24 * 4;
    private Integer waitTimeSecond = 10;
    private Integer visibilityTimeout = 30;

    private AwsSQSService awsSQSService;

    public AmazonSQS() {
        sqsUrl = awsSQSService.createQueue(sqsName, maxSize, retentionPeriod, waitTimeSecond, visibilityTimeout);
    }

    @Override
    public <T> Boolean sendMessage(T t) {
        if (t == null) {
            return false;
        }
        try {
            if (sqsUrl != null) {
                String json = JSONObject.getJSONString(t);
                if (json != null) {
                    awsSQSService.sendMessage(json, sqsUrl);
                }
            }
        } catch (Exception e) {
            logger.error("AmazonSQS.sendMessage error", e);
            return false;
        }

        return true;
    }

    @Override
    public <T> List<T> receiveMessage(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        List<Message> messageList = awsSQSService.receiveMessage(sqsUrl);
        for (Message message : messageList) {
            String json = message.getBody();
            awsSQSService.deleteMessage(sqsUrl, message.getReceiptHandle());
            if (StringUtils.isNotEmpty(json)) {
                try {
                    T t = JSONObject.getBean(json, clazz);
                    if (t != null) {
                        list.add(t);
                    }
                } catch (Exception e) {
                    logger.error("AmazonSQS.receiveMessage error", e);
                }
            }
        }
        return list;
    }

    @Override
    public Integer getWaitCount() {
        List<String> attributes = new ArrayList<String>();
        attributes.add("ApproximateNumberOfMessages");
        Map<String, String> attributeResult = awsSQSService.getQueueAttribute(sqsUrl, attributes);
        if (attributeResult != null) {
            return Integer.parseInt(attributeResult.get("ApproximateNumberOfMessages"));
        }
        return 0;
    }

    @Override
    public Integer getDealingCount() {
        List<String> attributes = new ArrayList<String>();
        attributes.add("ApproximateNumberOfMessagesNotVisible");
        Map<String, String> attributeResult = awsSQSService.getQueueAttribute(sqsUrl, attributes);
        if (attributeResult != null) {
            return Integer.parseInt(attributeResult.get("ApproximateNumberOfMessagesNotVisible"));
        }
        return 0;
    }

    public String getSqsName() {
        return sqsName;
    }

    public void setSqsName(String sqsName) {
        this.sqsName = sqsName;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(Integer retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public Integer getWaitTimeSecond() {
        return waitTimeSecond;
    }

    public void setWaitTimeSecond(Integer waitTimeSecond) {
        this.waitTimeSecond = waitTimeSecond;
    }

    public Integer getVisibilityTimeout() {
        return visibilityTimeout;
    }

    public void setVisibilityTimeout(Integer visibilityTimeout) {
        this.visibilityTimeout = visibilityTimeout;
    }

    public AwsSQSService getAwsSQSService() {
        return awsSQSService;
    }

    public void setAwsSQSService(AwsSQSService awsSQSService) {
        this.awsSQSService = awsSQSService;
    }
}
