package com.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @data 获取MQ的链接
 * @author  bitao
 */
public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("47.112.11.147");
        //AMQP 5672
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123456");
        return  factory.newConnection();
    }
}
