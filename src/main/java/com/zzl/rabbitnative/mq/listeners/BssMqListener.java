package com.zzl.rabbitnative.mq.listeners;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zzl.rabbitnative.mq.config.endpoints.ForwardMqEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
public class BssMqListener implements MqListener {

    @Autowired
    private ForwardMqEndpoint endpoint;

    @Value("${rabbitmq.listen.bss.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.listen.bss.queue}")
    private String queueName;

    @Value("${rabbitmq.listen.bss.routing-key}")
    private String routingKey;


    @Override
    public void listen() throws Exception {
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
                        String message = new String(body);
                        System.out.println("消费者bss == " + message);
                }
            });
    }
}
