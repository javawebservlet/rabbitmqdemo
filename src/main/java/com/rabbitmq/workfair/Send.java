package com.rabbitmq.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @data 生产者
 * @dec 公平分发
 */
public class Send {
    /**                       |---C1
     *  p------------Queue----|---C2
     * @param args
     */
    private static final String QUEUE_NAME = "test_work_simple";
    public static  void main(String [] args) throws IOException, TimeoutException, InterruptedException {
      //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明队列

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 公平分发
         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送给同一个消费者不得超过一条消息
         */
        int prafetchCount = 1;
        channel.basicQos(prafetchCount);

        for(int i=0;i<50;i++) {
            String msg="hello"+i;
            System.out.println("send msg"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
        }
        channel.close();
        connection.close();
    }
}
