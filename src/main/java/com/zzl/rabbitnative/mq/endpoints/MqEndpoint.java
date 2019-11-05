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
    public Connection getConnection() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        this.connection = connection;
        return connection;
    }

    // 获取 channel
    public Channel getChannel() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        this.channel = channel;
        return channel;
    }

    // 关闭流
    public void close() throws IOException, TimeoutException {
        // 关闭 channel
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        // 关闭 connection
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}
