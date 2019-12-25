package com.rabbitmq.ps;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bitao
 * @data 消费者获取消费
 */
public class Recv2 {
    private static final String QUEUE_NAME = "test_queue_topic_sms";
    private static final String EXCHANGE_NAME="text_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机转发
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //保证一次只分发一次
        channel.basicQos(1);
        //定义一个消费者
        Consumer  consumer=new DefaultConsumer(channel){
            //消费到达后触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String msg = new String(body,"utf-8");
                System.out.println("Recv msg[2]"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] deone");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume("QUEUE_NAME",autoAck,consumer);
    }
}
