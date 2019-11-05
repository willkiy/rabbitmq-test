package com.zzl.rabbitnative.mq.listeners;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zhangzhaolin on 2019/11/5.
 */
public class CsfConsumerA extends DefaultConsumer {

    public CsfConsumerA(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        String message = new String(body);
        System.out.println("CsfConsumerA == " + message);
    }
}
