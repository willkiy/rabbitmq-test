package com.zzl.rabbitnative.mq.senders;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
public interface MqSender {

    void send(String exchangeName, String routingKey, String message) throws IOException, TimeoutException;
}
