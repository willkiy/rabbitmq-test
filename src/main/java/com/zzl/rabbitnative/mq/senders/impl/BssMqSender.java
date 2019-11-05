package com.zzl.rabbitnative.mq.senders.impl;

import com.zzl.rabbitnative.mq.endpoints.impl.ForwardEndpoint;
import com.zzl.rabbitnative.mq.senders.BaseMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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
    public BssMqSender(ForwardEndpoint endpoint) {
        super(endpoint);
    }

    public void sendToBss(String message) {
        send(exchangeName, routingKey, message);
    }
}
