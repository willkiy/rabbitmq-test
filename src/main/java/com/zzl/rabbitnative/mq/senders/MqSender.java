package com.zzl.rabbitnative.mq.senders;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
public interface MqSender {

    void send(String exchangeName, String routingKey, String message);
}
