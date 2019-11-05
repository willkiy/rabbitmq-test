package com.zzl.rabbitnative.mq;

import com.zzl.rabbitnative.mq.listeners.impl.BssMqListener;
import com.zzl.rabbitnative.mq.listeners.impl.CsfMqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @ClassName ApplicationStartup
 * @Description: TODO
 * @Author zhaomeiru
 * @Date 2019/11/4 18:59
 * @Version V1.0
 **/
@Configuration
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private BssMqListener bssMqListener;
    @Autowired
    private CsfMqListener csfMqListener;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {  //项目启动后，执行启动消费者方法
        try {
            bssMqListener.listen();   //消费者的实现方法
            csfMqListener.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
