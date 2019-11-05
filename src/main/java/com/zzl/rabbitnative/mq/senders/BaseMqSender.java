package com.zzl.rabbitnative.mq.senders;

import com.rabbitmq.client.Channel;
import com.zzl.rabbitnative.mq.config.endpoints.MqEndpoint;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangzhaolin on 2019/11/5.
 */
public class BaseMqSender implements MqSender {

    private MqEndpoint endpoint;

    public BaseMqSender(MqEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void send(String exchangeName, String routingKey, String message) throws IOException, TimeoutException {
        Channel channel = endpoint.getChannel();
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        endpoint.close();
    }
}
