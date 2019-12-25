package com.rabbitmq.routing;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bitao
 * @desc 消费者消费
 */
public class Recv1 {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_direct_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //取得一个连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");

       Consumer consumer =  new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String msg = new String(body,"utf-8");
               System.out.println("[1] Recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //手动模式
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
