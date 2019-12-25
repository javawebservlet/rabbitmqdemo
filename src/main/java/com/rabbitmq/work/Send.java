package com.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @data 生产者
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
