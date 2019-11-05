package com.zzl.rabbitnative.mq.endpoints.impl;

import com.zzl.rabbitnative.mq.endpoints.MqEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
public class CsfEndpoint extends MqEndpoint {

    public CsfEndpoint(
            @Value("${rabbitmq.server.forward.host}") String host,
            @Value("${rabbitmq.server.forward.port}") int port,
            @Value("${rabbitmq.server.forward.username}") String username,
            @Value("${rabbitmq.server.forward.password}") String password,
            @Value("${rabbitmq.server.forward.virtual-host}") String virtualHost
    ) {
        super.setConnectionFactory(host, port, username, password, virtualHost);
    }
}
