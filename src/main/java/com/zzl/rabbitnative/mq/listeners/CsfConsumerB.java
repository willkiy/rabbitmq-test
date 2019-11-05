package com.zzl.rabbitnative.mq.listeners;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by zhangzhaolin on 2019/11/5.
 */
public class CsfConsumerB extends DefaultConsumer {

    public CsfConsumerB(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        String message = new String(body);
        System.out.println("CsfConsumerB == " + message);
    }
}
