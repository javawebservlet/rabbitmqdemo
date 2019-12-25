package com.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bitao
 * @desc  生成者 路由模式
 */
public class Send {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        //取得链接
        Connection connection = ConnectionUtil.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        //交换机
        String test_direct="direct";
        channel.exchangeDeclare(EXCHANGE_NAME,test_direct);

        String msg = "hello direct";
        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
         System.out.println("send msg:"+msg);
        //关闭链接
        channel.close();
        connection.close();
    }
}
