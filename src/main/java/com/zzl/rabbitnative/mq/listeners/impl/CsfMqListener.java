package com.zzl.rabbitnative.mq.listeners.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zzl.rabbitnative.mq.endpoints.impl.CsfEndpoint;
import com.zzl.rabbitnative.mq.listeners.MqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
public class CsfMqListener implements MqListener {

    private CsfEndpoint endpoint;

    @Value("${rabbitmq.listen.csf.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.listen.csf.queue}")
    private String queueName;

    @Value("${rabbitmq.listen.csf.routing-key}")
    private String routingKey;

    @Autowired
    public CsfMqListener(CsfEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void listen() {
        try {
            Channel channel = endpoint.getChannel();
            channel.exchangeDeclare(exchangeName, "topic", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            boolean autoAck=false;
            //消息消费完成确认
            channel.basicConsume(queueName, autoAck,"", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    try {
                        String message = new String(body);
                        System.out.println("消费者 csf == " + message);
                    }
                    catch (Exception e) {
                        channel.abort();  //此操作中的所有异常将被丢弃
                    }
                    finally {
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}