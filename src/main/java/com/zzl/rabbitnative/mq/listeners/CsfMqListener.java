package com.zzl.rabbitnative.mq.listeners;

import com.rabbitmq.client.*;
import com.zzl.rabbitnative.mq.MqConst;
import com.zzl.rabbitnative.mq.endpoints.CsfMqEndpoint;
import com.zzl.rabbitnative.mq.listeners.MqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
@ConfigurationProperties(prefix = "rabbitmq.listen")
public class CsfMqListener implements MqListener {

    @Autowired
    private CsfMqEndpoint endpoint;

    // 配置文件 map
    private Map<String, Map<String, String>> csf;

    @Override
    public void listen() throws Exception {
        for (String name: csf.keySet()) {
            Map<String, String> map = csf.get(name);
            String exchangeName = map.get(MqConst.EXCHANGE_KEY);
            String queueName = map.get(MqConst.QUEUE_KEY);
            String routingKey = map.get(MqConst.ROUTING_KEY);
            String consumerName = MqConst.LISTENER_CLASSPATH + map.get(MqConst.CONSUMER_KEY);
            // 声明并绑定
            Channel channel = endpoint.getChannel();
            channel.exchangeDeclare(exchangeName, "topic", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            // 开启监听

            Class consumerClass = Class.forName(consumerName);
            Constructor constructor = consumerClass.getConstructor(Channel.class);
            DefaultConsumer consumer = (DefaultConsumer) constructor.newInstance(channel);
            channel.basicConsume(queueName, true,"", consumer);
        }
    }

    public void setCsf(Map<String, Map<String, String>> csf) {
        this.csf = csf;
    }
}
