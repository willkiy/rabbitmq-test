package com.zzl.rabbitnative.mq.listeners.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zzl.rabbitnative.mq.endpoints.impl.CsfEndpoint;
import com.zzl.rabbitnative.mq.listeners.MqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
@ConfigurationProperties(prefix = "rabbitmq.listen")
public class CsfMqListener implements MqListener {

    private CsfEndpoint endpoint;

    private Map<String, Map<String, String>> csf;

    @Autowired
    public CsfMqListener(CsfEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void listenToA() {
        Map<String, String> map = csf.get("funcA");
        String exchangeName = map.get("exchange");
        String queueName = map.get("queue");
        String routingKey = map.get("routing-key");
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

    public void listenToB() {
        Map<String, String> map = csf.get("funcB");
        String exchangeName = map.get("exchange");
        String queueName = map.get("queue");
        String routingKey = map.get("routing-key");
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

    @Override
    public void listen() {
        listenToA();
        listenToB();
    }

    public void setCsf(Map<String, Map<String, String>> csf) {
        this.csf = csf;
    }
}
