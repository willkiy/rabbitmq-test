package com.zzl.rabbitnative.mq;

import com.zzl.rabbitnative.mq.listeners.MqListener;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

/**
 * @ClassName ApplicationStartup
 * @Description: TODO
 * @Author zhaomeiru
 * @Date 2019/11/4 18:59
 * @Version V1.0
 **/
@Configuration
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {  //项目启动后，执行启动消费者方法
        try {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext)event.getApplicationContext();
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory)context.getBeanFactory();
            Map<String, MqListener> beanMap = factory.getBeansOfType(MqListener.class);
            // 开启监听
            for (String beanName: beanMap.keySet()) {
                MqListener mqListener = beanMap.get(beanName);
                mqListener.listen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
