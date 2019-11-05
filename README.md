# 使用说明

## 1. 目录结构

```
──mq
    │  
    ├─config
    │  │  ApplicationStartup.java
    │  │  MqConst.java
    │  │  
    │  └─endpoints
    │          CsfMqEndpoint.java
    │          ForwardMqEndpoint.java
    │          MqEndpoint.java
    │          
    ├─listeners
    │      BssMqListener.java
    │      CsfConsumerA.java
    │      CsfConsumerB.java
    │      CsfMqListener.java
    │      MqListener.java
    │      
    └─senders
            BaseMqSender.java
            BssMqSender.java
            MqSender.java
            ProductMqSender.java
        
```

## 2. 相关基类与接口

1. `MqEndpoint`：endpoint基类。每一个MQ server对应一个endpoint。
2. `MqListener`：监听器（消费者）接口，定义监听方式。
3. `Consumer`：消息处理器接口，定义消息的处理方式。
4. `MqSender`：发送器（生产者）接口，定义消息的发送方式。
5. `BaseMqSender`：MqSender的一个实现类，封装了一些公共的常用方法。
6. `ApplicationStartup`：项目启动时启动所有监听器。
7. `MqConst`：定义常量。

## 3. 使用过程

### 3.1. 创建一个新的 endpoint

1. 在配置文件的`rabbitmq.server`下添加配置，包括`host,port,username,password,virtualHost`
2. 继承`MqEndpoint`基类，添加`@Component`注解
3. 读取配置文件
4. 调用基类的`setConnectionFactory`方法
5. 使用时通过`@Autowired`自动注入

### 3.2. 创建一个新的监听器

1. 在配置文件的`rabbitmq.listen`下添加配置，包括`queue,exchange,routing-key`
2. 编写消息处理器
   1. 继承`DefaultConsumer`类或实现`Consumer`接口
   2. 覆盖`handleDelivery`方法
3. 实现`MqListener`接口，添加`@Component`注解
4. 实现`listen`方法

特别的，对于Csf的监听器：
1.在配置文件的`rabbitmq.listen.csf`下添加配置，包括`queue,exchange,routing-key,consumer`，其中，`consumer`为该监听的消息处理器类的类名，规定该处理类必需处于`MqConst`指定的包名下。

### 3.3. 创建一个新的发送器

1. 继承`BssMqSender`类，添加`@Component`注解
2. 实现自己的发送方法，调用父类的`send`进行发送
3. 使用时通过`@Autowired`自动注入


## 4. 配置文件结构

```
rabbitmq:
  # MQ server
  server:
    forward:
      host: 192.168.56.101
      port: 5672
      username: admin
      password: admin
      virtual-host: /
    csf:
      host: 192.168.56.102
      port: 5672
      username: admin
      password: admin
      virtual-host: /
  # 监听端
  listen:
    bss:
      queue: bss
      exchange: bss
      routing-key: bss
    csf:
      funcA:
        queue: csfA
        exchange: csfA
        routing-key: csfA
        consumer: CsfConsumerA
      funcB:
        queue: csfB
        exchange: csfB
        routing-key: csfB
        consumer: CsfConsumerB
  # 发送端
  send:
    bss:
      exchange: bss
      routing-key: bss
    # 各产品
    product:
      cke:
        exchange: cke
        routing-key: cke
```