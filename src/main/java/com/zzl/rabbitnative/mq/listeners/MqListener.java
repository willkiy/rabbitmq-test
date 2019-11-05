package com.zzl.rabbitnative.mq.listeners;

/**
 * Created by zhangzhaolin on 2019/11/4.
 */
public interface MqListener {

    // 开启监听
    void listen() throws Exception;
}
