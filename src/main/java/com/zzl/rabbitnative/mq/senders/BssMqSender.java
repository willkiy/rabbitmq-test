package com.zzl.rabbitnative.mq.senders;

import com.zzl.rabbitnative.mq.config.endpoints.ForwardMqEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Created by zhangzhaolin on 2019/11/4.
 */
@Component
public class BssMqSender extends BaseMqSender {

    @Value("${rabbitmq.send.bss.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.send.bss.routing-key}")
    private String routingKey;

    // 构造
    @Autowired
    public BssMqSender(ForwardMqEndpoint endpoint) {
        super(endpoint);
    }

    public void sendToBss(String message) throws IOException, TimeoutException {
        send(exchangeName, routingKey, message);
    }
}
