package com.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author biao
 * @date  2019-12-24
 * @data 消费者获取消费
 */
public class Recv {
    private static final String QUEUE_NAME = "test_simple_queque";
    public static void main(String [] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection =  ConnectionUtil.getConnection();
        //从连接获取一个通道
        Channel channel = connection.createChannel();
        //创建声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String msg=new String(body,"utf-8");
               System.out.println("new api new "+msg);
            }
        };
        //监听队列 android
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
