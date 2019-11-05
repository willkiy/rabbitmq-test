package com.zzl.rabbitnative;

import com.zzl.rabbitnative.mq.senders.BssMqSender;
import com.zzl.rabbitnative.mq.senders.ProductMqSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitnativeApplicationTests {

    @Autowired
    private BssMqSender bssMqSender;

    @Autowired
    private ProductMqSender productMqSender;

    @Test
    void contextLoads() {
    }

    @Test
    public void test() throws Exception {
        bssMqSender.sendToBss("hello bss");
    }

}
