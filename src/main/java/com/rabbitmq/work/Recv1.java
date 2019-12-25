package com.rabbitmq.work;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bitao
 * @data 消费者1
 */
public class Recv1 {
    private static final String QUEUE_NAME = "test_simple_queque";
    public static void main(String [] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明队列
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Recv msg"+msg);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] desc");
                }
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
