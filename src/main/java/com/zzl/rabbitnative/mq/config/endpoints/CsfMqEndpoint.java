package com.zzl.rabbitnative.mq.config.endpoints;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
public class CsfMqEndpoint extends MqEndpoint{

    public CsfMqEndpoint(
            @Value("${rabbitmq.server.forward.host}") String host,
            @Value("${rabbitmq.server.forward.port}") int port,
            @Value("${rabbitmq.server.forward.username}") String username,
            @Value("${rabbitmq.server.forward.password}") String password,
            @Value("${rabbitmq.server.forward.virtual-host}") String virtualHost
    ) {
        super.setConnectionFactory(host, port, username, password, virtualHost);
    }
}
