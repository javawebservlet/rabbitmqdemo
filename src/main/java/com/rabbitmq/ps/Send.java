package com.rabbitmq.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bitao
 * 订阅模式 生成者
 */
public class Send {
    private static final String EXCHANGE_NAME="text_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        //声明交换器
        //分支
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //发送消息
        String msg="hello ps";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("send:"+msg);
        channel.close();
        connection.close();
    }
}
