package com.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author
 * 普通模式
 */
public class Send1 {
    private static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirSelect 将channel设置为confirm模式注意
        channel.confirmSelect();

        String msg= "hell confirm message";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if(!channel.waitForConfirms()) {
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }
        channel.close();
        connection.close();
    }
}
