package com.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.ConnectionUtil;
import com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author
 * @desc 批量
 */
public class Send2 {
    private static final String QUEUE_NAME ="test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.confirmSelect();
        String msg = "hello config message";
        //批量发送
        for(int i = 0; i<10;i++) {
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        //确认
        if(!channel.waitForConfirms()) {
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }
    }


}
