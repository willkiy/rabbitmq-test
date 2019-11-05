package com.zzl.rabbitnative.mq.endpoints;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * MQ 监听器基类
 * Created by zhangzhaolin on 2019/11/4.
 */
public class MqEndpoint {

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    // 配置 connection factory
    public void setConnectionFactory(
        String host,
        int port,
        String username,
        String password,
        String virtualHost
    ) {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
    }

    // 获取 Connection
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        this.connection = connection;
        return connection;
    }

    // 获取 channel
    public Channel getChannel() {
        Connection connection = getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.channel = channel;
        return channel;
    }

    // 关闭流
    public void close() {
        // 关闭 channel
        if(channel != null && channel.isOpen()){
            try {
                channel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        // 关闭 connection
        if(connection != null && connection.isOpen()){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
