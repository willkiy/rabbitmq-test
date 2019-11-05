package com.zzl.rabbitnative.mq.senders;

import com.zzl.rabbitnative.mq.endpoints.ForwardMqEndpoint;
import com.zzl.rabbitnative.mq.senders.BaseMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangzhaolin on 2019/11/5.
 */
@Component
@ConfigurationProperties(prefix = "rabbitmq.send")
public class ProductMqSender extends BaseMqSender {

    // 各产品的 exchange 和 rk
    private Map<String, Map<String, String>> product;

    public ProductMqSender(@Autowired ForwardMqEndpoint endpoint) {
        super(endpoint);
    }

    // 发送给产品线
    public void sendToProduct(String productName, String message) throws IOException, TimeoutException {
        if (product == null || !product.containsKey(productName)) {
            System.out.println("产品 MQ 配置不存在");
            return;
        }
        Map<String, String> productMqMap = product.get(productName);
        String exchangeName = productMqMap.get("exchange");
        String routingKey = productMqMap.get("routing-key");
        send(exchangeName, routingKey, message);
    }

    public void setProduct(Map<String, Map<String, String>> product) {
        this.product = product;
    }
}
