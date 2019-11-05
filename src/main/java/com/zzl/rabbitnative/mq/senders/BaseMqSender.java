package com.zzl.rabbitnative.mq.senders;

import com.rabbitmq.client.Channel;
import com.zzl.rabbitnative.mq.endpoints.MqEndpoint;

import java.io.IOException;

/**
 * Created by zhangzhaolin on 2019/11/5.
 */
public class BaseMqSender implements MqSender {

    private MqEndpoint endpoint;

    public BaseMqSender(MqEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void send(String exchangeName, String routingKey, String message) {
        Channel channel = null;
        try {
            channel = endpoint.getChannel();
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (endpoint != null) {
                endpoint.close();
            }
        }
    }
}
