package com.rabbitmq.confirm;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm事务
 */
public class Recv {
    private static final  String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv[confirm] msg:" + new String(body,"utf-8"));
            }
        });
    }
}
